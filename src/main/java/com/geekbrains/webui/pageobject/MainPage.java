package com.geekbrains.webui.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BaseView {
    NavigationBar navigationBar;

    @FindBy(xpath = "//li[@id='user-menu']/a")
    public WebElement profileMenu;

    public void clickOnUserMenuItem(String itemName) {
        Actions actions = new Actions(driver);
        profileMenu.click();
        WebElement item = driver.findElement(By.linkText(itemName));
        item.click();
    }

    public MainPage(WebDriver driver) {
        super(driver);
        navigationBar = new NavigationBar(driver);
    }
}

