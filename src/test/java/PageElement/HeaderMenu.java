package PageElement;

import Base.BasePage;
import com.codeborne.selenide.SelenideElement;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.codeborne.selenide.Selenide.$x;


public class HeaderMenu extends BasePage {
    public static SelenideElement MARKETS_BUTTON_XPATH = $x("//a[@id = 'ba-tableMarkets']"),
            SQUARE_BUTTON_XPATH = $x("//a[@class = 'header-menu-item_view typography-Subtitle4'][text() = 'Square']"),
            SQUARE_MENU_BLOG_BUTTON_XPATH = $x("//div[@class = 'header-nav-subtitle']/div[text() = 'Блог']"),
            SQUARE_MENU_SQUARE_BUTTON_XPATH = $x("//div[@class = 'header-nav-subtitle']/div[text() = 'Square']"),
            EARN_BUTTON_XPATH = $x("//a[@class = 'header-menu-item_view typography-Subtitle4'][text() = 'Earn']");

    //Кнопка "Рынки"
    //*MARKETS_BUTTON_XPATH;
                                //Меню "Square"
    //*SQUARE_BUTTON_XPATH;
    //Кнопка "Блог"
    //*SQUARE_MENU_BLOG_BUTTON_XPATH;
    //Кнопка "Square"
    //*SQUARE_MENU_BLOG_SQUARE_XPATH;
    //Кнопка "Earn"
    //*EARN_BUTTON_XPATH;
}
