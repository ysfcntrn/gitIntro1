package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Driver;

import java.util.List;

public class OpenMRSHomePage {

    public OpenMRSHomePage(){

        PageFactory.initElements(Driver.getDriver() , this);
    }

    @FindBy(xpath ="//h4")
    public WebElement textMessage;

    @FindBy (xpath = "//div[@id='apps']//a")
    public List<WebElement> homePageApps;

    public void clickApp(String name){
        for (WebElement element:homePageApps
             ) {
           if(element.getText().trim().toLowerCase().contains(name)){
               element.click();
               break;
           }

        }
    }

}
