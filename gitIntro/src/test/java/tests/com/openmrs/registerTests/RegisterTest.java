package tests.com.openmrs.registerTests;

import com.aventstack.extentreports.Status;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.OpenMRSHomePage;
import pages.OpenMRSRegisterPage;
import pages.OpenMrsLoginPage;
import tests.TestBase;
import utils.BrowserUtils;
import utils.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class RegisterTest extends TestBase {
    OpenMRSRegisterPage registerPage=new OpenMRSRegisterPage();
    OpenMRSHomePage homePage=new OpenMRSHomePage();
    @DataProvider(name = "patientRegistration")
    public Object[][] getPatientInfo(){
        return new Object[][] {
                {"Robert","Smith","Male","10","April","2001","2200 Devon Ave","222346678","Child","David" },
                {"Maria","Garcia","Female","14","January","1999","Chicago IL","875456789","Sibling","Obama"},
                {"James", "Anderson","Male","22","March","1980","Des Plaines IL","23567576","Supervisor","Jennifer"}
        };
    }
    @BeforeClass
    public void logInMRS(){
        OpenMrsLoginPage loginPage=new OpenMrsLoginPage();
        driver.get("https://demo.openmrs.org/openmrs/login.htm");
        loginPage.setLogIn(ConfigReader.getProperty("username"),ConfigReader.getProperty("password"));
        homePage.clickApp("Register a patient");
    }
    @Test
    public void validateTitle(){
        extentTest = reports.createTest("This is for title validation");
        String expectedTitle="OpenMRS Electronic Medical Record";
        String actualTitle=driver.getTitle();
        extentTest.log(Status.INFO,"This is before title validation");
        Assert.assertEquals(actualTitle, expectedTitle);
        extentTest.log(Status.INFO,"Title was validated");
    }
    @Test
    public void validateHeaders(){
        extentTest = reports.createTest("This is for header validation");

        String actualRegisterText=registerPage.registerText.getText().trim();
        String expectedRegisterText="Register a patient";
        softAssert.assertEquals(actualRegisterText, expectedRegisterText);
        extentTest.log(Status.INFO,"register text was validated");
        String actualQuestionText=registerPage.patientNameQuestion.getText().trim();
        String expectedQuestionText="What's the patient's name?";
        softAssert.assertEquals(actualQuestionText, expectedQuestionText);
        extentTest.log(Status.INFO,"Question text was validated");
        softAssert.assertEquals(registerPage.nameHeaders.get(0).getText(),"Given (required)" );

        softAssert.assertEquals(registerPage.nameHeaders.get(1).getText(),"Middle" );
        softAssert.assertEquals(registerPage.nameHeaders.get(2).getText(),"Family Name (required)" );
        softAssert.assertAll();
    }
    @Test
    public void validateDemographics(){
        extentTest = reports.createTest("This is for Demographics validation");

        // --normally it should come from API response or DB
        List<String> expectedHeader=OpenMRSRegisterPage.getData();
        for(int i=0;i<expectedHeader.size();i++){
            String actualHeader=registerPage.demographicsName.get(i).getText();
            softAssert.assertEquals( actualHeader, expectedHeader.get(i) );
        }
        extentTest.log(Status.INFO,"Demographics header was validated");
        softAssert.assertAll();

    }
    @Test(dependsOnMethods = "validateDemographics")
    public void validatePatientRegistration(){

        registerPage.givenName.sendKeys("Adam");
        registerPage.familyName.sendKeys("Lee");
        registerPage.genderLabel.click();
        BrowserUtils.selectByVisibleText(registerPage.genderField,"Male");
        registerPage.birthDateLabel.click();
        registerPage.birthdateDayField.sendKeys("22");
        BrowserUtils.selectByVisibleText(registerPage.birthdateMonthField,"May");
        registerPage.birthdateYearField.sendKeys("2002");
        registerPage.AddressButton.click();
        registerPage.address.sendKeys("2200 Devon Ave");
        registerPage.PhoneNumber.click();
        registerPage.phoneNumberBox.sendKeys("2243002244");
        registerPage.Relatives.click();
        BrowserUtils.selectByVisibleText(registerPage.relationshipType, "Child");
        registerPage.relationShipName.sendKeys("Jackson");
        registerPage.confirmationLabel.click();
        List<String> expectedConfirmData=OpenMRSRegisterPage.getConfirmationData();
        for(int i=0;i<expectedConfirmData.size();i++){
            String actualData=registerPage.confirmationPage.get(i).getText();
            softAssert.assertEquals(actualData,expectedConfirmData.get(i));
        }
        softAssert.assertAll();
        registerPage.submitButton.click();
    }
    @Test(dataProvider = "patientRegistration", dependsOnMethods = "validatePatientRegistration")
    public void validateMultiplePatientRegistration(String name, String familyName, String gender, String day, String month, String year, String address,
                                                    String phoneNumber, String relationShip, String relationName){
        BrowserUtils.waitForSec(2);
        driver.get("https://demo.openmrs.org/openmrs/index.htm");
        homePage.clickApp("Register a patient");
        registerPage.givenName.sendKeys(name);
        registerPage.familyName.sendKeys(familyName);
        registerPage.genderLabel.click();
        BrowserUtils.selectByVisibleText(registerPage.genderField,gender);
        registerPage.birthDateLabel.click();
        registerPage.birthdateDayField.sendKeys(day);
        BrowserUtils.selectByVisibleText(registerPage.birthdateMonthField,month);
        registerPage.birthdateYearField.sendKeys(year);
        registerPage.AddressButton.click();
        registerPage.address.sendKeys(address);
        registerPage.PhoneNumber.click();
        registerPage.phoneNumberBox.sendKeys(phoneNumber);
        registerPage.Relatives.click();
        BrowserUtils.selectByVisibleText(registerPage.relationshipType, relationShip);
        registerPage.relationShipName.sendKeys(relationName);
        registerPage.confirmationLabel.click();
        registerPage.submitButton.click();
    }
    @AfterMethod
    public void takeScreenShot(ITestResult result) throws IOException {
        if(result.getStatus()==ITestResult.FAILURE){
            BrowserUtils.takeScreenShot();
        }
    }
}
