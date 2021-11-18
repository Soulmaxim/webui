package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class MoviePage extends BaseView {

    public Header header;

    public MoviePage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
    }

    @FindBy(xpath = "//h4[.='Моя оценка']")
    WebElement myMark;

    @FindBy(xpath = "//label[@data-value]")
    List<WebElement> stars;

    public MoviePage setMark(String value) {
        stars.stream().filter(element -> element.getAttribute("data-value").equals(value)).findFirst().get().click();
        return this;
    }

    @FindBy(xpath = "//button[.='Удалить']")
    WebElement buttonDeleteMark;

    public AssertBlock clickButtonDeleteMark() {
        buttonDeleteMark.click();
        return new AssertBlock(driver);
    }

    @FindBy(xpath = "//button[.='Буду смотреть']")
    WebElement buttonWillSee;

    public PopUp clickButtonWillSee() {
        buttonWillSee.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PopUp.xpathDivWithMessage)));
        return new PopUp(driver);
    }
}
