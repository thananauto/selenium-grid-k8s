package com.qa.tests.base;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.qa.report.ExtentTestManager;
import com.qa.utility.ConfigFactory;
import com.qa.utility.ConfigReader;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

import static com.codeborne.selenide.Selenide.open;

public class BaseTestClass implements BeforeAllCallback, BeforeTestExecutionCallback, AfterAllCallback, AfterTestExecutionCallback{

    static ConfigReader configReader = ConfigFactory.configReader;
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        overrideSelenideConf();
         }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        init();
        ExtentTestManager.startTest(context.getTestMethod().get().getName(),context.getDisplayName());


    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        OnTestRun(context);
        teardown();
        ExtentTestManager.removeTest();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        ExtentTestManager.flush();
    }


    public void init(){
        open(configReader.baseUrl());
    }

    public  void teardown(){
        Selenide.closeWebDriver();
    }


    public static void overrideSelenideConf(){
        Configuration.browser = configReader.browser();
        Configuration.timeout = configReader.timeout();
        Configuration.pageLoadTimeout = configReader.pageLoadTimeOut();
        Configuration.reportsFolder = configReader.reportFolder();
        Configuration.downloadsFolder = configReader.downloadsFolder();
        Configuration.savePageSource = configReader.savePageSource();
        Configuration.screenshots = configReader.takeScreenShots();
        Configuration.remote = configReader.remoteUrl();
    }

    public  <T> T getNewInstance(Supplier<T> t){
        return  t.get();
    }

    @SneakyThrows
    public void OnTestRun(ExtensionContext context){
        ExtentTest extentTest = ExtentTestManager.getTest();

        String tagName = context.getTestMethod().get().getAnnotation(Tag.class).value();
        extentTest.assignCategory(tagName);
        if(context.getExecutionException().isPresent()){
            String exceptionName = context.getExecutionException().get().getClass().getSimpleName();
            extentTest.assignCategory(exceptionName);
            extentTest.log(Status.FAIL, context.getTestMethod().get().getName());
            for(int i=0;i<context.getTestMethod().get().getParameters().length;i++){
                extentTest.log(Status.FAIL,context.getTestMethod().get().getParameters()[i].toString());
            }

            WebDriver driver = WebDriverRunner.getWebDriver();
            //Take base64Screenshot screenshot for extent reports
            String path = getScreenshot(driver, context.getTestMethod().get().getName());
            extentTest.log(Status.FAIL, context.getExecutionException().toString(), MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        }
        else if(!context.getExecutionException().isPresent()){
            extentTest.log(Status.PASS, context.getDisplayName());
            //extentTest.log(Status.PASS,"TestClass:"+result.getClass().getName()+" : "+result.getMethod().getDescription());
        }



    }

    @SneakyThrows
    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        //after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination =  "screenshot/"+screenshotName+dateName+".png";
        File finalDestination = new File(System.getProperty("user.dir")+"/target/reports/"+destination);
        FileUtils.copyFile(source, finalDestination);
        //Returns the captured file path
        return destination;
    }


}
