package PageObject;

import Base.BasePage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;

public class MarketsPage extends BasePage {
    public static SelenideElement MARKET_SECTOR_FAVORITE_BUTTON_XPATH = $x("//div[@id = 'market_sector_Favorite']/a"),
            ADD_TO_MARKET_SECTOR_FAVORITE_BUTTON_XPATH = $x("//button[@data-bn-type = 'button']"),
            GET_BEFORE_SLASH_CRYPTO_NAME = $x("//div[contains(@class, 'subtitle2-1 mobile:subtitle3')]"),
            FIRST_CRYPTOCURRENCY_LINK_XPATH = $x("//div[@class = 'tab__column css-hq3o44']//div[contains(@class, 'subtitle3')]"),
            FIND_BTC_IN_ALL_CRIPTOCURRENCIES = $x("//div[@class = 'subtitle3 text-t-primary css-vurnku'][text() = 'BTC']/ancestor::div[@class = 'css-1y4fqhd']"),
            MARKETS_SPOT_MARGIN_USDT_XPATH = $x("//div[@id = 'market_sector_Spot/Margin Market']/a"),
            ADD_TO_FAVORITE_STAR_CSS_SELECTOR = $(".shrink-0.self-center.css-64tkvw > path"),
            MARKET_SECTOR_NEW_LISTINGS_BUTTON_XPATH = $x("//div[@id = 'market_sector_NewListing']/a");
    public static ElementsCollection NEW_LISTINGS_COLUMN_NAMES_COLLECTION = $$x("//div[@data-bn-type='text'][@class = 'css-1i04fkn']"),
            NEW_LISTINGS_CRYPTO_PRICES_COLLECTION = $$x("//div[@class = 'body2 items-center css-18yakpx']"),
            NEW_LISTINGS_CRYPTO_SHORT_NAME_COLLECTION = $$x("//div[@class = 'subtitle3 text-t-primary css-vurnku']");

    //Раздел "Обзор рынков"
    //Подраздел "Меню"
    //Меню "Избранное"
    //*MARKET_SECTOR_FAVORITE_BUTTON_XPATH;
    //Кнопка "Добавить в избранное"
    //*ADD_TO_MARKET_SECTOR_FAVORITE_BUTTON_XPATH;
    //Получить название криптовалюты до слеша
    //*GET_BEFORE_SLASH_CRYPTO_NAME;
    //Меню "Все криптовалюты"
    //Кнопка перехода на страницу первой в списке криптовалюты
    //*FIRST_CRYPTOCURRENCY_LINK_XPATH;
    //Найти в списке криптовалюту с названием 'BTC'
    //*FIND_BTC_IN_ALL_CRIPTOCURRENCIES;
    //Меню "Рынок спот/маржа"
    //*MARKETS_SPOT_MARGIN_USDT_XPATH;
    //Кнопка "Добавить в избранное(звездочка)"
    //*ADD_TO_FAVORITE_STAR_CSS_SELECTOR;
    //Меню "Фьючерсные рынки"
    //
    //Меню "Новые листинги"
    //*MARKET_SECTOR_NEW_LISTINGS_BUTTON_XPATH;
    //Коллекция названий колонок у криптоактива
    //*NEW_LISTINGS_COLUMN_NAMES_COLLECTION;
    //Коллекция коротких названий криптоактивов
    //*NEW_LISTINGS_CRYPTO_SHORT_NAME_COLLECTION;
    //Коллекция цен криптоактивов
    //*NEW_LISTINGS_CRYPTO_PRICES_COLLECTION;
    //Меню "Зоны"
    //

    public MarketsPage(SelenideElement path){
        path.click();
    }

    @Step
    public String getBeforeSlashCryptoName(SelenideElement element){
        String result = element.text();
        return result;
    }

}
