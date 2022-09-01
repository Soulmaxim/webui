package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Header extends BaseView {

    Header(WebDriver driver) {
        super(driver);
    }

    // public static final String xpathProfileMenu = "//div[contains(@class, 'avatarDefault')]";
    // одна и та же кнопка в хэдере на разных страницах разная
    // и прицепиться в xpath совсем не за что, поэтому такой некрасивый xpath
    public static final String xpathProfileMenu = "//header//span/parent::div/ancestor::button/parent::div";
    @FindBy(xpath = xpathProfileMenu)
    public WebElement profileMenu;

    @FindBy(xpath = "//button[.='Войти']")
    public WebElement loginButton;

    @FindBy(name = "kp_query")
    public WebElement inputSearch;

    public static final String xpathSearchLoader = "//div[contains(@class, 'spinner') and not(div)]";
    @FindBy(xpath = xpathSearchLoader)
    public WebElement searchLoader;

    public void clickOnUserLoginButton() {
        loginButton.click();
    }

    public SearchResult fillInputSearch(String text) {
        inputSearch.sendKeys(text);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(SearchResult.xpathSearchResultItems)));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new SearchResult(driver);
    }

    public ProfileMenu openProfileMenu() {
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        return new ProfileMenu(driver);
    }
}
