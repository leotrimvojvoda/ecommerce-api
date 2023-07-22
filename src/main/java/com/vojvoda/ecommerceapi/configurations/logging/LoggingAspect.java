package com.vojvoda.ecommerceapi.configurations.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
public class LoggingAspect {

    @Pointcut("execution(* com.vojvoda.ecommerceapi.core.*.*Service.*(..) )")
    public void servicesPointcut() {}

    @Before("servicesPointcut()")
    public void beforeLogger(JoinPoint joinPoint) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        Object[] args = joinPoint.getArgs();

        log.info("[BEFORE INVOKING] {}.{}({})",className,methodName,mapper.writeValueAsString(args));
    }


    @AfterReturning(pointcut = "servicesPointcut()", returning = "result")
    public void afterLogging(JoinPoint joinPoint, Object result) {

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();

        String returnValue = this.getValue(result);

        log.info("[AFTER INVOKING] {}.{} => Response: {}",className,methodName,returnValue);
    }

    private String getValue(Object result) {
        String returnValue = null;
        if (null != result) {
            if (result.toString().endsWith("@" + Integer.toHexString(result.hashCode()))) {
                returnValue = ReflectionToStringBuilder.toString(result);
            } else {
                returnValue = result.toString();
            }
        }
        return returnValue;
    }

//    @Around("myPointcut()")
//    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
//
//        ObjectMapper mapper = new ObjectMapper();
//        String methodName = pjp.getSignature().getName();
//        String className = pjp.getSignature().getDeclaringTypeName();
//        Object[] args = pjp.getArgs();
//
//        log.info("[BEFORE] Method invoked: {}.{}({})",className,methodName,mapper.writeValueAsString(args));
//
//        Object response = pjp.proceed();
//
//        log.info("[AFTER] Method invoked: {}.{} ; Response: {}",className,methodName,mapper.writeValueAsString(response));
//
//        return response;
//    }
}
