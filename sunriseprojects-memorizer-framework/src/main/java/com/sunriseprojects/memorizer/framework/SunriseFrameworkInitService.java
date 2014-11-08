package com.sunriseprojects.memorizer.framework;

public class SunriseFrameworkInitService {

	// http://www.mkyong.com/spring/spring-init-method-and-destroy-method-example/

	String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void initIt() throws Exception {
		System.out.println("Init method after properties are set : " + message);
		
		SunriseApplicationContext instance = SunriseApplicationContext.getInstance();
		instance.setAppContext("init-message", message);
	}

	public void cleanUp() throws Exception {
		System.out.println("Spring Container is destroy! Customer clean up");
		
		SunriseApplicationContext instance = SunriseApplicationContext.getInstance();
		String obj = (String)instance.getAppContext("init-message");
		System.out.println(obj);
		
		
	}

}
