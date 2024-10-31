package com.escuelas.project.escuelas_project.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpRequestAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before("execution(* com.escuelas.project.escuelas_project..controllers.*.*(..))")
    public void logHttpBeforeReturning(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("Método: {}, URL: {}, IP: {}, Parámetros: {}",
                joinPoint.getSignature().toShortString(), request.getRequestURL().toString(),
                request.getRemoteAddr(), request.getParameterMap());
    }

    @AfterReturning(pointcut = "execution(* com.escuelas.project.escuelas_project..controllers.*.*(..))", returning = "result")
    public void logHttpAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Método: {}, Respuesta: {}", joinPoint.getSignature().toShortString(), result);
    }

}
