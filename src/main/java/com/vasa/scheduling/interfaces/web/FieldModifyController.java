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

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.FieldService;
import com.vasa.scheduling.services.SeasonService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/fields/modify")
@Controller
public class FieldModifyController extends DefaultHandlerController{
	
	@Autowired
	private FieldService service;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="field") Integer fieldId,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Fields f = service.findById(fieldId);
		
		model.addAttribute("modifyfield", f);
		model.addAttribute("sports", teamService.findAllSports());
		return "fields/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		Fields field = null;		
		if(request.getParameter("id") != null){
			field = service.findById(Integer.valueOf(request.getParameter("id")));
		}
		
		if(request.getParameter("delete") != null){
			service.delete(field);
		}else{
			field.setName(request.getParameter("name"));
			field.setSport(teamService.findSportById(Integer.valueOf(request.getParameter("sport"))));

			if(request.getParameter("status") != null){
				field.setStatus(Status.toEnumFromCode(Integer.valueOf(request.getParameter("status"))));
			}
			
			String apply = request.getParameter("lights");
			if(apply != null && apply.equals("on")){
				field.setLights(true);
			}else{
				field.setLights(false);
			}
			
			String sunday = request.getParameter("sunday");
			if(sunday != null && sunday.equals("on")){
				field.setAvailableSunday(true);
				field.setSundayStartTime(Integer.valueOf(request.getParameter("sundayStart")));
				field.setSundayEndTime(Integer.valueOf(request.getParameter("sundayEnd")));
			}else{
				field.setAvailableSunday(false);
			}
			
			String monday = request.getParameter("monday");
			if(monday != null && monday.equals("on")){
				field.setAvailableMonday(true);
				field.setMondayStartTime(Integer.valueOf(request.getParameter("mondayStart")));
				field.setMondayEndTime(Integer.valueOf(request.getParameter("mondayEnd")));
			}else{
				field.setAvailableMonday(false);
			}
			
			String tuesday = request.getParameter("tuesday");
			if(tuesday != null && tuesday.equals("on")){
				field.setAvailableTuesday(true);
				field.setTuesdayStartTime(Integer.valueOf(request.getParameter("tuesdayStart")));
				field.setTuesdayEndTime(Integer.valueOf(request.getParameter("tuesdayEnd")));
			}else{
				field.setAvailableTuesday(false);
			}
			
			String wednesday = request.getParameter("wednesday");
			if(wednesday != null && wednesday.equals("on")){
				field.setAvailableWednesday(true);
				field.setWednesdayStartTime(Integer.valueOf(request.getParameter("wednesdayStart")));
				field.setWednesdayEndTime(Integer.valueOf(request.getParameter("wednesdayEnd")));
			}else{
				field.setAvailableWednesday(false);
			}
			
			String thursday = request.getParameter("thursday");
			if(thursday != null && thursday.equals("on")){
				field.setAvailableThursday(true);
				field.setThursdayStartTime(Integer.valueOf(request.getParameter("thursdayStart")));
				field.setThursdayEndTime(Integer.valueOf(request.getParameter("thursdayEnd")));
			}else{
				field.setAvailableThursday(false);
			}
			
			String friday = request.getParameter("friday");
			if(friday != null && friday.equals("on")){
				field.setAvailableFriday(true);
				field.setFridayStartTime(Integer.valueOf(request.getParameter("fridayStart")));
				field.setFridayEndTime(Integer.valueOf(request.getParameter("fridayEnd")));
			}else{
				field.setAvailableFriday(false);
			}
			
			String saturday = request.getParameter("saturday");
			if(saturday != null && saturday.equals("on")){
				field.setAvailableSaturday(true);
				field.setSaturdayStartTime(Integer.valueOf(request.getParameter("saturdayStart")));
				field.setSaturdayEndTime(Integer.valueOf(request.getParameter("saturdayEnd")));
			}else{
				field.setAvailableSaturday(false);
			}
			
			service.save(field);
		}

		model.addAttribute("sports", teamService.findAllSports());
		return "fields/list";
	}
	
}
