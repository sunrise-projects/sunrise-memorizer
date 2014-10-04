package com.sunriseprojects.memorizer.core.framework;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author mgarber
 *
 */
public class LoanProcessRuleEngine extends SpringRuleEngine {
	public static final SpringRuleEngine getEngine(String name) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/resources/SpringRuleEngineContext.xml");
		return (SpringRuleEngine) context.getBean(name);
	}
}
