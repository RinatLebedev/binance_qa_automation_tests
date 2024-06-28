package PageObject;

import Base.BasePage;
import Base.BaseTest;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class TradingPage extends BasePage {

    public static SelenideElement EDITING_THE_DISPLAY_TIMEFRAMES_CSS_SELECTOR = $(".css-423ckm"),
            BUTTON_EDIT_IN_EDITING_THE_DISPLAY_TIMEFRAMES_XPATH = $("//button[@class = ' css-1lwldgz']"),
            BUTTON_TECHNICAL_INDICATOR = $(".css-bt1pje"),
            BUTTON_SAVE_IN_TECHNICAL_INDICATOR_MENU = $(".css-1463suk");
    public static ElementsCollection VISIBLE_TIMEFRAME_COLLECTION = $$x("//div[@class = 'css-e2pgpg']/div"),
            TIMEFRAMES_IN_REDACTOR_COLLECTION_XPATH = $$x("//label[contains(@class, 'interval-option css')]/div[@data-bn-type = 'text']"),
            TECHNICAL_INDICATOR_ELEMENTS_COLLECTION = $$(".css-1uqhpi8 label.css-1il010j");




            //Временные промежутки(треугольник)
    //*EDITING_THE_DISPLAY_TIMEFRAMES_CSS_SELECTOR
    //Кнопка "Редактировать" внутри всплывающего меню временных промежутков
    //*BUTTON_EDIT_IN_EDITING_THE_DISPLAY_TIMEFRAMES_XPATH
    //XPATH коллекции элементов внутри треугольника
    //*TIMEFRAMES_IN_REDACTOR_COLLECTION_XPATH
    //Коллеция временных промежутков на странице с криптоактивом
    //*VISIBLE_TIMEFRAME_COLLECTION
            //Технический индикатор
    //*BUTTON_TECHNICAL_INDICATOR
    //Коллекция с меню "технический индикатор"
    //*TECHNICAL_INDICATOR_ELEMENTS_COLLECTION


    public TradingPage(SelenideElement path){
        path.click();
    }
}
