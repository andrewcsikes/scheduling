package com.vasa.scheduling.interfaces.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;
import com.vasa.scheduling.services.UserService;

@RequestMapping("/team/modify")
@Controller
public class TeamModifyController extends DefaultHandlerController{
	
	@Autowired
	private TeamService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ScheduleService scheduleService;
	
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
		//model.addAttribute("sports", service.findAllSports());
		model.addAttribute("seasons", scheduleService.findActiveSeasons());
		model.addAttribute("agegroups", service.findAllAgegroups());
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
		
		team.setName(request.getParameter("name"));
		team.setCoach(userService.findById(Integer.valueOf(request.getParameter("coach"))));
		//team.setSport(service.findSportById(Integer.valueOf(request.getParameter("sport"))));
		team.setSeason(scheduleService.findSeasonById(Integer.valueOf(request.getParameter("season"))));
		team.setSport(team.getSeason().getSport());
		team.setAgeGroup(service.findAgeGroupById(Integer.valueOf(request.getParameter("ageGroup"))));
		String limit = request.getParameter("practiceLimit");
		if(limit != null){
			try{
				team.setPracticeLimit(Integer.valueOf(limit));
			}catch(Exception e){
				
			}
		}else{
			team.setPracticeLimit(null);
		}
		team.setClassification(Classification.toEnumFromCode(Integer.valueOf(request.getParameter("classification"))));
		
		service.save(team);
		
		return "team/list";
	}
	
}
