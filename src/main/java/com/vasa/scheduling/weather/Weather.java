package com.vasa.scheduling.weather;

import java.util.HashMap;
import java.util.Map;

public class Weather {
    
    private Map<String, String> conditions;
    
    public void add(String date, String weather){
    	if(conditions == null){
    		conditions = new HashMap<String, String>();
    	}
    	String cond = conditions.get(date);
    	
    	if(weather.contains("Showers") ||
				weather.contains("Thunderstorms") ||
				weather.contains("Storms") ||
				weather.contains("Rain")){
    		if(cond != null){
        		weather = weather + ", " + cond;
        	}
        	conditions.put(date, weather);
    	}
    }
    
    public void addMessage(String date, String weather){
    	if(conditions == null){
    		conditions = new HashMap<String, String>();
    	}
    	String cond = conditions.get(date);
    	
    	if(cond != null){
        	weather = weather + cond;
        }
        conditions.put(date, weather);
    }
    
    public String getWeather(String date){
    	String c = conditions.get(date);
    	if(c==null){
    		c="";
    	}
    	return c;
    }
}