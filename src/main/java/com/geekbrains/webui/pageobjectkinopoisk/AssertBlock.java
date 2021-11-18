package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AssertBlock extends BaseView {

    @FindBy(xpath = "//p[contains(., 'Ваша оценка удалится')]//parent::node()//button[.='Удалить']")
    WebElement buttonDeleteMark;

    public AssertBlock(WebDriver driver) {
        super(driver);
    }

    public void clickButtonAssertDeleteMark() {
        buttonDeleteMark.click();
    }
}
