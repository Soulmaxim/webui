package com.geekbrains.webui.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CreateProjectPage extends BaseView {

    public CreateProjectPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(name = "crm_project[name]")
    public WebElement inputName;

    CreateProjectPage fillName(String name) {
        inputName.sendKeys(name);
        return this;
    }

    @FindBy(xpath = "//span[.='Укажите организацию']/ancestor::a")
    public WebElement buttonSetOrganization;

    private final String xpathOrganizationItem = "//div[.='1234124']";
    @FindBy(xpath = xpathOrganizationItem)
    public WebElement organizationItem;

    CreateProjectPage setOrganization() {
        buttonSetOrganization.click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathOrganizationItem)));
        organizationItem.click();
        return this;
    }

    @FindBy(xpath = "//select[@name='crm_project[manager]']")
    public WebElement selectManager;

    CreateProjectPage fillManager(String manager) {
        new Select(selectManager).selectByVisibleText(manager);
        return this;
    }

    @FindBy(name = "crm_project[curator]")
    public WebElement selectCurator;

    CreateProjectPage fillCurator(String curator) {
        new Select(selectCurator).selectByVisibleText(curator);
        return this;
    }

    @FindBy(name = "crm_project[rp]")
    public WebElement selectRp;

    CreateProjectPage fillRp(String rp) {
        new Select(selectRp).selectByVisibleText(rp);
        return this;
    }

    @FindBy(name = "crm_project[administrator]")
    public WebElement selectAdministrator;

    CreateProjectPage fillAdministrator(String administrator) {
        new Select(selectAdministrator).selectByVisibleText(administrator);
        return this;
    }

    @FindBy(name = "crm_project[businessUnit]")
    public WebElement selectBusinessUnit;

    CreateProjectPage fillBusinessUnit(String businessUnit) {
        new Select(selectBusinessUnit).selectByVisibleText(businessUnit);
        return this;
    }

    public static final String xpathSaveAndCloseButton = "//button[contains(., 'Сохранить и закрыть')]";
    @FindBy(xpath = xpathSaveAndCloseButton)
    public WebElement buttonSaveAndClose;

    public CreateProjectPage clickSaveAndCloseButton() {
        buttonSaveAndClose.click();
        return this;
    }

    public static final String xpathSpanValidationFailed = "//span[@class='validation-failed']";
    @FindBy(xpath = xpathSpanValidationFailed)
    public WebElement spanValidationFailed;
}
