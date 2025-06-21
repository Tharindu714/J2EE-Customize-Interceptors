package com.tharindu.ee.interceptor.ejb;

import com.tharindu.ee.interceptor.annotation.Login;
import jakarta.ejb.Stateless;

@Stateless
public class UserSessionBean {

    public String doAction() {
        return "Executing action without parameters";
    }

    @Login
    public void doAction(String name, int age) {
        // Business logic for the user session bean
        System.out.println("Executing action with name: " + name + " and age: " + age);
    }
}
