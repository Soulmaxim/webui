package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WillSeePage extends BaseView {

    public Header header;

    public WillSeePage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
    }

    @FindBy(xpath = "//li//a[@class='name']")
    List<WebElement> linksOfFilms;

    public FilmGreenMileBlock moveToFilmBlock(String linkText) {
        Actions actions = new Actions(driver);
        WebElement item = linksOfFilms.stream().filter(element -> element.getText().equals(linkText)).findFirst().get();
        actions.moveToElement(item).build().perform();
        return new FilmGreenMileBlock(driver, linkText);
    }
}
