package com.sunriseprojects.memorizer.core.console;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;


public class StartQARuleEngine extends SpringRuleEngine {
	public static final SpringRuleEngine getEngine(String name) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("StartQARuleEngine.xml");
		return (SpringRuleEngine) context.getBean(name);
	}
}
