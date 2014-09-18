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

import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.SeasonService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/season/add")
@Controller
public class SeasonAddController extends DefaultHandlerController{
	
	@Autowired
	private SeasonService service;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		model.addAttribute("sports", teamService.findAllSports());
		
		return "season/add";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Season season = new Season();
		
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
		}
		
		service.save(season);
		
		List<Season> seasons = service.findAll();	
	    model.addAttribute("seasons", seasons);
		return "season/list";
	}
	
}
