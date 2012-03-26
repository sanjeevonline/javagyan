/**
 * 
 */
package com.javagyan.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Aspect for logging exception
 * @author Sanjeev Kumar
 */
@Aspect
public class ExceptionHandlingAspect {
    private static Logger LOGGER = null;

    @Pointcut("call(* com.javagyan.example..*(..))")
    public void exceptionLogMethods() {
    }

    @AfterThrowing(pointcut = "exceptionLogMethods()", throwing = "e")
    public void handleException(final Throwable e, final JoinPoint jp) {
        if (jp.getThis() != null) {
            LOGGER = LoggerFactory.getLogger(jp.getThis().getClass());
        } else {
            LOGGER = LoggerFactory.getLogger(ExceptionHandlingAspect.class);
        }
        LOGGER.error(jp.getThis().getClass() + " threw an exception.  The details are: " + e);
    }
}
