package com.envision.automation.tests;

import com.envision.automation.framework.core.BaseTest;
import com.envision.automation.framework.util.DataGenereator;
import com.envision.automation.pages.HomePage;
import com.envision.automation.pages.LandingPage;
import com.envision.automation.pages.LoginPage;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests extends BaseTest {
    @Test

    public void ValidateSuccessfullLoginToApplication() throws IOException, InterruptedException {
JSONObject loginData=jsonUtils.getJsonObject(jsonUtils.mainJsonObj,"loginData") ;
String username=jsonUtils.getJsonObjectValue(loginData,"username");
String password=jsonUtils.getJsonObjectValue(loginData,"password");
String loginName= jsonUtils.getJsonObjectValue(loginData,"loginName");

LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage = landingPage.launchAutomationPractiseApplication().clickOnSign();

        HomePage homePage = loginPage.enterUsername(username).enterPassword(password).clickOnSignIn();
        homePage.checkIfSignOutDisplayed()
                .CheckIfUserLoggedInIsValid(loginName);
    }
    @Test
    public void ValidateSuccessfullLoginUsingRandomData() throws IOException, InterruptedException {
        System.out.println(DataGenereator.getUsername()+"a"+DataGenereator.getPassword()+"a"+DataGenereator.getLoginName());

        LandingPage landingPage = new LandingPage(driver);
        LoginPage loginPage = landingPage.launchAutomationPractiseApplication().clickOnSign();

        HomePage homePage = loginPage.enterUsername(DataGenereator.getUsername()).enterPassword(DataGenereator.getPassword()).clickOnSignIn();
        homePage.checkIfSignOutDisplayed()
                .CheckIfUserLoggedInIsValid(DataGenereator.getLoginName());
    }}