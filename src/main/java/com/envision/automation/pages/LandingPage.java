package com.envision.automation.pages;

import com.envision.automation.framework.core.BasePage;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class LandingPage extends BasePage {
    WebDriver driver;
    public  LandingPage(WebDriver driver){
        super(driver);
        this.driver=driver;
    }
    public LandingPage launchAutomationPractiseApplication(){
        launchUrl("http://automationpractice.com/index.php");
        return  this;
    }
 public  LoginPage clickOnSign() throws IOException {
     clickOn("LandingPage.linkSignIn");
     return new LoginPage(this.driver);
 }
}
