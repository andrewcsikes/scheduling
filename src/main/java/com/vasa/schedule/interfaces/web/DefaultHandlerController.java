package com.vasa.schedule.interfaces.web;

import javax.servlet.http.HttpSession;

public class DefaultHandlerController {
	
	public String verifyUser(HttpSession session){
		return (String)session.getAttribute("user");
	}
	
}
