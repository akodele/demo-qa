package pages.components;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CitySelection {

    private static SelenideElement city=$("#city");

    public void selectCity(String cityName){
        city.click();
        city.$(byText(cityName)).click();
    }

}
