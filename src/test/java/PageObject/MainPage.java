package PageObject;

import Base.BasePage;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;


public class MainPage extends BasePage{
    //Кнопка закрытия всплывающего аллерта с куки
    public static SelenideElement ACCEPT_COOKIE = $x("//button[@id = 'onetrust-accept-btn-handler']");


    public MainPage(String url){
        Selenide.open(url);
    }

}
