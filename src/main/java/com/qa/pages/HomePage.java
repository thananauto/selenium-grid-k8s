package com.qa.pages;

import lombok.SneakyThrows;

public class HomePage {

    private LeftMenuComponent leftMenuComponent;

    public HomePage() {
        this.leftMenuComponent = new LeftMenuComponent();
    }


    public LeftMenuComponent getLeftMenuComponent(){
        return leftMenuComponent;
    }


    @SneakyThrows
    public <T> T getInstanceOfPage(Class<T> clazz)  {
        return clazz.getDeclaredConstructor().newInstance();
    }


}
