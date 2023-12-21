package com.qa.entity;

public enum LeftMenuComponentType {
    ADMIN("Admin"),
    PIM("PIM"),
    LEAVE("Leave"),
    MYINFO("My Info"),
    CLAIM("Claim"),
    RECRUITEMENT("Recruitment");

    private String menuName;

    LeftMenuComponentType(String menuName){
        this.menuName = menuName;
    }

    public String getMenuName(){
        return this.menuName;
    }

}
