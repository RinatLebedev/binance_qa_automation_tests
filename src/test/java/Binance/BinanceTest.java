package Binance;

import Base.BaseTest;
import PageElement.HeaderMenu;
import PageObject.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static AutocompletesAndCheckers.SomeFunctions.TestingFunctions.*;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("UI")
public class BinanceTest extends BaseTest {

    @Test
    @Description("Проверка функциональности добавления криптовалютной пары в избранное, " +
            "проверка корректного отображения названия криптовалюты в паре ")
    void testCase_1() {
        step("Открываем сайт", ()-> mainPage = new MainPage(SomeVariables.BASE_URL));
        step("Принимаем куки", () -> mainPage.buttonClicker(MainPage.ACCEPT_COOKIE));
        step("Открываем раздел рынки", () -> marketsPage = new MarketsPage(HeaderMenu.MARKETS_BUTTON_XPATH));
        step("Нажимаем на кнопку перехода в подраздел 'Избранное'", () -> marketsPage.buttonClicker(MarketsPage.MARKET_SECTOR_FAVORITE_BUTTON_XPATH));
        step("Нажимаем на кнопку 'Добавить в избранное'", () -> marketsPage.buttonClicker(MarketsPage.ADD_TO_MARKET_SECTOR_FAVORITE_BUTTON_XPATH));
        step("Нажимаем функциональный элемент 'добавление криптовалютной пары в избранное'", () -> marketsPage.buttonClicker(MarketsPage.ADD_TO_FAVORITE_STAR_CSS_SELECTOR));
        step("Возвращаемся в подраздел 'Избранное'" , () -> marketsPage.buttonClicker(MarketsPage.MARKET_SECTOR_FAVORITE_BUTTON_XPATH));
        step("Проверка, содержит ли название криптовалюты до слеша нужные символы", () -> {
            String expectedResult = "BTC";
            String actualResult = marketsPage.getBeforeSlashCryptoName(MarketsPage.GET_BEFORE_SLASH_CRYPTO_NAME);
            Assertions.assertTrue(expectedResult.equals(actualResult));
        });
    }

    @Test
    @Description("Проверка отображения подраздела 'Новые листинги' в разделе 'Рынки'. " +
            "Составление отчета с названием криптовалюты и её ценой ")
    void testCase_2(){
        step("Открываем сайт", ()-> mainPage = new MainPage(SomeVariables.BASE_URL));
        step("Принимаем куки", () -> mainPage.buttonClicker(MainPage.ACCEPT_COOKIE));
        step("Открываем раздел рынки", () -> marketsPage = new MarketsPage(HeaderMenu.MARKETS_BUTTON_XPATH));
        step("Нажимаем на кнопку перехода в подраздел 'Новые листинги'", () -> marketsPage.buttonClicker(MarketsPage.MARKET_SECTOR_NEW_LISTINGS_BUTTON_XPATH));
        step("Записываем в коллекцию все названия колонок, сверяем их с ожидаемым результатом," +
                "записываем в текстовый файл короткие названия криптовалют и их цену", () -> {
            ElementsCollection columnNamesCollection = MarketsPage.NEW_LISTINGS_COLUMN_NAMES_COLLECTION.shouldBe(size(6));
            List<String> textsArray1 = listFillingFunction(columnNamesCollection);
            List<String> expectedResult = new ArrayList<>();
            expectedResult.add(0,"Название");
            expectedResult.add(1,"Цена");
            expectedResult.add(2,"Изменить");
            expectedResult.add(3,"Объем за 24ч");
            expectedResult.add(4,"Капитализация");
            expectedResult.add(5,"Дата листинга");
            Assertions.assertTrue(textsArray1.equals(expectedResult));
        });
        step("Записываем в файл 'testCase_2_output.txt' короткие названия криптовалют и их цену", () -> {
            ElementsCollection cryptoShortNamesCollection = MarketsPage.NEW_LISTINGS_CRYPTO_SHORT_NAME_COLLECTION.shouldBe(size(10));
            ElementsCollection cryptoPricesCollection = MarketsPage.NEW_LISTINGS_CRYPTO_PRICES_COLLECTION.shouldBe(size(10));
            Map<String, String> nameAndPrice = mapFillingFunction(cryptoShortNamesCollection, cryptoPricesCollection);
            showMap(nameAndPrice);
            writeToTextFile("textFiles/testCase_2_output", nameAndPrice);
        });
    }

    @Test
    @Description("Проверка функциональности редактирования подраздела 'Технический индикатор' в разделе 'График' страницы криптовалюты")
    void testCase_3() throws InterruptedException {
        step("Открываем сайт", ()-> mainPage = new MainPage(SomeVariables.BASE_URL));
        step("Принимаем куки", () -> mainPage.buttonClicker(MainPage.ACCEPT_COOKIE));
        step("Открываем раздел рынки", () -> marketsPage = new MarketsPage(HeaderMenu.MARKETS_BUTTON_XPATH));
        Thread.sleep(5000);
        step("Найти в списке криптовалют BTC и нажать на коротное название ", () -> tradingPage = new TradingPage(MarketsPage.FIND_BTC_IN_ALL_CRIPTOCURRENCIES));
        switchTo().window(1); //фокус на вторую вкладку
        Thread.sleep(8000);
        step("Открываем меню кнопки 'Технический индикатор', активируем индикаторы: 'EMA', 'WMA'", ()->{
            tradingPage.buttonClicker(TradingPage.BUTTON_TECHNICAL_INDICATOR); //Кнопка технический индикатор
            ElementsCollection colOfIndicators = TradingPage.TECHNICAL_INDICATOR_ELEMENTS_COLLECTION;
            colOfIndicators.get(0).click();
            colOfIndicators.get(1).click();
            tradingPage.buttonClicker(TradingPage.BUTTON_SAVE_IN_TECHNICAL_INDICATOR_MENU); //Кнопка сохранить
        });
        Thread.sleep(5000);
        step("Проверяем, что индикаторы активированы и отображаются. Если они парсятся, значит активны", ()-> {
            SelenideElement ma = $(".chart-title-indicator-container > span[key = 'MA[7]']"),
                    ema = $(".chart-title-indicator-container > span[key = 'EMA[7]']"),
                    wma = $(".chart-title-indicator-container > span[key = 'WMA[7]']");
        });
    }

    @Test
    @Description("Проверка отображения раздела 'Binance Square'. Получения href атрибута статьи")
    void testCase_4() throws InterruptedException{
        step("Открываем сайт", ()-> mainPage = new MainPage(SomeVariables.BASE_URL));
        step("Принимаем куки", () -> mainPage.buttonClicker(MainPage.ACCEPT_COOKIE));
        Thread.sleep(5000);
        step("Открываем раздел Square");
        step("Переход в подраздел 'Блог' из раздела 'Square' и возврат назад", () -> {
            actions().moveToElement(HeaderMenu.SQUARE_BUTTON_XPATH)
                    .moveToElement(HeaderMenu.SQUARE_MENU_BLOG_BUTTON_XPATH)
                    .click().build().perform();
            back();
        });
        step("Переход в подраздел 'Square'", () -> {
            actions().moveToElement(HeaderMenu.SQUARE_BUTTON_XPATH)
                    .moveToElement(HeaderMenu.SQUARE_MENU_SQUARE_BUTTON_XPATH)
                    .click().build().perform();
        });
        Thread.sleep(5000);
        step("Получение href акрибура первой статьи, запись его в файл 'testCase_4_output.txt'", () -> {
            ElementsCollection postsElementsCollection = SquarePage.SQUADE_PAGE_POSTS_ELEMENTS_COLLECTION_XPATH;
            String bool = "";
            String link = postsElementsCollection.get(1).getAttribute("href");
            writeToTextFile("textFiles/testCase_4_output", link);
            Assertions.assertTrue(!(bool.equals(link)));
        });
    }


    @Test
    @Description("Проверка работоспособности раздела 'Binance Earn'. Получение данных из меню поиска криптовалют")
    void testCase_5(){
        step("Открываем сайт", ()-> mainPage = new MainPage(SomeVariables.BASE_URL));
        step("Принимаем куки", () -> mainPage.buttonClicker(MainPage.ACCEPT_COOKIE));
        step("Открываем раздел Earn", () -> earnPage = new EarnPage(HeaderMenu.EARN_BUTTON_XPATH));
        //Thread.sleep(5000);
        //earnPage.buttonClicker(EarnPage.CLOSE_POP_UP_WINDOW_CSS_SELECTOR);
        step("Переводим фокус страницы вниз до поля ввода названия криптовалюты, ввод в поле текста: 'NOT'", () -> {
            earnPage.scrollToElement(EarnPage.EARN_PAGE_WHAT_IS_EARN_BUTTON_XPATH);
            earnPage.buttonClicker(EarnPage.INPUT_FIND_COIN_INPUT_XPATH);
            earnPage.setSendKeys(EarnPage.INPUT_FIND_COIN_INPUT_XPATH, "NOT");
                });
        //Thread.sleep(5000);
        step("Получение знанения из колонки 'Расч. APR', запись его в файл 'testCase_5_output.txt'", () -> {
            SelenideElement firstCoinAPR = EarnPage.GET_EARN_PAGE_FIRST_COIN_APR_XPATH;
            String bool = "";
            String result = firstCoinAPR.getText();
            writeToTextFile("textFiles/testCase_5_output", result);
            Assertions.assertTrue(!(bool.equals(result)));
        });
    }
}
