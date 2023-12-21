package com.qa.tests.base;

import com.qa.utility.ConfigFactory;
import com.qa.utility.ConfigReader;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.function.Supplier;

@ExtendWith(BaseTestClass.class)
public class TestSetup {

    public  <T> T getNewInstance(Supplier<T> t){
        return  t.get();
    }
}
