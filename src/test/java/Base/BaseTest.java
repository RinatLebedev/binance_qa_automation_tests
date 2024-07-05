package Base;

import AutocompletesAndCheckers.Variables;
import PageElement.HeaderMenu;
import PageObject.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.google.common.collect.ImmutableMap;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

abstract public class BaseTest implements Variables{
    protected EarnPage earnPage;
    protected MainPage mainPage;
    protected MarketsPage marketsPage;
    protected SquarePage squarePage;
    protected TradingPage tradingPage;

    protected HeaderMenu headerMenu;

    public void setUp(){
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager"; //Должен ли драйвер подождать, пока страница полностью загрузится
        Configuration.headless = false; //Отображется ли окно при прогонке теста(false). Безоконный режим - (true)
        //Configuration.baseUrl = "https://www.binance.com/ru";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        //Selenoid
        Configuration.baseUrl = "http://localhost:4444/wd/hub";
        Configuration.browserCapabilities = capabilities();


    }
    static DesiredCapabilities capabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        capabilities.setCapability("selenoid:options", ImmutableMap.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        return capabilities;
    }

    @BeforeAll
    public static void globalSetup(){
        System.setProperty("chromeoptions.args", "--remote-allow-origins=*");
    }
    @BeforeEach
    public void init(){
        setUp();
    }

    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }
}