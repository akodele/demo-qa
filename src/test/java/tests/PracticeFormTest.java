package tests;

import com.codeborne.selenide.ElementsCollection;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTest extends TestBase {


    static ElementsCollection
            tableOfResult=$$x("//table/tbody/tr/td[2]");

    @Test
    public void fillingTheFields() throws InterruptedException {
        //переменные
        Faker faker=new Faker();

        LocalDate dateBirth=faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();//LocalDate.of(2000,1,26);
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd' 'MMMM','yyyy", Locale.ENGLISH);

        String nameText=faker.name().firstName(),
                surnameText=faker.name().lastName(),
                emailText=faker.internet().emailAddress(),
                genderText="Female",
                phoneText=faker.phoneNumber().subscriberNumber(10),
                currentAddressText=faker.address().fullAddress(),
                dateBirthForCheck=dateBirth.format(dateTimeFormatter),
                fileName="qa_guru_conflict_merge.png",
                stateName="NCR",
                cityName="Delhi";

        String[] setSubjectTexts={"En","M"},
                checkSubjectTexts={"English","Maths"},
                hobbiesNames={"Reading","Music"};

        File picture=new File("src/test/resources/"+fileName);
        System.out.println("ffffffffffffffffffff");

        registrationPage.
                openRegistrationPage(). //открываем страницу
                setFirstName(nameText). //Имя
                setLastName(surnameText). //Фамилия
                setEmail(emailText). //Почта
                setGender(genderText). //Пол
                setPhoneNumber(phoneText). //Телефон
                setDateOfBirth(dateBirth). //заполняем форму даты рандомными числами (импровизация)
                setSubject(setSubjectTexts[0]).setSubject(setSubjectTexts[1]). //Дисциплины
                setHobbies(hobbiesNames[0]).setHobbies(hobbiesNames[1]). //Хобби
                setPicture(picture). //Картинка
                setCurrentAddress(currentAddressText). //Текущий адрес
                setStateAndCity(stateName,cityName). //stateAndCity
                submit(); //Нажимаем на кнопку Submit


        //проверяем значения таблицы результата
        registrationResultsModal.verifyModalAppears().
            verifyResult("Student Name",nameText+" "+surnameText). //Student Name
            verifyResult("Student Email",emailText). //Student Email
            verifyResult("Gender",genderText). //Gender
            verifyResult("Mobile",phoneText). //Mobile
            verifyResult("Date of Birth",dateBirthForCheck). //Date of Birth
            verifyResult("Subjects",checkSubjectTexts[0]+", "+checkSubjectTexts[1]). //Subjects
            verifyResult("Hobbies",hobbiesNames[0]+", "+hobbiesNames[1]). //Hobbies
            verifyResult("Picture",fileName). //Picture
            verifyResult("Address",currentAddressText). //Address
            verifyResult("State and City",stateName+" "+cityName); //State and City

    }
}
