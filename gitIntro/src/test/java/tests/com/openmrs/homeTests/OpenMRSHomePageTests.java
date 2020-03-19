package tests.com.openmrs.homeTests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.OpenMRSHomePage;
import pages.OpenMrsLoginPage;
import tests.TestBase;
import utils.ConfigReader;

import java.util.List;

public class OpenMRSHomePageTests extends TestBase {

    OpenMRSHomePage page = new OpenMRSHomePage();
    @BeforeClass
    public void logIn(){
        OpenMrsLoginPage loginPage = new OpenMrsLoginPage();
        driver.get("https://demo.openmrs.org/openmrs/login.htm");
        loginPage.setLogIn(ConfigReader.getProperty("username"), ConfigReader.getProperty("password"));
    }

    @Test
    public void validateMessage(){

        String expectedMessage = "Logged in as Super User (admin) at Pharmacy.";
        String actualMessage = page.textMessage.getText();

    }
    @Test
    public void calidateURL(){
        String expectedURL = "https://demo.openmrs.org/openmrs/referenceapplication/home.page";
        String actualMessage = driver.getCurrentUrl();
        Assert.assertEquals(actualMessage , expectedURL);
    }
    @Test
    public void validateHomeApps(){

        for (WebElement element:page.homePageApps
             ) {
            Assert.assertTrue(element.isDisplayed());
        }

    }

}
