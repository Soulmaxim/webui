package com.geekbrains.webui.junit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

public class KinopoiskTest {
    private static Logger logger = LoggerFactory.getLogger(KinopoiskTest.class);
    private WebDriver driver;
    private WebDriverWait webDriverWait;

    @BeforeAll
    private static void setupDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    private void initBrowserAndLogin() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-notification");
        driver = new ChromeDriver(chromeOptions);
        webDriverWait = new WebDriverWait(driver, 5);
        logger.debug("Initialize Chrome browser");
        driver.get("https://www.kinopoisk.ru/");
        login();
    }

    private void login() {
        driver.findElement(By.xpath("//button[.='Войти']")).click();
        List<WebElement> anyAccount = driver.findElements(By.xpath("//span[contains(., 'другой аккаунт')]/ancestor::a"));
        // если недавно был выполнен выход из аккаунта (видимо), то будет это дополнительное окно
        if (anyAccount.size() != 0) anyAccount.get(0).click();
        driver.findElement(By.name("login")).sendKeys("soulmaxim2");
        driver.findElement(By.xpath("//span[.='Войти']/ancestor::button")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("passwd")));
        driver.findElement(By.name("passwd")).sendKeys("Qwerty123!");
        driver.findElement(By.xpath("//span[.='Войти']/ancestor::button")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class, 'avatarDefault')]")));
        WebElement profileMenu = driver.findElement(By.xpath("//div[contains(@class, 'avatarDefault')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        WebElement menuExit = driver.findElement(By.xpath("//button[.='Выйти']"));
        assertThat(menuExit, isDisplayed());
        logger.debug("Success authorization");
    }

    @Test
    @DisplayName("Выставление оценки фильму")
    void setMarkTest () {
        driver.findElement(By.name("kp_query")).sendKeys("Побег из Шоушенка");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[.='Побег из Шоушенка']/ancestor::article/a")));
        driver.findElement(By.xpath("//h4[.='Побег из Шоушенка']/ancestor::article/a")).click();
        driver.findElement(By.xpath("//label[@data-value='10']")).click();
        WebElement myMark = driver.findElement(By.xpath("//h4[.='Моя оценка']"));
        assertThat(myMark, isDisplayed());
        logger.debug("My mark is displayed");
        driver.findElement(By.xpath("//button[.='Удалить']")).click();
        driver.findElement(By.xpath("//p[contains(., 'Ваша оценка удалится')]//parent::node()//button[.='Удалить']")).click();
        assertThat(myMark, not(isDisplayed()));
        logger.debug("My mark is deleted");
    }

    @Test
    @DisplayName("Добавление фильма в избранное")
    void addFilmToFavoriteTest () {
        driver.findElement(By.name("kp_query")).sendKeys("Зелёная миля");
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[.='Зеленая миля']/ancestor::article/a")));
        driver.findElement(By.xpath("//h4[.='Зеленая миля']/ancestor::article/a")).click();
        driver.findElement(By.xpath("//button[.='Буду смотреть']")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[.='Фильм добавлен в папку «Буду смотреть»' and not(@*)]")));
        WebElement popUp = driver.findElement(By.xpath("//div[.='Фильм добавлен в папку «Буду смотреть»' and not(@*)]"));
        assertThat(popUp, isDisplayed());
        logger.debug("Pop-up is displayed");

        WebElement profileMenu = driver.findElement(By.xpath("//div[contains(@class, 'avatarDefault')]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        driver.findElement(By.linkText("Фильмы")).click();
        WebElement favoritedFilm = driver.findElement(By.linkText("Зеленая миля"));
        assertThat(favoritedFilm, isDisplayed());
        logger.debug("Film is favorited");

        WebElement filmLi = driver.findElement(By.xpath("//a[.='Зеленая миля']/parent::node()/parent::node()"));
        actions.moveToElement(filmLi).build().perform();
        driver.findElement(By.xpath("//a[.='Зеленая миля']/parent::node()/parent::node()//a[@title='удалить фильм']")).click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(favoritedFilm));
        assertThat(favoritedFilm, not(isDisplayed()));
        logger.debug("Film is unfavorited");
    }

    private void logout() {
        WebElement profileMenu = driver.findElement(By.xpath("//a[@href='/mykp/movies/']/following-sibling::div/button"));
        Actions actions = new Actions(driver);
        actions.moveToElement(profileMenu).build().perform();
        driver.findElement(By.xpath("//button[.='Выйти']")).click();
        logger.debug("Unauthorized");
    }

    @AfterEach
    private void tearDown() {
        logout();
        driver.quit();
        logger.debug("Success teardown");
    }
}
