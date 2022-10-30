package com.envision.automation.framework.util;

import com.relevantcodes.extentreports.LogStatus;

public class Reporter {
    public static void logPassedStep(String message){
ExtentTestManager.getTest().log(LogStatus.PASS,message);
    }

    public static void logFailedStep(String message){
        ExtentTestManager.getTest().log(LogStatus.FAIL,message);

    }
}
