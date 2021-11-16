package com.geekbrains.webui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PartnerSubMenu extends BaseView {

    public PartnerSubMenu(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//span[.='Контактные лица']/ancestor::a")
    public WebElement partnerSubMenuItem;

    public void partnerSubMenuItemClick() {
        partnerSubMenuItem.click();
    }
}
