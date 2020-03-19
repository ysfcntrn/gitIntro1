package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {
    public static void switchWindow(String targetTitle){
        Set<String> allWindows=Driver.getDriver().getWindowHandles();
        if(!Driver.getDriver().getTitle().equals(targetTitle)){
            for(String window : allWindows){
                Driver.getDriver().switchTo().window(window);
                if(Driver.getDriver().getTitle().equals(targetTitle)){
                    break;
                }
            }
        }
    }
    public static void verifyBrokenLink( List<WebElement> links){
        for(WebElement link : links ){
            String hrefValue=link.getAttribute("href");
            try {
                URL url = new URL(hrefValue);
                HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                connect.setConnectTimeout(3000);
                connect.connect();
                if (connect.getResponseCode()>=400 || connect.getResponseCode()>=500) {
                    System.out.println(hrefValue);
                    System.out.println("The link is broken " + connect.getResponseMessage() + " " +
                            connect.getResponseCode());
                }
            }catch (IOException e){
                e.getStackTrace();
            }
        }
    }
    public static void hoverOver(WebElement element){
        Actions actions=new Actions(Driver.getDriver());
        actions.moveToElement(element).build().perform();
    }
    public static void dragAndDrop( WebElement from, WebElement to){
        Actions actions=new Actions(Driver.getDriver());
        actions.dragAndDrop(from,to).build().perform();
    }
    // homework create reusable method to wait visibility of element
    // to wait clickable of element
    public static void waitForVisibility(WebDriver driver , WebElement element, int timeUnit){
        WebDriverWait wait=new WebDriverWait(driver,timeUnit);
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static void takeScreenShot() throws IOException {
        File src=((TakesScreenshot)Driver.getDriver()).getScreenshotAs(OutputType.FILE);
        File desFile=new File("ScreenShotFile/"+System.currentTimeMillis()+".png");
        FileUtils.copyFile(src, desFile);
    }



    public  static WebElement waitForClickable(By locator, int timeUnits){
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(),timeUnits);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    //wait for visibility
    public static WebElement waitForVisiblity(By locator, int timeUnits){
        WebDriverWait wait=new WebDriverWait(Driver.getDriver(),timeUnits);
        return  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    //Fluent wait
    public static WebElement fluentWait(WebElement element,int timeUnits,int pollingTime){
        Wait<WebDriver> wait=new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(timeUnits, TimeUnit.SECONDS)
                .pollingEvery(pollingTime,TimeUnit.SECONDS)
                .ignoring(NoSuchFieldException.class);
        WebElement element1=wait.until(
                driver -> element
        );
        return element1;
    }
    public static void waitForSec(int time){
        time=time*1000;
        try {
            Thread.sleep(time);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
















    }
    public static void selectByVisibleText(WebElement element, String text){
        Select select=new Select(element);
        select.selectByVisibleText(text);
    }

   }
