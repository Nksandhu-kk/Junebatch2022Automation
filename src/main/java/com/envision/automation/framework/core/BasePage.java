package com.envision.automation.framework.core;

import com.envision.automation.framework.util.ConfigLoader;
import com.envision.automation.framework.util.ORUtils;
import com.envision.automation.framework.util.Reporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
   static WebDriver baseDriver;

   public BasePage(WebDriver driver){

       this.baseDriver=driver;
   }

   public static WebDriver launchBrowser(String browserType){
       try {
           if (browserType.equalsIgnoreCase("Chrome")) {
               System.setProperty("webdriver.chrome.driver", ConfigLoader.getChromeDriverPath());
               baseDriver = new ChromeDriver();
           } else if (browserType.equalsIgnoreCase("edge")) {
               System.setProperty("webdriver.edge.driver", ConfigLoader.getEdgeDriverPath());
               baseDriver = new EdgeDriver();
           } else {
               throw new UnsupportedOperationException("BrowserType [" + browserType + "]is not supported");
           }
           baseDriver.manage().timeouts().pageLoadTimeout(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
           baseDriver.manage().timeouts().implicitlyWait(ConfigLoader.getWaitTime(), TimeUnit.SECONDS);
           baseDriver.manage().window().maximize();
           Reporter.logPassedStep("Browser["+ browserType+"] launched and maximized succesfully");
       }catch (Exception e){
Reporter.logFailedStep("Unable to launch browser["+ browserType+"], exception occured:"+ e);
        e.printStackTrace();
       }
        return  baseDriver;
   }
   public void launchUrl(String url){
       try {
           this.baseDriver.get(url);
           Reporter.logPassedStep("Url ["+ url+"] launched successfully");
       }catch (Exception e){
           Reporter.logFailedStep("Unable to launch url ["+ url+"] ");
       }
   }
   public By getFindBy(String propertyName) throws IOException {
       By by=null;
       try {
           ORUtils.loadORFile();
           String orElementValue = ORUtils.getORPropertyValue(propertyName);
           String byMode = orElementValue.split("#", 2)[0];
           String byValue = orElementValue.split("#", 2)[1];

           if (byMode.equalsIgnoreCase("id")) {
               by = By.id(byValue);
           } else if (byMode.equalsIgnoreCase("name")) {
               by = By.name(byValue);
           } else if (byMode.equalsIgnoreCase("class")) {
               by = By.className(byValue);
           } else if (byMode.equalsIgnoreCase("tagName")) {
               by = By.tagName(byValue);
           } else if (byMode.equalsIgnoreCase("css")) {
               by = By.cssSelector(byValue);
           } else if (byMode.equalsIgnoreCase("xpath")) {
               by = By.xpath(byValue);
           } else if (byMode.equalsIgnoreCase("lt")) {
               by = By.linkText(byValue);
           } else if (byMode.equalsIgnoreCase("plt")) {
               by = By.partialLinkText(byValue);
           } else {
               throw new UnsupportedOperationException("ByMode value is not supported, check the OR.properties and pass supported values only ");
           }

       }catch (Exception e) {
           Reporter.logFailedStep("Unable to get by locator["+ by+"]");
       }

       return by;
   }
   public WebElement findWebElement(String orElement) throws IOException {
       WebElement element=null;
try {
    By by = getFindBy(orElement);
   // element = this.baseDriver.findElement(by);
    element=new WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.presenceOfElementLocated(by));
    element=new WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.visibilityOfElementLocated(by));
Reporter.logPassedStep("OR Element["+ orElement+"] found successfully");
}catch (Exception e) {
Reporter.logFailedStep("Unable to find the element["+ orElement+"]");
}
      return  element;
   }
   public List<WebElement> findWebElements(String orElement) throws IOException {
       List<WebElement> elements=null;
       try {
           By by = getFindBy(orElement);
            //elements = this.baseDriver.findElements(by);
           elements=new WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
           Reporter.logPassedStep(" Elements["+ orElement+"] found successfully");
       }catch (Exception e){
           Reporter.logFailedStep("Unable to find the elements["+ orElement+"]");
       }
       return elements;
}
public void clickOn(String elementName) throws IOException {
       try{
       WebElement element=findWebElement(elementName);
       new  WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.elementToBeClickable(element));
       element.click();
       Reporter.logPassedStep("Clicked on element["+ elementName+"]");
}catch (Exception e) {
           Reporter.logFailedStep("Unable to click on element["+ elementName+"]");
       }
       }
public void typeInto(String elementName,String contentToType) throws IOException, InterruptedException {
   try{
    WebElement element=findWebElement(elementName);
       new  WebDriverWait(baseDriver,ConfigLoader.getWaitTime()).until(ExpectedConditions.elementToBeClickable(element));
element.click();
Thread.sleep(100);
element.clear();
    Thread.sleep(100);
element.sendKeys(contentToType);
Reporter.logPassedStep("Type["+ contentToType+"] into element["+ elementName+"]");
}catch (Exception e){
       Reporter.logFailedStep("Unable to Type["+ contentToType+"] into element["+ elementName+"]");
   }
}
public String getWebElementText(String elementName) throws IOException {
    WebElement element=null;
    String text="";
       try {
           element = findWebElement(elementName);
           text= element.getText();
           Reporter.logPassedStep("Fetched Text["+ text +"] from element ["+ elementName+"]");
       }catch (Exception e) {
Reporter.logFailedStep("Unable to fetch text from element["+ elementName+"]");
       }

    return text;
}
public String getWebElementAttribute(String elementName,String attributeType) throws IOException {
    WebElement element=null;
    String attributeValue=null;
       try {
        element = findWebElement(elementName);
        attributeValue=element.getAttribute(attributeType);
    Reporter.logPassedStep("Fetched attribute["+ attributeType+"] value["+ attributeValue+"] ");
       }catch (Exception e) {
           Reporter.logPassedStep("unable to Fetch attribute["+ attributeType+"] value ");
    }

    return attributeValue;
}

public static void  closebrowser() {
    try {
        baseDriver.close();
        Reporter.logPassedStep("Closed browser succesfully");
    } catch (Exception e) {
        Reporter.logFailedStep("Unable to Close browser succesfully");
    }
}
public void closeAllBrowsers(){
       try {
           this.baseDriver.quit();
           Reporter.logPassedStep("Closed all browser and terminated driver session");
       }catch (Exception e) {
           Reporter.logFailedStep("Unable to Close browser and terminated driver session");
       }
}
public void refreshPage(){
       try {
           this.baseDriver.navigate().refresh();
           Reporter.logPassedStep("Web page refreshed");
       }catch (Exception e){
           Reporter.logFailedStep("Web page failed to refresh");

       }
}
public String getCurrentUrl(){
       String url=null;
       try{
           url=baseDriver.getCurrentUrl();
           Reporter.logPassedStep("Current urll of page is "+ url);
       }catch (Exception e){
           Reporter.logFailedStep("Unable to fetch current url of page");

       }
       return url;
}
    public String getTitlePage(){
        String title=null;
        try{
            title=baseDriver.getTitle();
            Reporter.logPassedStep("Title of page is:"+ title);
        }catch (Exception e){
            Reporter.logFailedStep("Unable to fetch title of page");

        }
        return title;
    }

public void selectfromDropdown(String elementName,String how, String valueToSelect) throws IOException {
    try {
        WebElement element = findWebElement(elementName);
        Select dropDownList = new Select(element);

        if (how.equalsIgnoreCase("value")) {
            dropDownList.selectByValue(valueToSelect);
        } else if (how.equalsIgnoreCase("visibleText")) {
            dropDownList.selectByVisibleText(valueToSelect);
        } else if (how.equalsIgnoreCase("index")) {
            dropDownList.selectByIndex(Integer.parseInt(valueToSelect));
        }
        Reporter.logPassedStep(" VAlue["+ valueToSelect+"] selected from dropdown["+ elementName+"]");
    }catch (Exception e){
        Reporter.logFailedStep(" Unable to select VAlue["+ valueToSelect+"] selected from dropdown["+ elementName+"]");
    }
}
public void selectIndexvaluefromDropDown(String elementName, String value) throws IOException {
       try {
           selectfromDropdown(elementName, "index", value);
       }catch (Exception e){

       }

}
    public void selectVisiblevaluefromDropDown(String elementName, String value) throws IOException {
       try {
           selectfromDropdown(elementName, "visibleText", value);
       }catch (Exception e){

       }

    }
    public void selectValuefromDropDown(String elementName, String value) throws IOException {
        try {
            selectfromDropdown(elementName, "value", value);
        }catch (Exception e){

        }
    }
    public boolean isDisplayed(String elementName) throws IOException {
        WebElement element=null;
       try {
          element = findWebElement(elementName);
          Reporter.logPassedStep("Element ["+ elementName+"] displayed successfully");
      }catch (Exception e) {
Reporter.logFailedStep("Element ["+ elementName+"] not displayed");
      }
       return element.isDisplayed();

    }
}