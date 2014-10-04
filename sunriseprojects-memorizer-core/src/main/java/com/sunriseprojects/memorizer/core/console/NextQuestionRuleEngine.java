package com.sunriseprojects.memorizer.core.console;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sunriseprojects.memorizer.core.framework.SpringRuleEngine;


public class NextQuestionRuleEngine extends SpringRuleEngine {
	public static final SpringRuleEngine getEngine(String name) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("NextQuestionRuleEngine.xml");
		return (SpringRuleEngine) context.getBean(name);
	}
}
