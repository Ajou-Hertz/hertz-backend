package com.ajou.hertz.common.logger.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.ajou.hertz.*..api..*(..))")
    public void controllerPoint() {
    }

    @Pointcut("execution(* com.ajou.hertz.*..service..*(..))")
    public void servicePoint() {
    }

    @Pointcut("execution(* com.ajou.hertz.*..repository..*(..))")
    public void repositoryPoint() {
    }
}
