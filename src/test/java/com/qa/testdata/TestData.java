package com.qa.testdata;

import com.qa.entity.Employee;
import com.qa.entity.LoginDetails;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class TestData  {
    private static PodamFactoryImpl FACTORY = new PodamFactoryImpl();

    private TestData(){}

    public static Employee getEmployeeData(){
        return FACTORY.manufacturePojo(Employee.class);
    }
    public static LoginDetails getLoginData(){
        return FACTORY.manufacturePojo(LoginDetails.class);
    }
}
