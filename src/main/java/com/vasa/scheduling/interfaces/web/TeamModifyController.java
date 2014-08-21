package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.State;
import com.vasa.scheduling.enums.UserType;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/team/modify")
@Controller
public class TeamModifyController extends DefaultHandlerController{
	
	@Autowired
	private TeamService service;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="team") Integer teamId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("modifyteam", service.findById(teamId));
		model.addAttribute("coaches", userService.findAllCoaches());
		model.addAttribute("sports", service.findAllSports());
		return "team/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Team team = null;
		
		if(request.getParameter("id") != null){
			team = service.findById(Integer.valueOf(request.getParameter("id")));
		}
		
		service.save(team);
		
		return "user/home";
	}
	
}
