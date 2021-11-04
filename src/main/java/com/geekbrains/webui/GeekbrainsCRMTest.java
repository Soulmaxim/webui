package com.geekbrains.webui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class GeekbrainsCRMTest {
    static WebDriver beforeAll() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://crm.geekbrains.space/user/login");
        login(driver);
        return driver;
    }

    public static void createProjectTest () throws InterruptedException {
        WebDriver driver = beforeAll();

        List<WebElement> menuItems = driver.findElements(By.xpath("//ul[@class='nav nav-multilevel main-menu']/li/a"));
        WebElement expensesMenuItem = menuItems.stream().filter(e -> e.getText().equals("Проекты")).findFirst().get();
        Actions actions = new Actions(driver);
        actions.moveToElement(expensesMenuItem).build().perform();
        driver.findElement(By.xpath("//span[.='Все проекты']/ancestor::a")).click();
        driver.findElement(By.xpath("//a[.='Создать проект']")).click();

        driver.findElement(By.name("crm_project[name]")).sendKeys("Brrrrra");
        driver.findElement(By.xpath("//span[.='Укажите организацию']/ancestor::a")).click();
        // тут можно бы и список получить
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
        WebElement createdProject = driver.findElement(By.xpath("//span[@class='validation-failed']"));
        if (createdProject.isDisplayed()) System.out.println("Project created");

        Thread.sleep(5000);
        afterAll(driver);
    }

    public static void createContactPerson () throws InterruptedException {
        WebDriver driver = beforeAll();

        List<WebElement> menuItems = driver.findElements(By.xpath("//ul[@class='nav nav-multilevel main-menu']/li/a"));
        WebElement expensesMenuItem = menuItems.stream().filter(e -> e.getText().equals("Контрагенты")).findFirst().get();
        Actions actions = new Actions(driver);
        actions.moveToElement(expensesMenuItem).build().perform();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[.='Контактные лица']/ancestor::a")).click();
        driver.findElement(By.linkText("Создать контактное лицо")).click();
        driver.findElement(By.name("crm_contact[lastName]")).sendKeys("Testfam");
        driver.findElement(By.name("crm_contact[firstName]")).sendKeys("Testname");
        driver.findElement(By.name("crm_contact[middleName]")).sendKeys("Testotch");
        driver.findElement(By.name("crm_contact[jobTitle]")).sendKeys("Tester");
        driver.findElement(By.xpath("//span[.='Укажите организацию']/ancestor::a")).click();
        driver.findElement(By.xpath("//div[.='1234124']")).click();
        driver.findElement(By.xpath("//button[contains(.,'Сохранить и закрыть')]")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'btn') and contains(., 'Должность')]")).click();
        driver.findElement(By.xpath("//input[@name='value']")).sendKeys("Tester");
        driver.findElement(By.xpath("//input[@name=\"value\"]/following-sibling::button[.=\"Обновить\"]")).click();
        // тут нужно нормальное ожидание
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[contains(.,'Создан в')]/ancestor::a")).click();
        // тут нужно нормальное ожидание
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[contains(.,'Создан в')]/ancestor::a")).click();
        // тут нужно нормальное ожидание
        Thread.sleep(3000);
        WebElement createdContact = driver.findElement(By.xpath("//tr/td[contains(.,'Testfam Testname Testotch')]"));
        if (createdContact.isDisplayed()) System.out.println("Contact created");

        Thread.sleep(5000);
        afterAll(driver);
    }

    public static void main(String[] args) throws InterruptedException {
        createProjectTest();
        createContactPerson();
    }

    static void login(WebDriver driver) {
        WebElement element = driver.findElement(By.id("prependedInput"));
        element.sendKeys("Applanatest1");
        driver.findElement(By.id("prependedInput2")).sendKeys("Student2020!");
        driver.findElement(By.id("_submit")).click();
        if (driver.getCurrentUrl().equals("https://crm.geekbrains.space/")) {
            WebElement profileMenu = driver.findElements(By.xpath("//ul[@class='nav pull-right user-menu']/li/a")).get(0);
            profileMenu.click();
            WebElement exitButton = driver.findElement(By.linkText("Выйти"));
            if (exitButton.isDisplayed()) System.out.println("Success authorization");
        }
    }

    static void logout(WebDriver driver) {
        WebElement profileMenu = driver.findElements(By.xpath("//ul[@class='nav pull-right user-menu']/li/a")).get(0);
        profileMenu.click();
        driver.findElement(By.linkText("Выйти")).click();
        System.out.println("logout success");
    }

    static void afterAll(WebDriver driver) {
        logout(driver);
        driver.quit();
    }
}
