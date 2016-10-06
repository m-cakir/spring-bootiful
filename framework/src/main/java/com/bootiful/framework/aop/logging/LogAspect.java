package com.bootiful.framework.aop.logging;

import com.bootiful.framework.annotation.Logging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("@annotation(com.bootiful.framework.annotation.Logging)")
	public void logging(){}
	
	@Around("logging()")
	public Object aroundLogProcess(ProceedingJoinPoint joinPoint) throws Throwable{

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String declaringTypeName = signature.getDeclaringTypeName();
		String method = signature.getName();
		String arguments = Arrays.toString(joinPoint.getArgs());

		Logging annotation = signature.getMethod().getAnnotation(Logging.class);

		logger.info("{}.{}() with Logging.method = {}", declaringTypeName, method, annotation.method());

		logger.info("{}.{}() with argument[s] = {}", declaringTypeName, method, arguments);

		try {

			Object result = joinPoint.proceed();

			logger.info("{}.{}() with result = {}", declaringTypeName, method, result);

			return result;

		} catch (IllegalArgumentException e) {

			logger.info("Illegal argument: {} in {}.{}()", arguments, declaringTypeName, method);

			throw e;
		}
	}
}