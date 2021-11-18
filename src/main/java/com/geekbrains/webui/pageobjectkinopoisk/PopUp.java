package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PopUp extends BaseView {

    public PopUp(WebDriver driver) {
        super(driver);
    }

    public static final String xpathDivWithMessage = "//div[.='Фильм добавлен в папку «Буду смотреть»' and not(@*)]";
    @FindBy (xpath = xpathDivWithMessage)
    WebElement divWithMessage;
}
