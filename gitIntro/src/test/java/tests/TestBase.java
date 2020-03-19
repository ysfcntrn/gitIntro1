package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;
import utils.Driver;

import java.util.concurrent.TimeUnit;

public class TestBase {

    public static WebDriver driver;
    public static SoftAssert softAssert;
    public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports reports;
    public static ExtentTest extentTest;
    @BeforeTest
    public void setDriver(){

        driver = Driver.getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10 , TimeUnit.SECONDS);
        softAssert = new SoftAssert();
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/myReport.html");
        htmlReporter.config().setDocumentTitle("OpenMrs Automation Report");
        htmlReporter.config().setReportName("Registration Functionality Validation");
        htmlReporter.config().setTheme(Theme.DARK);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);
        reports.setSystemInfo("OS", "MAC OS");
        reports.setSystemInfo("Environment", "Test Environment");
        reports.setSystemInfo("Domain", "https://demo.openmrs.org/openmrs/login.htm");

    }

    @AfterTest
    public void tearDown(){

        if (driver!=null){
            reports.flush();
            driver.quit();

        }
    }
}
