package com.geekbrains.webui.pageobjectkinopoisk;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.qatools.htmlelements.matchers.WebElementMatchers.isDisplayed;

public class PageObjectKinopoiskTest {
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
        driver.get("https://www.kinopoisk.ru/");
        webDriverWait = new WebDriverWait(driver, 5);
        login();
    }

    private void login() {
        new MainPage(driver).header.clickOnUserLoginButton();
        new LoginPage(driver)
                .fillLogin("Soulmaxim2")
                .submitLogin()
                .fillPassword("Qwerty123!")
                .submitPassword();
        WebElement menuExit = new MainPage(driver).header.openProfileMenu().profileMenuItems
                .stream().filter(element -> element.getText().equals("Выйти")).findFirst().get();
        assertThat(menuExit, isDisplayed());
    }

    @Test
    @DisplayName("Выставление оценки фильму")
    void setMarkTest () {
        new MainPage(driver).header
                .fillInputSearch("Побег из Шоушенка")
                .clickOnSearchResultItem("film/326/sr/2/");

        MoviePage moviePage = new MoviePage(driver);
        moviePage.setMark("10");
        assertThat(moviePage.myMark, isDisplayed());

        moviePage
                .clickButtonDeleteMark()
                .clickButtonAssertDeleteMark();
        assertThat(moviePage.myMark, not(isDisplayed()));
    }

    @Test
    @DisplayName("Добавление фильма в список 'Буду смотреть'")
    void addFilmToWillSeeTest () {
        new MainPage(driver).header
                .fillInputSearch("Зелёная миля")
                .clickOnSearchResultItem("film/435/sr/2/");

        MoviePage moviePage = new MoviePage(driver);
        WebElement popUp = moviePage
                .clickButtonWillSee()
                .divWithMessage;
        assertThat(popUp, isDisplayed());

        moviePage.header
                .openProfileMenu()
                .clickOnProfileMenuItem("Фильмы");

        WillSeePage willSeePage = new WillSeePage(driver);
        FilmGreenMileBlock filmGreenMileBlock = willSeePage.moveToFilmBlock("Зеленая миля");
        assertThat(filmGreenMileBlock.divFilmBlock, isDisplayed());

        filmGreenMileBlock.clickButtonDeleteFilm();
        assertThat(filmGreenMileBlock.divFilmBlock, not(isDisplayed()));
    }

    private void logout() {
        new Header(driver).openProfileMenu().clickOnProfileMenuItem("Выйти");
    }

    @AfterEach
    private void tearDown() {
        logout();
        driver.quit();
    }
}
