package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpSession;
import com.vasa.scheduling.domain.User;

public class DefaultHandlerController {
	
	public User verifyUser(HttpSession session){
		return (User)session.getAttribute("user");
	}
	
}
