package com.qa.pages;

import com.qa.entity.LeftMenuComponentType;
import lombok.SneakyThrows;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;

public class LeftMenuComponent {

    public LeftMenuComponent selectLeftMenu(LeftMenuComponentType menuComponentType){
        $(byText(menuComponentType.getMenuName())).should(visible).click();
        return this;
    }

    @SneakyThrows
    public <T> T getInstanceOfPage(Class<T> clazz)  {
        return clazz.getDeclaredConstructor().newInstance();
    }

}
