package com.qa.tests;

import com.qa.entity.Employee;
import com.qa.entity.LeftMenuComponentType;
import com.qa.entity.LoginDetails;
import com.qa.pages.EmployeeInformationPage;
import com.qa.pages.LoginPage;
import com.qa.testdata.TestData;
import com.qa.tests.base.TestSetup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class AddEmployeeTest extends TestSetup {

    @Test
    @Tag("Employee")
    @DisplayName("Add the employee information")
    public void addEmployeeInformtion1()  {

        Employee employee = TestData.getEmployeeData();
        LoginDetails loginDetails = TestData.getLoginData();

        getNewInstance(LoginPage::new)
                .logIntoApplication(loginDetails.getUserName(), loginDetails.getPassWord())
                .getLeftMenuComponent()
                .selectLeftMenu(LeftMenuComponentType.PIM);
                getNewInstance(EmployeeInformationPage::new)
                .addEmployee(employee)
                .employeeCreatedSuccessfully();
    }

   @Test
   @Tag("Employee")
   @DisplayName("Navigate to the My info section")
    public void employeeInformation1() {
       //read yml in Hask key

       Employee employee = TestData.getEmployeeData();
       LoginDetails loginDetails = TestData.getLoginData();

       getNewInstance(LoginPage::new)
               .logIntoApplication("Admin", "admin123")
               .getLeftMenuComponent()
               .selectLeftMenu(LeftMenuComponentType.MYINFO);
       getNewInstance(EmployeeInformationPage::new)
               .navigateMyInfo();

   }

    @Test
    @Tag("Admin")
    @DisplayName("verify the claim section")
    public void checkClaim1()  {

        LoginDetails loginDetails = TestData.getLoginData();

        getNewInstance(LoginPage::new)
                .logIntoApplication(loginDetails.getUserName(), loginDetails.getPassWord())
                .getLeftMenuComponent()
                .selectLeftMenu(LeftMenuComponentType.CLAIM);
                 getNewInstance(EmployeeInformationPage::new)
                .checkClaimCount();
    }


   @Test
   @Tag("Admin")
   @DisplayName("Employee recruitment")
    public void employeeRecruitment1()  {

        LoginDetails loginDetails = TestData.getLoginData();

       getNewInstance(LoginPage::new)
                .logIntoApplication(loginDetails.getUserName(), loginDetails.getPassWord())
                .getLeftMenuComponent()
                .selectLeftMenu(LeftMenuComponentType.RECRUITEMENT);
                getNewInstance(EmployeeInformationPage::new)
                .checkVacancies();
    }


}
