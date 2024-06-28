package PageObject;

import Base.BasePage;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class EarnPage extends BasePage {
    public static SelenideElement EARN_PAGE_WHAT_IS_EARN_BUTTON_XPATH = $x("//button[@class = 'bn-button bn-button__secondary data-size-middle earn-faq-dialog-button']"),
            INPUT_FIND_COIN_INPUT_XPATH = $x("//input[@class = 'bn-textField-input'][@placeholder = 'Найти монету']"),
            CLOSE_POP_UP_WINDOW_CSS_SELECTOR = $(".bn-svg.notification-card-header-close"),
            GET_EARN_PAGE_FIRST_COIN_APR_XPATH = $x("//div[@class = 'bn-flex'][@style = 'align-items: baseline;']/div");
    //Кнопка "Что такое Earn?"
    //*EARN_PAGE_WHAT_IS_EARN_BUTTON_XPATH
    //Поле ввода "Найти монету"
    //*INPUT_FIND_COIN_INPUT_XPATH
    //Кнопка закрытия всплывающее окно справа внизу экрана
    //*CLOSE_POP_UP_WINDOW_CSS_SELECTOR
    //Получение значения "Расч. APR"
    //*GET_EARN_PAGE_FIRST_COIN_APR_XPATH

    public EarnPage(SelenideElement path){
        path.click();
    }
}
