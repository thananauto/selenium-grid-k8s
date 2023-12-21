package com.qa.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ReportExtent {

    public  ExtentReports extentReports;

    public ReportExtent() {
        extentReports = ExtentManager.createExtentReports();
    }

    public ExtentTest createTest(String testName){
        return extentReports.createTest(testName);
    }


    public  void flush(){
        extentReports.flush();
    }





}
