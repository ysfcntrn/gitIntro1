package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Driver {
    private static WebDriver driver;

    private Driver(){}

    public static WebDriver getDriver(){

     if (driver == null){
         String driverName = "DriverName";
         switch (ConfigReader.getProperty("browser")){
             case "firefox":
                 WebDriverManager.firefoxdriver().setup();
                 driver = new FirefoxDriver();

                 return driver;
             default:
                 WebDriverManager.chromedriver().setup();
                 driver = new ChromeDriver();

                 return driver;
         }
     }           return driver;

    }
}
