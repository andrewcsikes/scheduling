package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Sport;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.SportService;

@RequestMapping("/sport/add")
@Controller
public class SportAddController extends DefaultHandlerController{
	
	@Autowired
	private SportService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		return "sport/add";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Sport sport = new Sport();
		
		sport.setName(request.getParameter("name"));
		
		service.save(sport);
		
		return "user/home";
	}
	
}
