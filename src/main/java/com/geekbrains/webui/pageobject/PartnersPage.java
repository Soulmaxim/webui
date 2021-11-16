package com.geekbrains.webui.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PartnersPage extends BaseView {

    public PartnersPage(WebDriver driver) {
        super(driver);
    }

    private final String linkTextButtonCreatePartner = "Создать контактное лицо";
    @FindBy(linkText = linkTextButtonCreatePartner)
    public WebElement buttonCreatePartner;

    public final String xpathButtonPosition = "//div[contains(@class, 'btn') and contains(., 'Должность')]";
    @FindBy(xpath = xpathButtonPosition)
    public WebElement buttonPosition;

    public PartnersPage clickButtonPosition() {
        buttonPosition.click();
        return this;
    }

    @FindBy(xpath = "//input[@name='value']")
    public WebElement inputPosition;

    public PartnersPage fillPosition(String position) {
        inputPosition.sendKeys(position);
        return this;
    }

    @FindBy(xpath = "//input[@name='value']/following-sibling::button[.='Обновить']")
    public WebElement buttonUpdate;

    public PartnersPage clickUpdateButton() {
        buttonUpdate.click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(loader));
        return this;
    }

    @FindBy(xpath = "//span[contains(.,'Создан в')]/ancestor::a")
    public WebElement buttonCreatedAt;

    public PartnersPage clickButtonCreatedAt() {
        buttonCreatedAt.click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(loader));
        return this;
    }

    @FindBy(xpath = "//tr/td")
    public List<WebElement> createdContacts;

    public CreatePartnerPage clickCreatePartner() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(linkTextButtonCreatePartner)));
        buttonCreatePartner.click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CreatePartnerPage.xpathSaveAndCloseButton)));
        return new CreatePartnerPage(driver);
    }
}
