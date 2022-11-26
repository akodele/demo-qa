package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class StateSelection {

    private static SelenideElement state=$("#state");

    public void selectState(String stateName){
        state.click();
        state.$(byText(stateName)).click();
    }

}
