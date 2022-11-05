package com.envision.automation.framework.util;

import com.github.javafaker.Faker;

public class DataGenereator {
    public static String getUsername(){
        String username=new Faker().name().username();
        return username;

    }
    public static String getPassword(){
        String password=new Faker().internet().password(true);
    return password;

    }
    public static String getLoginName(){
        String loginName=new Faker().name().fullName();
        return loginName;


    }
}
