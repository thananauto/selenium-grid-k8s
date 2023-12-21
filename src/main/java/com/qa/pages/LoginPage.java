package com.qa.pages;

import com.aventstack.extentreports.ExtentTest;
import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import com.qa.report.ExtentTestManager;
import lombok.SneakyThrows;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    public LoginPage(){}


    private final SelenideElement USERNAME= $(byName("username"));
    private final SelenideElement PASSWORD= $(byAttribute("placeholder","Password"));
    private final SelenideElement LOGIN= $(byAttribute("type", "submit"));
    private final SelenideElement INVALID_CREDENTIAL = $(".oxd-alert-content--error");

    private ExtentTest extentTest = ExtentTestManager.getTest();

    public HomePage logIntoApplication(String username, String password){
        USERNAME.should(enabled).setValue(username);
        extentTest.info("Enter the username: "+username);
        PASSWORD.should(visible).setValue(password);
        extentTest.info("Enter the password: "+password);
        LOGIN.should(enabled).click(ClickOptions.usingJavaScript());
        extentTest.info("click on the login button");
        return new HomePage();

    }

    public void validateInvalidCredentials(String username, String password){
        logIntoApplication(username, password);
        INVALID_CREDENTIAL.shouldBe(visible).shouldHave(text("Invalid credentials"));
        extentTest.info("Error should be displayed as Invalid credentials");

    }

    @SneakyThrows
    public <T> T getInstanceOfPage(Class<T> clazz)  {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
