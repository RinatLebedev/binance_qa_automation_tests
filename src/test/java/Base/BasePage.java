package Base;


import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class BasePage extends BaseTest{

    public static String getCurrentURL(){
        String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
        return currentUrl;
    }

    public void buttonClicker(SelenideElement element){
        element.shouldBe(visible,enabled,exist).click();
    }
    public void buttonCssSelectorClicker(String selector){
        $(selector).shouldBe(visible,enabled,exist).click();
    }
    public void setSendKeys(SelenideElement element, String text){
        element.shouldBe(visible,enabled,exist).sendKeys(text);
    }
    public void scrollToElement(SelenideElement element){
        element.shouldBe(visible,enabled,exist).scrollTo();
    }

}
