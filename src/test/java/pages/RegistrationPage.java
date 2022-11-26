package pages;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.selector.ByText;
import org.openqa.selenium.Keys;
import pages.components.CitySelection;
import pages.components.StateSelection;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationPage {

    public RegistrationPage openRegistrationPage(){
        //открываем страницу и убираем рекламный баннер
        open("/automation-practice-form");
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");
        return this;
    }

    private static SelenideElement
            firstName=$x("//input[@id='firstName']"),
            lastName=$x("//input[@id='lastName']"),
            email=$x("//input[@id='userEmail']"),
            genderRadioButton=$("#genterWrapper"),
            mobileNumber=$x("//input[@id='userNumber']"),
            dateOfBirthInput=$x("//input[@id='dateOfBirthInput']"),
            subjects=$x("//input[@id='subjectsInput']"),
            hobbies=$("#hobbiesWrapper"),
            picture=$x("//input[@id='uploadPicture']"),
            currentAddress=$x("//textarea[@id='currentAddress']"),
            buttonSubmit=$x("//*[@id='submit']/parent::div");

    StateSelection stateSelection=new StateSelection();
    CitySelection citySelection=new CitySelection();

    public RegistrationPage setFirstName(String nameText) {
        firstName.setValue(nameText);
        return this;
    }

    public RegistrationPage setLastName(String surnameText) {
        lastName.setValue(surnameText);
        return this;
    }

    public RegistrationPage setEmail(String emailText) {
        email.setValue(emailText);
        return this;
    }

    public RegistrationPage setGender(String genderText) {
        genderRadioButton.$(byText(genderText)).click();
        return this;
    }

    public RegistrationPage setPhoneNumber(String phoneText) {
        mobileNumber.setValue(phoneText);
        return this;
    }

    public RegistrationPage setDateOfBirth(LocalDate dateBirth) {
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        dateOfBirthInput.val(dateBirth.format(dateTimeFormatter));
        dateOfBirthInput.sendKeys(Keys.HOME,
                Keys.DELETE,Keys.DELETE,Keys.DELETE,
                Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.DELETE,
                Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.ENTER);
        return this;
    }

    public RegistrationPage setSubject(String subject) {
        subjects.setValue(subject).pressEnter();
        return this;
    }

    public RegistrationPage setHobbies(String hobbyName) {
        hobbies.$(byText(hobbyName)).click();
        return this;
    }

    public RegistrationPage setPicture(File fileName) {
        picture.uploadFile(fileName);
        return this;
    }

    public RegistrationPage setCurrentAddress(String currentAddressText) {
        currentAddress.setValue(currentAddressText);
        return this;
    }

    public RegistrationPage setStateAndCity(String stateName, String cityName){
        stateSelection.selectState(stateName);
        citySelection.selectCity(cityName);
        return this;
    }

    public void submit() {
        buttonSubmit.click();
    }

}
