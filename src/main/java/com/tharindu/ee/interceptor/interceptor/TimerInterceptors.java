package com.tharindu.ee.interceptor.interceptor;

import com.tharindu.ee.interceptor.annotation.TimeoutLogger;
import jakarta.annotation.Priority;
import jakarta.interceptor.AroundTimeout;
import jakarta.interceptor.Interceptor;

import java.util.Timer;

@TimeoutLogger
@Interceptor
@Priority(1)
public class TimerInterceptors {
    @AroundTimeout
    public Object aroundTimeout(jakarta.interceptor.InvocationContext context) throws Exception {
        System.out.println("Timer method is about to be invoked: " + context.getMethod().getName());
        Timer timer = (Timer) context.getTimer();
        timer.cancel();

        Object result = context.proceed();
        System.out.println("Timer method has been invoked: " + context.getMethod().getName());
        return result;
    }
}
