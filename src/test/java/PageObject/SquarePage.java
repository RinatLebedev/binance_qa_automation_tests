package PageObject;

import Base.BasePage;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class SquarePage extends BasePage {
    public static ElementsCollection SQUADE_PAGE_POSTS_ELEMENTS_COLLECTION_XPATH = $$x("//div[@class = 'feed-content-text css-1t2jijo']/a");
    public SquarePage(SelenideElement path){
        path.click();
    }
}
