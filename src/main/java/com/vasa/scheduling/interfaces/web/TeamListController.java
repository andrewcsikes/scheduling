package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@Controller
@RequestMapping("/team/list")
public class TeamListController extends DefaultHandlerController {

	@Autowired private TeamService service;
	@Autowired private UserService userService;
	@Autowired private ScheduleService scheduleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		List<Team> teams = service.findActive();	
	    model.addAttribute("teams", teams);
	    
	    model.addAttribute("coaches", userService.findAllCoaches());
		model.addAttribute("sports", service.findAllSports());
		model.addAttribute("seasons", scheduleService.findActiveSeasons());
		model.addAttribute("agegroups", service.findAllAgegroups());
	    
	    return "team/list";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String search(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		String sportId = request.getParameter("sport");
		String agegroup = request.getParameter("ageGroup");
		String season = request.getParameter("season");
		
		List<Team> teams = service.findActive();
		List<Team> serachTeams = new ArrayList<Team>();
		
		for(Team team:teams){
			if((sportId.equals("All") || team.getSport().getId().toString().equals(sportId)) &&
					(agegroup.equals("All") || team.getAgeGroup().getId().toString().equals(agegroup)) &&
					(season.equals("All") || team.getSeason().getId().toString().equals(season)))
			{
						serachTeams.add(team);
			}
		}
		
	    model.addAttribute("teams", serachTeams);
	    
	    model.addAttribute("coaches", userService.findAllCoaches());
		model.addAttribute("sports", service.findAllSports());
		model.addAttribute("seasons", scheduleService.findActiveSeasons());
		model.addAttribute("agegroups", service.findAllAgegroups());
		
	    return "team/list";
	}
}