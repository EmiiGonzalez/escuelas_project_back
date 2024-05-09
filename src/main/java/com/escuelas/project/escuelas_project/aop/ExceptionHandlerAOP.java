package com.escuelas.project.escuelas_project.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlerAOP {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.escuelas.project.escuelas_project.service.models.exceptions.*.*(..))")
    public void logBeforeException(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();        //obtenemos el nombre del método
        Object[] args = joinPoint.getArgs();    //obtenemos los argumentos de la llamada a la excepción
        
        StringBuilder logBuilder = new StringBuilder(); //creamos un StringBuilder para construir el mensaje
        logBuilder.append("Exception in method: ").append(methodName);  //añadimos el nombre del método
        
        if (args != null && args.length > 0) {  //si hay argumentos
            logBuilder.append(", Arguments: [");
            for (int i = 0; i < args.length; i++) { //recorremos los argumentos
                logBuilder.append(args[i]); 
                if (i < args.length - 1) {
                    logBuilder.append(", ");
                }
            }
            logBuilder.append("]");
        }
        
        logger.error(logBuilder.toString());    //mostramos el mensaje
    }

    @AfterReturning(pointcut = "execution(* com.escuelas.project.escuelas_project.service.models.exceptions.*.*(..))", returning = "result")
    public void logAfterReturningException(JoinPoint joinPoint, Object result) {
        logger.info("Método: {}, Respuesta: {}", joinPoint.getSignature().toShortString(), result);
    }
}
