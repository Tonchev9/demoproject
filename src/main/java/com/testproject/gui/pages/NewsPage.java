package com.testproject.gui.pages;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import com.onecrew.sqat_java.core.foundation.webdriver.decorator.ExtendedWebElement;
import com.onecrew.sqat_java.core.gui.AbstractPage;
import com.testproject.gui.components.NewsItem;

public class NewsPage extends AbstractPage {

    @FindBy(className="searchFor")
    private ExtendedWebElement searchTextField;

    @FindBy(xpath="//input[@value='Search']")
    private ExtendedWebElement searchButton;

    @FindBy(xpath="//div[@class='news-item']")
    private List<NewsItem> news;

    public NewsPage(WebDriver driver) {
        super(driver);
        setPageURL("/news.php3");
    }

    public List<NewsItem> searchNews(String q) {
        searchTextField.type(q);
        searchButton.click();
        return news;
    }

}
