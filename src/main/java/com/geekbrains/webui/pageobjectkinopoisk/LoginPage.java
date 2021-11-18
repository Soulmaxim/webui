package com.geekbrains.webui.pageobjectkinopoisk;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BaseView {

    @FindBy(name = "login")
    public WebElement inputLogin;

    @FindBy(name = "passwd")
    public WebElement inputPassword;

    @FindBy(xpath = "//span[.='Войти']/ancestor::button")
    public WebElement buttonLogin;

    public LoginPage fillLogin(String login) {
        inputLogin.sendKeys(login);
        return this;
    }

    public LoginPage fillPassword(String password) {
        inputPassword.sendKeys(password);
        return this;
    }

    public LoginPage submitLogin() {
        buttonLogin.click();
        webDriverWait.until(ExpectedConditions.visibilityOf(inputPassword));
        return this;
    }

    public void submitPassword() {
        buttonLogin.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Header.xpathProfileMenu)));
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }
}

