package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import pages.RegistrationPage;
import pages.components.RegistrationResultsModal;

public class TestBase {

    RegistrationPage registrationPage=new RegistrationPage();
    RegistrationResultsModal registrationResultsModal=new RegistrationResultsModal();
    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "https://demoqa.com";
    }

}
