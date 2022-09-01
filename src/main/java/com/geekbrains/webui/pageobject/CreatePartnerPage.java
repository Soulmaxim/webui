package com.geekbrains.webui.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CreatePartnerPage extends BaseView {
    public CreatePartnerPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "crm_contact[lastName]")
    public WebElement inputLastName;

    @FindBy(name = "crm_contact[firstName]")
    public WebElement inputFirstName;

    @FindBy(name = "crm_contact[middleName]")
    public WebElement inputMiddleName;

    @FindBy(name = "crm_contact[jobTitle]")
    public WebElement inputJobTitle;

    CreatePartnerPage fillLastName(String lastName) {
        inputLastName.sendKeys(lastName);
        return this;
    }

    CreatePartnerPage fillFirstName(String firstName) {
        inputFirstName.sendKeys(firstName);
        return this;
    }

    CreatePartnerPage fillMiddleName(String midName) {
        inputMiddleName.sendKeys(midName);
        return this;
    }

    CreatePartnerPage fillJobTitle(String title) {
        inputJobTitle.sendKeys(title);
        return this;
    }

    @FindBy(xpath = "//span[.='Укажите организацию']/ancestor::a")
    public WebElement buttonSetOrganization;

    private final String xpathOrganizationItem = "//div[.='1234124']";
    @FindBy(xpath = xpathOrganizationItem)
    public WebElement organizationItem;

    CreatePartnerPage setOrganization() {
        buttonSetOrganization.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOrganizationItem)));
        organizationItem.click();
        return this;
    }

    public static final String xpathSaveAndCloseButton = "//button[contains(., 'Сохранить и закрыть')]";
    @FindBy(xpath = xpathSaveAndCloseButton)
    public WebElement buttonSaveAndClose;

    public CreatePartnerPage clickSaveAndCloseButton() {
        buttonSaveAndClose.click();
        return this;
    }

    public static final String xpathSpanValidationFailed = "//button[contains(., 'Сохранить и закрыть')]";
    @FindBy(xpath = xpathSpanValidationFailed)
    public WebElement spanValidationFailed;
}
