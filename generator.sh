#!/bin/bash

suite=($(echo "$1" | tr ',' '\n'))
testruns=($(echo "$2"))

for suite in "${suite[@]}"
do
 for i in $(seq 1 $testruns)
  do
  job_title=$suite-$i
  ci_run_id=$(cat /proc/sys/kernel/random/uuid)
	cat <<EOF

job_queue_test_run_$job_title:
  stage: .pre
  image: k8spatterns/curl-jq
  script:
    - |
      accessToken=\$(echo \$(curl --request POST \
      --url "$reportingServiceURL/api/auth/refresh" \
      --header "content-type: application/json" \
      --data '{ "refreshToken": "$reportingAccessToken"}') | jq '.accessToken' | tr -d '"')
    - |
      curl --request POST \
      --url "$reportingServiceURL/api/tests/runs/queue" \
      --header "content-type: application/json" \
      --header 'Authorization: Bearer '"\$accessToken"'' \
      --data '{
            "jobUrl": "$CI_JOB_URL",
            "buildNumber": "$CI_JOB_ID",
            "branch": "$CI_COMMIT_BRANCH",
            "env": "$env",
            "ciRunId": "$ci_run_id",
            "ciParentUrl": "",
            "ciParentBuild": "",
            "project": "$reportingProject"
          }'
  allow_failure: false
  rules:
    - if: (\$CI_COMMIT_BRANCH == "feature/apitests")
      when: always


job_run_tests_$job_title:
  stage: test
  needs: ["job_queue_test_run_$job_title"]
  image: maven:3.8-jdk-8
  script:
    - mvn help:effective-settings
    - mvn -B -U -Dzafira_enabled=true -Dzafira_service_url=$reportingServiceURL -Dzafira_access_token=$reportingAccessToken -Duser.timezone=UTC clean test -DADMIN_EMAILS=ivan.dobrinov@1crew.com -DJOB_URL=$CI_JOB_URL -DJOB_NAME=$job_title -DJOB_BASE_NAME=$job_title -Dplatform=* -Dsuite=$suite -Dzafira_rerun_failures=false -Dbrowser=API -Dbranch=$CI_COMMIT_BRANCH -Dzafira_project=$reportingProject -Dci_run_id=$ci_run_id -Dqueue_registration=true -Denv=$env -Dthread_count=$threads -Dtest_run_rules= -f pom.xml
  allow_failure: true
  rules:
    - if: (\$CI_COMMIT_BRANCH == "feature/apitests")
      when: always


EOF
done
done
