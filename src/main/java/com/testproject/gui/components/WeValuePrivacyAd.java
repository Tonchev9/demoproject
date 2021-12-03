package com.testproject.gui.components;

/*
 * Copyright 2013-2019 QAPROSOFT (http://onecrew.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.onecrew.sqat_java.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.onecrew.sqat_java.core.gui.AbstractUIObject;

public class WeValuePrivacyAd extends AbstractUIObject {
	@FindBy(xpath = "//button[contains(@onclick, 'setAndSaveAllConsent')]")
	private ExtendedWebElement okBtn;

	@FindBy(xpath="//div[@id='uniccmp']/div[@id='unic-b']/div[@class='fixed unic-modal flex flex-col justify-center top-0 bottom-0 left-0 right-0 unic-bg']/div[@class='unic-bar mx-2 md:mx-3 rounded-t-lg bg-white fixed flex z-40 bottom-0 border-t border-l border-r shadow-md justify-center items-center max-h-full']/div[@class='flex flex-col p-2 w-full']/div[@class='flex w-full flex-wrap']/div[@class='flex']/button[@class=' bg-gray-300 text-sm md:text-base hover:bg-gray-400 text-gray-800 font-bold py-2 px-4 rounded outline-none m-2 '][2]")
	private ExtendedWebElement acceptAll;

	public WeValuePrivacyAd(WebDriver driver, SearchContext searchContext) {
		super(driver, searchContext);
	}

	public WeValuePrivacyAd(WebDriver driver) {
		super(driver);
	}

	public void closeAdIfPresent() {
		okBtn.clickIfPresent();
	}

	public void acceptAllCookies() {
		acceptAll.clickIfPresent();
	}

}
