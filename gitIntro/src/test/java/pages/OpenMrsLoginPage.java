package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.BrowserUtils;
import utils.Driver;

public class OpenMrsLoginPage {

    public OpenMrsLoginPage(){

        PageFactory.initElements(Driver.getDriver() , this);

    }

    @FindBy(id = "username")
    public WebElement userName;

    @FindBy(id = "password")
    public WebElement password;

    @FindBy(id = "Pharmacy")
    public WebElement pharmacy;

    @FindBy(id = "loginButton")
    public WebElement logIn;

    public void setLogIn(String userName , String password){
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        pharmacy.click();
        logIn.click();

    }



}
