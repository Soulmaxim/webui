package com.geekbrains.webui.junit;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.hasText;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

public class GeekbrainsCRMTest {
    private static Logger logger = LoggerFactory.getLogger(GeekbrainsCRMTest.class);
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    private static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    private void initBrowserAndLogin() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, 5);
        logger.debug("Initialize Chrome browser");
        driver.get("https://crm.geekbrains.space/user/login");
        login();
    }

    private void login() {
        WebElement element = driver.findElement(By.id("prependedInput"));
        element.sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.id("_submit")).click();
        assertThat(driver.getCurrentUrl(), equalTo("https://crm.geekbrains.space/"));
        WebElement profileMenu = driver.findElements(By.xpath("//ul[@class='nav pull-right user-menu']/li/a")).get(0);
        profileMenu.click();
        WebElement exitButton = driver.findElement(By.linkText("Выйти"));
        assertThat(exitButton, isDisplayed());
        logger.debug("Success authorization");
    }

    @Test
    @DisplayName("Создание проекта")
    void createProjectTest () {
        List<WebElement> menuItems = driver.findElements(By.xpath("//ul[@class='nav nav-multilevel main-menu']/li/a"));
        WebElement expensesMenuItem = menuItems.stream().filter(e -> e.getText().equals("Проекты")).findFirst().get();
        Actions actions = new Actions(driver);
        actions.moveToElement(expensesMenuItem).build().perform();
        driver.findElement(By.xpath("//span[.='Все проекты']/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Создать проект")));
        driver.findElement(By.xpath("//a[.='Создать проект']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("crm_project[name]")));
        driver.findElement(By.name("crm_project[name]")).sendKeys("Brrrrra");
        driver.findElement(By.xpath("//span[.='Укажите организацию']/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[.='1234124']")));
        driver.findElement(By.xpath("//div[.='1234124']")).click();
        Select selectManager = new Select(driver.findElement(By.name("crm_project[manager]")));
        selectManager.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
        Select selectCurator = new Select(driver.findElement(By.name("crm_project[curator]")));
        selectCurator.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
        Select selectRP = new Select(driver.findElement(By.name("crm_project[rp]")));
        selectRP.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
        Select selectAdmin = new Select(driver.findElement(By.name("crm_project[administrator]")));
        selectAdmin.selectByVisibleText("Applanatest1 Applanatest1 Applanatest1");
        Select selectBusinessUnit = new Select(driver.findElement(By.name("crm_project[businessUnit]")));
        selectBusinessUnit.selectByVisibleText("Research & Development");
        driver.findElement(By.xpath("//button[contains(.,'Сохранить и закрыть')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='validation-failed']")));
        WebElement createdProject = driver.findElement(By.xpath("//span[@class='validation-failed']"));
        assertThat(createdProject, hasText("Это значение уже используется."));
        logger.debug("Project not created (it`s correct)");
    }

    @Test
    @DisplayName("Создание контактного лица")
    void createContactPerson () {
        List<WebElement> menuItems = driver.findElements(By.xpath("//ul[@class='nav nav-multilevel main-menu']/li/a"));
        WebElement expensesMenuItem = menuItems.stream().filter(
                e -> e.getText().equals("Контрагенты")).findFirst().get();
        Actions actions = new Actions(driver);
        actions.moveToElement(expensesMenuItem).build().perform();
        driver.findElement(By.xpath("//span[.='Контактные лица']/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.elementToBeClickable(
                By.linkText("Создать контактное лицо")));
        driver.findElement(By.linkText("Создать контактное лицо")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("crm_contact[lastName]")));
        driver.findElement(By.name("crm_contact[lastName]")).sendKeys("Testfam");
        driver.findElement(By.name("crm_contact[firstName]")).sendKeys("Testname");
        driver.findElement(By.name("crm_contact[middleName]")).sendKeys("Testotch");
        driver.findElement(By.name("crm_contact[jobTitle]")).sendKeys("Tester");
        driver.findElement(By.xpath("//span[.='Укажите организацию']/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[.='1234124']")));
        driver.findElement(By.xpath("//div[.='1234124']")).click();
        driver.findElement(By.xpath("//button[contains(.,'Сохранить и закрыть')]")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'btn') and contains(., 'Должность')]")));
        driver.findElement(By.xpath("//div[contains(@class, 'btn') and contains(., 'Должность')]")).click();
        driver.findElement(By.xpath("//input[@name='value']")).sendKeys("Tester");
        driver.findElement(By.xpath("//input[@name=\"value\"]/following-sibling::button[.=\"Обновить\"]")).click();

//        Не понял почему здесь не работает такое ожидание:
//        webDriverWait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//span[contains(.,'Создан в')]/ancestor::a")));
//        Если его поставить, что всё равно пишет, что element is not clickable

        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='loader-content']")));
        driver.findElement(By.xpath("//span[contains(.,'Создан в')]/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[@class='loader-content']")));
        driver.findElement(By.xpath("//span[contains(.,'Создан в')]/ancestor::a")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tr/td[contains(.,'Testfam Testname Testotch')]")));
        WebElement createdContact = driver.findElement(By.xpath("//tr/td[contains(.,'Testfam Testname Testotch')]"));
        assertThat(createdContact, isDisplayed());
        logger.debug("Contact created");
    }

    private void logout() {
        WebElement profileMenu = driver.findElements(By.xpath("//ul[@class='nav pull-right user-menu']/li/a")).get(0);
        profileMenu.click();
        driver.findElement(By.linkText("Выйти")).click();
        logger.debug("Unauthorized");
    }

    @AfterEach
    private void tearDown() {
        logout();
        driver.quit();
        logger.debug("Success teardown");
    }
}
