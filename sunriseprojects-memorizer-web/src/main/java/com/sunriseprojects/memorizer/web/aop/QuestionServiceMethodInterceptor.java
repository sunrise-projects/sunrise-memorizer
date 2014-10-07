package com.sunriseprojects.memorizer.web.aop;

import java.util.Arrays;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionServiceMethodInterceptor implements MethodInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(QuestionServiceMethodInterceptor.class);
	
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		logger.info("Method name : "
				+ methodInvocation.getMethod().getName());
		logger.info("Method arguments : "
				+ Arrays.toString(methodInvocation.getArguments()));

		logger.info("Intercepted : Before intercept!");

		try {
			Object result = methodInvocation.proceed();
			logger.info("Intercepted : After intercept!");

			return result;

		} catch (IllegalArgumentException e) {

			logger.info("Intercepted : Throw exception intercept!");
			throw e;
		}
	}
}
