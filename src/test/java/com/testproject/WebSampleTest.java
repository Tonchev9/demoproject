package com.testproject;

/*
 * Copyright 2013-2018 QAPROSOFT (http://onecrew.com/).
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

import java.util.List;

import com.onecrew.sqat_java.core.foundation.utils.tag.Priority;
import com.onecrew.sqat_java.core.foundation.utils.tag.TestPriority;
import com.onecrew.sqat_java.core.foundation.utils.tag.TestTag;
import com.testproject.gui.components.WeValuePrivacyAd;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.onecrew.sqat_java.core.foundation.AbstractTest;
import com.onecrew.sqat_java.core.foundation.dataprovider.annotations.XlsDataSourceParameters;
import com.onecrew.sqat_java.core.foundation.utils.ownership.MethodOwner;
import com.testproject.gui.components.FooterMenu;
import com.testproject.gui.components.NewsItem;
import com.testproject.gui.components.compare.ModelSpecs;
import com.testproject.gui.components.compare.ModelSpecs.SpecType;
import com.testproject.gui.pages.BrandModelsPage;
import com.testproject.gui.pages.CompareModelsPage;
import com.testproject.gui.pages.HomePage;
import com.testproject.gui.pages.ModelInfoPage;
import com.testproject.gui.pages.NewsPage;

/**
 * This sample shows how create Web test.
 *
 * @author SQAT
 */
public class WebSampleTest extends AbstractTest {
    @Test(dataProvider = "SingleDataProvider", description = "JIRA #AUTO-0008")
    @MethodOwner(owner = "ivan")
    @TestPriority(Priority.P3)
    @TestTag(name = "area test", value = "data provider")
    @TestTag(name = "specialization", value = "xlsx")
    @XlsDataSourceParameters(path = "xls/demo.xlsx", sheet = "GSMArena", dsUid = "TUID", dsArgs = "brand, model, display, camera, ram, battery")
    public void testModelSpecs(String brand, String model, String display, String camera, String ram, String battery) {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        // Select phone brand
        homePage = new HomePage(getDriver());
        BrandModelsPage productsPage = homePage.selectBrand(brand);
        // Select phone model
        ModelInfoPage productInfoPage = productsPage.selectModel(model);
        // Verify phone specifications
        Assert.assertEquals(productInfoPage.readDisplay(), display, "Invalid display info!");
        Assert.assertEquals(productInfoPage.readCamera(), camera, "Invalid camera info!");
        Assert.assertEquals(productInfoPage.readRam(), ram, "Invalid ram info!");
        Assert.assertEquals(productInfoPage.readBattery(), battery, "Invalid battery info!");
    }


    @Test(description = "JIRA #AUTO-0009")
    @MethodOwner(owner = "qpsdemo")
    @TestPriority(Priority.P1)
    @TestTag(name = "area test", value = "web")
    public void testCompareModels() {
        // Open GSM Arena home page and verify page is opened
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        WeValuePrivacyAd closeAd = new WeValuePrivacyAd(homePage.getDriver());
        closeAd.closeAdIfPresent();
        closeAd.acceptAllCookies();
        // Open model compare page
        FooterMenu footerMenu = homePage.getFooterMenu();
        Assert.assertTrue(footerMenu.isUIObjectPresent(2), "Footer menu wasn't found!");
        CompareModelsPage comparePage = footerMenu.openComparePage();
        // Compare 3 models
        List<ModelSpecs> specs = comparePage.compareModels("Samsung Galaxy J3", "Samsung Galaxy J5", "Samsung Galaxy J7 Pro");
        // Verify model announced dates
        Assert.assertEquals(specs.get(0).readSpec(SpecType.ANNOUNCED), "2016, March 31. Released 2016, May 06");
        Assert.assertEquals(specs.get(1).readSpec(SpecType.ANNOUNCED), "2015, June 19. Released 2015, July 28");
        Assert.assertEquals(specs.get(2).readSpec(SpecType.ANNOUNCED), "2017, June");
    }

    @Test(description = "JIRA #AUTO-0010")
    @MethodOwner(owner = "sqatdemo")
    public void testNewsSearch() {
        HomePage homePage = new HomePage(getDriver());
        homePage.open();
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened!");

        NewsPage newsPage = homePage.getFooterMenu().openNewsPage();
        Assert.assertTrue(newsPage.isPageOpened(), "News page is not opened!");

        final String searchQ = "iphone";
        List<NewsItem> news = newsPage.searchNews(searchQ);
        Assert.assertFalse(CollectionUtils.isEmpty(news), "News not found!");
        for(NewsItem n : news) {
            System.out.println(n.readTitle());
            Assert.assertTrue(StringUtils.containsIgnoreCase(n.readTitle(), searchQ), "Invalid search results!");
        }
    }

}
