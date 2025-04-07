package org.digi2nomad.translationmemory.web.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TranslationmemoryRestControllerLogger {
	
	@Before("execution(* org.digi2nomad.translationmemory.web.controller.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Before executing: " + joinPoint.getSignature().getName());
    }

	@After("execution(* org.digi2nomad.translationmemory.web.controller.*.*(..))")
	public void logAfter(JoinPoint joinPoint) {
		System.out.println("After executing: " + joinPoint.getSignature().getName());
	}
	
	@Around("execution(* org.digi2nomad.translationmemory.web.controller.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Before executing: " + joinPoint.getSignature().getName());
		Object result = joinPoint.proceed();
		System.out.println("After executing: " + joinPoint.getSignature().getName());
		return result;
	}
	
	@AfterThrowing("execution(* org.digi2nomad.translationmemory.web.controller.*.*(..))")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		System.out.println("Exception in: " + joinPoint.getSignature().getName() 
				+ " with error: " + error.getMessage());
	}

	@AfterReturning(pointcut = "execution(* org.digi2nomad.translationmemory.web.controller.*.*(..))", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		System.out.println("After returning from: " + joinPoint.getSignature().getName() 
				+ " with result: " + result);
	}
	
}
