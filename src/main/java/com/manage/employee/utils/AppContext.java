package com.manage.employee.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class AppContext {

	public static Environment env;

	private static AppContext instance;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public void setEnvironment(Environment environment) {
		AppContext.env = environment;
	}

	@PostConstruct
	public void registerInstance() {
		instance = this;
	}

	public static <T> T getBean(Class<T> clazz) {
		if (instance == null) {
			throw new IllegalStateException("AppContext is not initialized yet.");
		}
		return instance.applicationContext.getBean(clazz);
	}
}
