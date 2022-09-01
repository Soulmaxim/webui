package com.geekbrains.webui.pageobject;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.hasText;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

public class PageObjectTest {
    WebDriver driver;
    WebDriverWait webDriverWait;

    @BeforeAll
    static void registerDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUpBrowser() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        driver.get("https://crm.geekbrains.space/user/login");
        login();
    }

    void login() {
        new LoginPage(driver)
                .fillLogin("Applanatest1")
                .fillPassword("Student2020!")
                .submitLogin();
        assertThat(driver.getCurrentUrl(), equalTo("https://crm.geekbrains.space/"));
    }

    @Test
    @DisplayName("Создание проекта")
    void createProjectTest () {
        new MainPage(driver).navigationBar.openNavBarItem("Проекты");
        new ProjectsSubMenu(driver).projectsSubMenuItemClick();
        CreateProjectPage createProjectPage = new ProjectsPage(driver).clickCreateProject();
        createProjectPage
                .fillName("Brrrrra")
                .setOrganization()
                .fillManager("Applanatest1 Applanatest1 Applanatest1")
                .fillCurator("Applanatest1 Applanatest1 Applanatest1")
                .fillAdministrator("Applanatest1 Applanatest1 Applanatest1")
                .fillRp("Applanatest1 Applanatest1 Applanatest1")
                .fillBusinessUnit("Research & Development")
                .clickSaveAndCloseButton();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(createProjectPage.xpathSpanValidationFailed)));
        assertThat(createProjectPage.spanValidationFailed, hasText("Это значение уже используется."));
    }

    @Test
    @DisplayName("Создание контактного лица")
    void createContactPerson () {
        new MainPage(driver).navigationBar.openNavBarItem("Контрагенты");
        new PartnerSubMenu(driver).partnerSubMenuItemClick();
        CreatePartnerPage createPartnerPage = new PartnersPage(driver).clickCreatePartner();
        createPartnerPage
                .fillLastName("Testfam")
                .fillFirstName("Testname")
                .fillMiddleName("Testotch")
                .fillJobTitle("Tester")
                .setOrganization()
                .clickSaveAndCloseButton();

        PartnersPage partnersPage = new PartnersPage(driver);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(partnersPage.xpathButtonPosition)));
        partnersPage
                .clickButtonPosition()
                .fillPosition("Tester")
                .clickUpdateButton()
                .clickButtonCreatedAt()
                .clickButtonCreatedAt();

        WebElement createdContact = partnersPage.createdContacts.stream().filter(
                element -> element.getText().equals("Testfam Testname Testotch")).findFirst().get();
        assertThat(createdContact, isDisplayed());
    }

    void logout() {
        new MainPage(driver).clickOnUserMenuItem("Выйти");
    }

    @AfterEach
    void tearDown() {
        logout();
        driver.quit();
    }
}
