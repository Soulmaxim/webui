package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProfileMenu extends BaseView {

    public ProfileMenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//nav//li")
    public List<WebElement> profileMenuItems;

    public void clickOnProfileMenuItem(String itemName) {
        WebElement item = profileMenuItems.stream().filter(element -> element.getText().equals(itemName)).findFirst().get();
        item.click();
    }
}
