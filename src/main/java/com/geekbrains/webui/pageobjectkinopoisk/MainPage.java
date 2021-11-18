package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.WebDriver;

public class MainPage extends BaseView {

    public Header header;

    public MainPage(WebDriver driver) {
        super(driver);
        header = new Header(driver);
    }
}

