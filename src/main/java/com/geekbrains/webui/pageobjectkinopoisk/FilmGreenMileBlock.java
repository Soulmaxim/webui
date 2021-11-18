package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FilmGreenMileBlock extends BaseView {

    // это очень странный класс, но непонятно как сделать грамотнее
    // (случай когда на сайте несколько однотипных блоков, которые отличаются только названием)

    private String linkText;

    public FilmGreenMileBlock(WebDriver driver, String linkText) {
        super(driver);
        this.linkText = linkText;
    }

    @FindBy (xpath = "//a[.='Зеленая миля']/parent::node()/parent::node()")
    WebElement divFilmBlock;

    @FindBy (xpath = "//a[.='Зеленая миля']/parent::node()/parent::node()//a[@title='удалить фильм']")
    WebElement buttonDeleteFilm;

    public void clickButtonDeleteFilm() {
        buttonDeleteFilm.click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(divFilmBlock));
    }
}
