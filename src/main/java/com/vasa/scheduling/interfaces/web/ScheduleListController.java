package com.vasa.scheduling.interfaces.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Classification;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@Controller
@RequestMapping("/schedule/list")
public class ScheduleListController extends DefaultHandlerController {

	// TODO: Only get Active Fields
	
	@Autowired private TeamService service;
	@Autowired private ScheduleService scheduleService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		int month = today.get(Calendar.MONTH);
		
		List<FieldSchedule> schedule = scheduleService.findByMonth(new Date());
			
	    model.addAttribute("shedule", schedule);
	    model.addAttribute("teams", service.findActive());
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
		String month = request.getParameter("month");
		String filterClass = request.getParameter("class");
		
		Calendar today = Calendar.getInstance();
		Date d = new Date();
		today.setTime(d);
		today.set(Calendar.MONTH, Integer.valueOf(month)-1);
		
		
		List<FieldSchedule> schedule = null;
		
		if(teamId.equals("All")){
			if(filterClass.equals("0")){
				schedule = scheduleService.findByMonth(today.getTime());
			}else{
				model.addAttribute("filterClass", filterClass);
				schedule = scheduleService.findByMonthAndClassification(today.getTime(), Classification.toEnumFromCode(Integer.valueOf(filterClass)));
			}
		}else{
			Team team = service.findById(Integer.valueOf(teamId));
			model.addAttribute("filterTeam", team.getId());
			if(filterClass.equals("0")){
				schedule = scheduleService.findByMonthAndTeam(today.getTime(), team);
			}else{
				model.addAttribute("filterClass", filterClass);
				schedule = scheduleService.findByMonthAndTeamAndClassification(today.getTime(), team, Classification.toEnumFromCode(Integer.valueOf(filterClass)));
			}
		}
		
		model.addAttribute("filterMonth", month);
		model.addAttribute("shedule", schedule);
	    model.addAttribute("teams", service.findActive());
		
	    return "schedule/list";
	}
}