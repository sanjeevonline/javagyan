package com.javagyan.example.aspect;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("call(* com.javagyan.example.aspect..*(..))")
    public void logAspects() {
    }

    @Pointcut("call(* com.javagyan.example.util..*(..))")
    public void logUtils() {
    }

    @Pointcut("logAspects() || logUtils()")
    public void coreModuleLogging() {
    }

    /**
     * AfterThrowing : Gets called in case any exceptions are thrown during method execution in any of the classes that
     * are covered by the defined pointcuts.
     * <ul>
     * <li>Logs the signature of the method where exception was thrown.</li>
     * <li>Logs the exception trace.</li>
     * </ul>
     * @param joinPoint : Joinpoint on which it is be applied.
     * @param e : Exception instance.
     */
    @AfterThrowing(value = "coreModuleLogging()", throwing = "e")
    public void logAfterThrowing(final JoinPoint joinPoint, final Throwable e) {
        LOGGER.error("An exception has been thrown in {} ", joinPoint.getSignature().getName() + "()");
        LOGGER.error("Exception Cause {}:", e.getCause(), e);
    }

    /**
     * Around : Gets invoked when any method covered by the defined pointcuts are executed.It has control on the
     * execution of the method.Following activities are performed in this advice:
     * <ul>
     * <li>Logs the entry of the method along with the parameters.</li>
     * <li>Logs the total execution time taken by the method.</li>
     * <li>Logs the exit of the method along with the return value.</li>
     * </ul>
     * @param joinPoint : Joinpoint on which it will be applied
     * @return Object : Return value from method if any.
     * @throws Throwable
     */
    @Around(value = "coreModuleLogging()")
    public Object logAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final String jointPointName = joinPoint.getSignature().getName();
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final String[] parameterNames = signature.getParameterNames();
        final Object[] parameterValues = joinPoint.getArgs();
        LOGGER.info("Entering method {}", jointPointName + " Argument details {}" + Arrays.toString(parameterNames)
                + " Values " + Arrays.toString(parameterValues));
        final long startTime = System.currentTimeMillis();
        final Object result = joinPoint.proceed();
        LOGGER.info("Exiting method {} ", jointPointName + " Execution took :"
                + (System.currentTimeMillis() - startTime) + " msec .Method returns " + result);
        return result;
    }
}
