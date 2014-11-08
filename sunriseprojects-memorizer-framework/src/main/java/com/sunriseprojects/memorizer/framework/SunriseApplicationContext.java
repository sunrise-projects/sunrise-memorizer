package com.sunriseprojects.memorizer.framework;

import java.util.concurrent.ConcurrentHashMap;

public class SunriseApplicationContext {

	private volatile static SunriseApplicationContext uniqueInstance;
	
	private ConcurrentHashMap<String, Object> map = null;
	
	
	private SunriseApplicationContext() {
		System.out.println("init SunriseApplicationContext Singleton ");
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
		System.out.println("set SunriseApplicationContext Singleton " + key + " " + value);
		map.put(key, value);
	}
	
	public Object getAppContext(String key) {
		System.out.println("get SunriseApplicationContext Singleton " + key + " " + map.get(key));
		return map.get(key);
	}
	 	

	
	
}
