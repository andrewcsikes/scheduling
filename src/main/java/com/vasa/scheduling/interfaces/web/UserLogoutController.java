package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.services.UserService;

@RequestMapping("/user/logout")
@Controller
public class UserLogoutController extends DefaultHandlerController{

	@RequestMapping(method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("user");
		return "login";
	}
	
}
