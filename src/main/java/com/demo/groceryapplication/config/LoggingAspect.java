package com.demo.groceryapplication.config;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    public LoggingAspect() {
        System.out.println("LoggingAspect Called");
    }

    @Pointcut("execution(* com.demo.groceryapplication.service.ProductService.*(..))")
    public void productServiceMethods() {

    }

    @Pointcut("execution(* com.demo.groceryapplication.controller.CustomerController.*(..))")
    public void customerServiceMethods() {
    }

    @Before("customerServiceMethods() || productServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP] before execution" + joinPoint.getSignature());
    }

    @AfterReturning(pointcut = "productServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AOP] after execution" + joinPoint.getSignature());
    }

    @AfterThrowing(pointcut = "productServiceMethods()")
    public void logAfterThrowing(JoinPoint joinPoint) {
        System.out.println("[AOP] after throwing execution" + joinPoint.getSignature());
    }
}
