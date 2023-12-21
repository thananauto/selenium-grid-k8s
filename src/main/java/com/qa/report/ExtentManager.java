package com.qa.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.util.Objects;

 class ExtentManager {
    public static  ExtentReports extentReports ;

    public synchronized static ExtentReports createExtentReports() {

        if(Objects.nonNull(extentReports)){
            return extentReports;
        }
        extentReports = new ExtentReports();
        ExtentSparkReporter reporter = new ExtentSparkReporter("target/reports/index.html");
        reporter.config().setReportName("Reqres Summary Report");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Blog Name", "QA Test");
        extentReports.setSystemInfo("Author", "Thanan");
        return extentReports;
    }
}
