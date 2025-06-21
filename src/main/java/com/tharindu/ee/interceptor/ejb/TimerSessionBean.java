package com.tharindu.ee.interceptor.ejb;

import com.tharindu.ee.interceptor.annotation.TimeoutLogger;
import jakarta.ejb.Schedule;
import jakarta.ejb.Stateless;
import jakarta.ejb.Timeout;

@Stateless
@TimeoutLogger
public class TimerSessionBean {
    @Schedule(hour = "*", minute = "*", second = "*/10", persistent = false)
    @Timeout
    public void doTimerAction() {
        // Business logic for the timer session bean
        System.out.println("Executing timer action");
    }
}
