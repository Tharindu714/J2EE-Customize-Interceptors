package com.tharindu.ee.interceptor.servlet;

import com.tharindu.ee.interceptor.ejb.UserSessionBean;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test")
public class Test extends HttpServlet {
    @EJB
    UserSessionBean userSessionBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String action = userSessionBean.doAction();
            System.out.println("Action Result: " + action);

            userSessionBean.doAction("Stanley", 25);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
