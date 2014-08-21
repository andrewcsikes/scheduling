package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.TeamService;

@Controller
@RequestMapping("/team/list")
public class TeamListController extends DefaultHandlerController {

	@Autowired private TeamService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		List<Team> teams = service.findActive();	
	    model.addAttribute("teams", teams);
	    return "team/list";
	}
}