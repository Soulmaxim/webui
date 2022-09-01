package com.geekbrains.webui.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProjectsSubMenu extends BaseView {

    @FindBy(xpath = "//span[.='Все проекты']/ancestor::a")
    public WebElement projectsSubMenuItem;

    public ProjectsSubMenu(WebDriver driver) {
        super(driver);
    }

    public void projectsSubMenuItemClick() {
        projectsSubMenuItem.click();
    }
}
