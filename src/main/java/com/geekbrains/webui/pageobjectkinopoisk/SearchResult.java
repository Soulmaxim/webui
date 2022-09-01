package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class SearchResult extends BaseView {
    public SearchResult(WebDriver driver) {
        super(driver);
    }

    public static final String xpathSearchResultItems = "//h4/ancestor::article/a";
    @FindBy(xpath = xpathSearchResultItems)
    public List<WebElement> searchResultItems;

    public void clickOnSearchResultItem(String result) {
        searchResultItems.stream().filter(element -> element.getAttribute("href").equals(driver.getCurrentUrl()+result)).findFirst().get().click();
    }
}
