package com.tharindu.ee.interceptor.interceptor;

import com.tharindu.ee.interceptor.annotation.Login;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;

@Interceptor
@Login
@Priority(2)
public class LoginInterceptor {
    @AroundInvoke
   public Object interceptMethod(jakarta.interceptor.InvocationContext context) throws Exception {
        System.out.println("Checking login status...");
        return context.proceed();
    }
}
