package com.geekbrains.webui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KinopoiskTest {
    static WebDriver beforeAll() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://www.kinopoisk.ru/");
        login(driver);
        return driver;
    }

    public static void setAndDeleteMarkTest () throws InterruptedException {
        WebDriver driver = beforeAll();
        driver.findElement(By.name("kp_query")).sendKeys("Побег из Шоушенка");
        driver.findElement(By.xpath("//h4[.='Побег из Шоушенка']/ancestor::a")).click();
        driver.findElement(By.xpath("//label[@data-value='10']")).click();
        WebElement myMark = driver.findElement(By.xpath("//h4[.='Моя оценка']"));
        if (myMark.isDisplayed()) System.out.println("My mark is displayed");
        Thread.sleep(5000);

        driver.findElement(By.xpath("//button[.='Удалить']")).click();
        Thread.sleep(1000); // тут нужно нормальное ожидание
        driver.findElement(By.xpath("//p[contains(., 'Ваша оценка удалится')]//parent::node()//button[.='Удалить']")).click();
        System.out.println("My mark is deleted");
        Thread.sleep(5000);
        afterAll(driver);
    }

    public static void addFilmToFavoriteTest () throws InterruptedException {
        WebDriver driver = beforeAll();
        driver.findElement(By.name("kp_query")).sendKeys("Зелёная миля");
        driver.findElement(By.xpath("//h4[.='Зеленая миля']/ancestor::a")).click();
        driver.findElement(By.xpath("//button[.='Буду смотреть']")).click();

//        FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
//                .withTimeout(Duration.ofSeconds(5))
//                .pollingEvery(Duration.ofMillis(500))
//                .ignoring(NoSuchElementException.class);
//        fluentWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[.='Фильм добавлен в папку «Буду смотреть»' and not(@*)]"))));
//        тут нужно нормальное ожидание поп-апа
        Thread.sleep(2000);
        WebElement popUp = driver.findElement(By.xpath("//div[.='Фильм добавлен в папку «Буду смотреть»' and not(@*)]"));
        if (popUp.isDisplayed()) System.out.println("Pop-up is displayed");

        WebElement profileMenu = driver.findElement(By.xpath("//div[contains(@class, 'avatarDefault')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        driver.findElement(By.linkText("Фильмы")).click();
        WebElement favoritedFilm = driver.findElement(By.linkText("Зеленая миля"));
        if (favoritedFilm.isDisplayed()) System.out.println("Film is favorited");

        WebElement filmLi = driver.findElement(By.xpath("//a[.='Зеленая миля']/parent::node()/parent::node()"));
        actions.moveToElement(filmLi).build().perform();
        driver.findElement(By.xpath("//a[.='Зеленая миля']/parent::node()/parent::node()//a[@title='удалить фильм']")).click();
        System.out.println("Film is unfavorited");

        Thread.sleep(5000);
        afterAll(driver);
    }

    public static void main(String[] args) throws InterruptedException {
        addFilmToFavoriteTest();
        setAndDeleteMarkTest();
    }

    static void login(WebDriver driver) {
        // кинопоиск для входа на сайт отправляет на страницу входа в яндекс аккаунт
        // поэтому просто ввести адрес страницы регистрации тут не получится видимо?
        // адрес такой примерно у неё: https://passport.yandex.ru/auth?origin=kinopoisk&retpath=https%3A%2F%2Fsso.passport.yandex.ru%2Fpush%3Fretpath%3Dhttps%253A%252F%252Fwww.kinopoisk.ru%252Fapi%252Fprofile-pending%252F%253Fretpath%253Dhttps%25253A%25252F%25252Fwww.kinopoisk.ru%25252F%26uuid%3D80b185f7-ac2e-46b6-b343-a5d9af9674ac
        driver.findElement(By.xpath("//button[.='Войти']")).click();
        List<WebElement> anyAccount = driver.findElements(By.xpath("//span[contains(., 'другой аккаунт')]/ancestor::a"));
        // если недавно был выполнен выход из аккаунта, то будет это дополнительное окно
        if (anyAccount.size() != 0) anyAccount.get(0).click();
        driver.findElement(By.name("login")).sendKeys("soulmaxim2");
        driver.findElement(By.xpath("//span[.='Войти']/ancestor::button")).click();
        driver.findElement(By.name("passwd")).sendKeys("Qwerty123!");
        driver.findElement(By.xpath("//span[.='Войти']/ancestor::button")).click();
        WebElement profileMenu = driver.findElement(By.xpath("//div[contains(@class, 'avatarDefault')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        WebElement menuExit = driver.findElement(By.xpath("//button[.='Выйти']"));
        if (menuExit.isDisplayed()) System.out.println("Success authorization");
    }

    static void logout(WebDriver driver) {
        WebElement profileMenu = driver.findElement(By.xpath("//a[@href='/mykp/movies/']/following-sibling::div/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        driver.findElement(By.xpath("//button[.='Выйти']")).click();
    }

    static void afterAll(WebDriver driver) {
        logout(driver);
        driver.quit();
    }
}
