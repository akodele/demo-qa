package tests;

import com.codeborne.selenide.Configuration;
import helpers.Attach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.RegistrationPage;
import pages.components.RegistrationResultsModal;

public class TestBase {

    RegistrationPage registrationPage=new RegistrationPage();
    RegistrationResultsModal registrationResultsModal=new RegistrationResultsModal();
    @BeforeAll
    static void beforeAll() {
        Configuration.holdBrowserOpen = true;
        Configuration.browser=System.getProperty("browser","chrome");
        Configuration.browserVersion=System.getProperty("browserVersion","100.0");
        Configuration.browserSize = System.getProperty("browserSize","1920x1080");
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.remote = System.getProperty("selenoidUrl","https://user1:1234@selenoid.autotests.cloud")+"/wd/hub";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;
    }
    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

}
