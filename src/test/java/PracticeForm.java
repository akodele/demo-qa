
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.commands.PressEnter;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PracticeForm {
    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
    }
    private final String HOME_PAGE_URL="/automation-practice-form";
    static SelenideElement
            firstName=$x("//input[@id='firstName']"),
            lastName=$x("//input[@id='lastName']"),
            email=$x("//input[@id='userEmail']"),
            genderRadioButton=$x("//input[contains(@value,'Female')]/parent::div"),
            mobileNumber=$x("//input[@id='userNumber']"),
            dateOfBirthInput=$x("//input[@id='dateOfBirthInput']"),
            subjects=$x("//input[@id='subjectsInput']"),
            hobbiesReading=$x("//label[text()='Reading']"),
            hobbiesMusic=$x("//label[text()='Music']"),
            picture=$x("//input[@id='uploadPicture']"),
            currentAddress=$x("//textarea[@id='currentAddress']"),
            state=$x("//div[text()='Select State']"),
            stateNCR=$x("//div[text()='NCR']"),
            city=$x("//div[text()='Select City']"),
            cityDelhi=$x("//div[text()='Delhi']"),
            buttonSubmit=$x("//*[@id='submit']/parent::div"),
            modalResult=$x("//div[@class='modal-content']");
    static ElementsCollection
            tableOfResult=$$x("//table/tbody/tr/td[2]");

    @Test
    public void fillingTheFields() throws InterruptedException {
        //переменные
        String nameText="Akerke";
        String surnameText="Kalibekova";
        String emailText="akalibekova@jmart.kz";
        String phoneText="7777777777";
        String currentAddressText="Almaty, Al-Farabi, 36";

        //открываем страницу и убираем рекламный баннер
        open(HOME_PAGE_URL);
        executeJavaScript("$('footer').remove()");
        executeJavaScript("$('#fixedban').remove()");

        //заполняем формы
        //Имя
        if(firstName.isDisplayed()){
            firstName.setValue(nameText);
        }
        //Фамилия
        if(lastName.isDisplayed()){
            lastName.setValue(surnameText);
        }
        //Почта
        if(email.isDisplayed()){
            email.setValue(emailText);
        }
        //Пол
        if(genderRadioButton.isDisplayed()){
            genderRadioButton.click();
        }
        //Телефон
        if(mobileNumber.isDisplayed()){
            mobileNumber.setValue(phoneText);
        }
        //заполняем форму даты рандомными числами (импровизация)
        Faker faker=new Faker();
        LocalDate dateBirth=faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//LocalDate.of(2000,1,26);
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
        if(dateOfBirthInput.isDisplayed()){
            dateOfBirthInput.val(dateBirth.format(dateTimeFormatter));
            dateOfBirthInput.sendKeys(Keys.HOME,
                    Keys.DELETE,Keys.DELETE,Keys.DELETE,
                    Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.DELETE,
                    Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.DELETE,Keys.ENTER);
        }
        //Дисциплины
        if (subjects.isDisplayed()){
            subjects.setValue("En").pressEnter();
            subjects.setValue("M").pressEnter();
        }
        //Хобби
        if (hobbiesReading.isDisplayed()){
            hobbiesReading.click();
        }
        if (hobbiesMusic.isDisplayed()){
            hobbiesMusic.click();
        }
        //Картинка
        if (picture.isDisplayed()){
            picture.uploadFile(new File("src/test/resources/qa_guru_conflict_merge.png"));
        }
        //Текущий адрес
        if (currentAddress.isDisplayed()){
            currentAddress.setValue(currentAddressText);
        }
        //stateAndCity
        if (state.isDisplayed()){
            state.click();
            stateNCR.click();
            if (city.isEnabled()){
                city.click();
                cityDelhi.click();
            }
        }
        //Нажимаем на кнопку Submit
        if(buttonSubmit.isDisplayed()){
            buttonSubmit.scrollTo().submit();
        }




        //проверяем значения таблицы результата
        if (modalResult.isDisplayed()){
            tableOfResult.get(0).shouldHave(text(nameText+" "+surnameText));//Student Name
            tableOfResult.get(1).shouldHave(text(emailText));//Student Email
            tableOfResult.get(2).shouldHave(text("Female"));//Gender
            tableOfResult.get(3).shouldHave(text(phoneText));//Mobile

            String dateOfBirthForEquals=tableOfResult.get(4).getText();
            DateTimeFormatter dateTimeFormatterReverse=DateTimeFormatter.ofPattern("dd' 'MMMM','yyyy", Locale.ENGLISH);
            LocalDate dateBirthReverse=LocalDate.parse(dateOfBirthForEquals, dateTimeFormatterReverse);
            assertEquals(dateBirthReverse, dateBirth);//Date of Birth

            tableOfResult.get(5).shouldHave(text("English"+", "+"Maths"));//Subjects
            tableOfResult.get(6).shouldHave(text("Reading"+", "+"Music"));//Hobbies
            tableOfResult.get(7).shouldHave(text("qa_guru_conflict_merge.png"));//Picture
            tableOfResult.get(8).shouldHave(text("Almaty, Al-Farabi, 36"));//Address
            tableOfResult.get(9).shouldHave(text("NCR Delhi"));//State and City
        }

    }
}
