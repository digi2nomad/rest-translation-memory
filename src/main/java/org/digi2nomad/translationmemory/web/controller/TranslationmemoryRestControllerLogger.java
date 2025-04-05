package org.digi2nomad.translationmemory.web.controller;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
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
	
}
