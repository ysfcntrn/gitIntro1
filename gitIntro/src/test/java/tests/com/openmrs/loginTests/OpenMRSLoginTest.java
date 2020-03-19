package tests.com.openmrs.loginTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.OpenMrsLoginPage;
import tests.TestBase;
import utils.BrowserUtils;
import utils.ConfigReader;

import javax.naming.OperationNotSupportedException;
import java.util.concurrent.TimeUnit;

public class OpenMRSLoginTest extends TestBase {

    @Test
    public void testLogin(){
        driver.get("https://demo.openmrs.org/openmrs/login.htm");
        OpenMrsLoginPage page = new OpenMrsLoginPage();

        String userName = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");
        page.setLogIn(userName , password);
       // BrowserUtils.waitForSec(5);

        String expeectedTitle="Home";

       // driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);


        String actualTitle = driver.getTitle();

        Assert.assertEquals(actualTitle , expeectedTitle);
    }

}
