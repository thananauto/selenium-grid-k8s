package com.qa.report;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent  = ExtentManager.createExtentReports();


    public static synchronized ExtentReports getExtentReport(){
        return extent;
    }

    /**
     * This will return the current test method thread ID
     * @return
     */
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    /**
     * This method will execute at start of the test
     * @param testName
     * @param desc
     * @return
     */
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized void flush(){
        extent.flush();


    }

    public static void removeTest() {
        extentTestMap.remove((int) Thread.currentThread().getId());
    }
}
