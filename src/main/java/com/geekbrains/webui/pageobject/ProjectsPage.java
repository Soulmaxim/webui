package com.geekbrains.webui.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProjectsPage extends BaseView {

    private final String linkTextButtonCreateProject = "Создать проект";
    @FindBy(linkText = linkTextButtonCreateProject)
    public WebElement buttonCreateProject;

    public ProjectsPage(WebDriver driver) {
        super(driver);
    }

    public CreateProjectPage clickCreateProject() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(linkTextButtonCreateProject)));
        buttonCreateProject.click();
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(CreateProjectPage.xpathSaveAndCloseButton)));
        return new CreateProjectPage(driver);
    }
}
