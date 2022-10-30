package com.envision.automation.framework.core;

import com.envision.automation.framework.util.ConfigLoader;
import com.envision.automation.framework.util.ExtentManager;
import com.envision.automation.framework.util.ExtentTestManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;
import java.lang.reflect.Method;

public class BaseTest {
    public WebDriver driver;
    public BasePage basePage;
    @BeforeSuite
    public void loadConfigurations() throws IOException {
        ExtentManager.getReporter();
        ConfigLoader.loadConfigurations();

    }

    @BeforeMethod
    public void loadBrowser(Method methodName){
        ExtentTestManager.startTest(methodName.getName(),"");
        this.driver=BasePage.launchBrowser(ConfigLoader.getBrowserType());

    }
    @AfterMethod
    public void testDownBrowser(){
        BasePage.closebrowser();
        ExtentTestManager.stopTest();
        //basePage.closebrowser();
    }

    @AfterSuite
    public void testDownConfigurations(){
        driver=null;
ExtentManager.getReporter().flush();
ExtentManager.getReporter().close();
    }
}
