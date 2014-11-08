package com.sunriseprojects.memorizer.framework;

import java.util.concurrent.ConcurrentHashMap;

public class SunriseApplicationContext {

	private volatile static SunriseApplicationContext uniqueInstance;
	
	private ConcurrentHashMap<String, Object> map = null;
	
	
	private SunriseApplicationContext() {
		
		map = new ConcurrentHashMap<String, Object>();
	}
	
	public static SunriseApplicationContext getInstance() {
		
		if(uniqueInstance == null) {
			synchronized (SunriseApplicationContext.class) {
				uniqueInstance = new SunriseApplicationContext();
			}
		}
		return uniqueInstance;
	}
	
	public void setAppContext(String key, Object value) {
		map.put(key, value);
	}
	
	public Object getAppContext(String key) {
		return map.get(key);
	}
	

	
	
}
