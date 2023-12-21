package com.qa.pages;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.qa.entity.Employee;
import com.qa.report.ExtentTestManager;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EmployeeInformationPage {

    private static final SelenideElement ADD_EMPLOYEE_BTN = $(byTagAndText("a","Add Employee"));
    private static final SelenideElement FIRST_NAME = $(byName("firstName"));
    private static final SelenideElement MIDDLE_NAME = $(byName("middleName"));
    private static final SelenideElement LAST_NAME = $(byName("lastName"));
    private static final SelenideElement SAVE = $(byTagAndText("button","Save"));
    private static final SelenideElement IMAGE = $(byAttribute("type","file"));
    private static final SelenideElement SUCCESS = $(byText("Success"));

    private static final SelenideElement PERSONAL_DETAILS = $(byTagAndText("a","Personal Details"));
    private static final SelenideElement CONTACT_DETAILS = $(byTagAndText("a","Contact Details"));
    private static final SelenideElement JOB = $(byTagAndText("a","Job"));
    private static final SelenideElement MY_CLAIMS = $(byTagAndText("a","My Claims"));
    private static final ElementsCollection CLAIMS_ROWS = $$(".oxd-table-body [role='row']");

    private static final SelenideElement VACANCIES = $(byTagAndText("a", "Vacancies"));

    public void checkVacancies(){
        VACANCIES.shouldBe(visible).click();
    }

    private ExtentTest extentTest = ExtentTestManager.getTest();

    public void checkClaimCount(){
        MY_CLAIMS.shouldBe(visible).click();
        extentTest.log(Status.INFO, "Click the My claims");
        CLAIMS_ROWS.should(sizeGreaterThan(1));
        extentTest.log(Status.INFO, "claim row should be greater than 1");
    }

    public void navigateMyInfo(){
        PERSONAL_DETAILS.shouldBe(visible).click();
        extentTest.log(Status.INFO, "Personal details should be visible");
        CONTACT_DETAILS.shouldBe(visible).click();
        extentTest.log(Status.INFO, "Contact details should be visible");
        JOB.shouldBe(visible).click();
        extentTest.log(Status.INFO, "Click on the Job link");

    }
    public EmployeeInformationPage addEmployee(Employee employee){
        ADD_EMPLOYEE_BTN.should(visible).click();
        extentTest.log(Status.INFO, "Click on Add Employee button");
        FIRST_NAME.should(visible).setValue(employee.getFirstName());
        extentTest.log(Status.INFO, "Enter the first name as "+employee.getFirstName());
        MIDDLE_NAME.should(visible).setValue(employee.getMiddleName());
        extentTest.log(Status.INFO, "Enter the middle name as "+employee.getMiddleName());
        LAST_NAME.should(visible).setValue(employee.getLastName());
        extentTest.log(Status.INFO, "Enter the last name as "+employee.getLastName());
        IMAGE.should(enabled).uploadFromClasspath("Design-pattern.png");
        SAVE.should(enabled).click();
        extentTest.log(Status.INFO, "Click on the save button");
        SUCCESS.shouldBe(appear);
        extentTest.log(Status.INFO, "Success message should appear");
        return this;

    }

    public void employeeCreatedSuccessfully(){
        SUCCESS.shouldBe(enabled);
        extentTest.log(Status.INFO, "Success message should be enabled");

    }
}
