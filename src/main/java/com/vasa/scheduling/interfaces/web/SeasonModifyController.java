package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.SeasonService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/season/modify")
@Controller
public class SeasonModifyController extends DefaultHandlerController{
	
	// TODO: Apple Scheduling Rules
	
	@Autowired
	private SeasonService service;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="season") Integer seasonId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Season s = service.findById(seasonId);
		
		model.addAttribute("modifyseason", s);
		model.addAttribute("sports", teamService.findAllSports());
		model.addAttribute("formattedDate", s.getStartDate()==null?null:sdf.format(s.getStartDate()));
		return "season/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Season season = null;		
		if(request.getParameter("id") != null){
			season = service.findById(Integer.valueOf(request.getParameter("id")));
		}
		
		if(request.getParameter("delete") != null){
			service.delete(season);
		}else{
			season.setName(request.getParameter("name"));
			season.setSport(teamService.findSportById(Integer.valueOf(request.getParameter("sport"))));

			if(request.getParameter("status") != null){
				season.setStatus(Status.toEnumFromCode(Integer.valueOf(request.getParameter("status"))));
			}
			
			String dateString = request.getParameter("startDate");
			if(dateString != null && dateString.length()>0){
				Date d = null;
				try {
					d = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
				Calendar date = Calendar.getInstance();
				date.setTime(d);
				date.set(Calendar.HOUR_OF_DAY, 0);
				date.set(Calendar.MINUTE, 0);
			
				season.setStartDate(date.getTime());
			}else{
				season.setStartDate(null);
			}
			
			service.save(season);
		}
		
		List<Season> seasons = service.findAll();	
	    model.addAttribute("seasons", seasons);
		return "season/list";
	}
	
}
