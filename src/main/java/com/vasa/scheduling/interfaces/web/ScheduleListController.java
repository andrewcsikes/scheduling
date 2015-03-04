package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@Controller
@RequestMapping("/schedule/list")
public class ScheduleListController extends DefaultHandlerController {

	@Autowired private TeamService service;
	@Autowired private ScheduleService scheduleService;
	
	@RequestMapping(value="/quick", method = RequestMethod.GET)
	public String quick(@RequestParam(required=false, value="date") String date, Model model, HttpServletRequest request) {
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		int month = today.get(Calendar.MONTH);
		
		List<FieldSchedule> schedule = scheduleService.findByMonth(new Date());
		List<Game> games = scheduleService.findGameByMonth(new Date());
			
	    model.addAttribute("shedule", schedule);
	    model.addAttribute("games", games);
	    model.addAttribute("teams", service.findActive());
	    model.addAttribute("fields", scheduleService.findAllFields());
		model.addAttribute("filterMonth", month+1);
		
		return "schedule/quick-list";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		int month = today.get(Calendar.MONTH);
		
		List<FieldSchedule> schedule = scheduleService.findByMonth(new Date());
		List<Game> games = scheduleService.findGameByMonth(new Date());
			
	    model.addAttribute("shedule", schedule);
	    model.addAttribute("games", games);
	    model.addAttribute("teams", service.findActive());
	    model.addAttribute("fields", scheduleService.findAllFields());
		model.addAttribute("filterMonth", month+1);
	    
	    
	    return "schedule/list";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String search(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		String teamId = request.getParameter("team");
		
		String game = request.getParameter("game");
		Boolean gamesOnly = null;
		if(game.equals("2")){
			gamesOnly=true;
		}else if(game.equals("1")){
			gamesOnly=false;
		}
		
		String month = request.getParameter("month");
		String filterClass = request.getParameter("class");
		String fieldName = request.getParameter("field");
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		today.set(Calendar.MONTH, Integer.valueOf(month)-1);
		
		List<FieldSchedule> schedule = null;
		List<Game> games = null;
		
		if(teamId.equals("All")){
			if(fieldName.equals("All")){
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(null, null, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(null, null, filterClass, today.getTime());
				}
			}else{
				Fields field = scheduleService.findFieldByName(fieldName);
				model.addAttribute("filterfield", field.getName());
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(null, field, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(null, field, filterClass, today.getTime());
				}
			}
		}else{
			Team team = service.findById(Integer.valueOf(teamId));
			model.addAttribute("filterTeam", team.getId());
			if(fieldName.equals("All")){
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(team, null, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(team, null, filterClass, today.getTime());
				}
			}else{
				Fields field = scheduleService.findFieldByName(fieldName);
				model.addAttribute("filterfield", field.getName());
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(team, field, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(team, field, filterClass, today.getTime());
				}
			}
		}
		
		List<FieldSchedule> returnVal = new ArrayList<FieldSchedule>();
		if(filterClass != null && !filterClass.equals("0")){
			if(schedule != null){
				for(FieldSchedule s: schedule){
					if(s.getTeam() != null && s.getTeam().getClassification().getCode().equals(Integer.valueOf(filterClass))){
						returnVal.add(s);
					}
				}
			}
			games = null;
		}else if(schedule != null && schedule.size()>0){
			returnVal.addAll(schedule);
		}
		
		model.addAttribute("filterClass", filterClass);
		model.addAttribute("filterMonth", month);
	    model.addAttribute("teams", service.findActive());
	    model.addAttribute("fields", scheduleService.findAllFields());
		model.addAttribute("game", game);
		model.addAttribute("shedule", returnVal);
		model.addAttribute("games", games);
		
	    return "schedule/list";
	}
	
	@RequestMapping(value="/quick", method = RequestMethod.POST)
	public String quickSearch(Model model, HttpServletRequest request) {
		
		String teamId = request.getParameter("team");
		
		String game = request.getParameter("game");
		Boolean gamesOnly = null;
		if(game.equals("2")){
			gamesOnly=true;
		}else if(game.equals("1")){
			gamesOnly=false;
		}
		
		String month = request.getParameter("month");
		String filterClass = request.getParameter("class");
		String fieldName = request.getParameter("field");
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		today.set(Calendar.MONTH, Integer.valueOf(month)-1);
		
		List<FieldSchedule> schedule = null;
		List<Game> games = null;
		
		if(teamId.equals("All")){
			if(fieldName.equals("All")){
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(null, null, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(null, null, filterClass, today.getTime());
				}
			}else{
				Fields field = scheduleService.findFieldByName(fieldName);
				model.addAttribute("filterfield", field.getName());
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(null, field, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(null, field, filterClass, today.getTime());
				}
			}
		}else{
			Team team = service.findById(Integer.valueOf(teamId));
			model.addAttribute("filterTeam", team.getId());
			if(fieldName.equals("All")){
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(team, null, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(team, null, filterClass, today.getTime());
				}
			}else{
				Fields field = scheduleService.findFieldByName(fieldName);
				model.addAttribute("filterfield", field.getName());
				if(gamesOnly == null || !gamesOnly){
					schedule = scheduleService.findByFilter(team, field, filterClass, today.getTime());
				}
				if(gamesOnly == null || gamesOnly){
					games = scheduleService.findGamesByFilter(team, field, filterClass, today.getTime());
				}
			}
		}
		
		List<FieldSchedule> returnVal = new ArrayList<FieldSchedule>();
		if(filterClass != null && !filterClass.equals("0")){
			for(FieldSchedule s: schedule){
				if(s.getTeam() != null && s.getTeam().getClassification().getCode().equals(Integer.valueOf(filterClass))){
					returnVal.add(s);
				}
			}
		}else if(schedule != null){
			returnVal.addAll(schedule);
		}
		
		model.addAttribute("filterClass", filterClass);
		model.addAttribute("filterMonth", month);
	    model.addAttribute("teams", service.findActive());
	    model.addAttribute("fields", scheduleService.findAllFields());
		model.addAttribute("game", game);
		model.addAttribute("shedule", returnVal);
		model.addAttribute("games", games);
		
	    return "schedule/quick-list";
	}
}