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
				if(request.getParameter("sundayStart") != null && request.getParameter("sundayStart").length()>0)
					field.setSundayStartTime(Integer.valueOf(request.getParameter("sundayStart")));
				if(request.getParameter("sundayEnd") != null && request.getParameter("sundayEnd").length()>0)
					field.setSundayEndTime(Integer.valueOf(request.getParameter("sundayEnd")));
			}else{
				field.setAvailableSunday(false);
			}
			
			String monday = request.getParameter("monday");
			if(monday != null && monday.equals("on")){
				field.setAvailableMonday(true);
				if(request.getParameter("mondayStart") != null && request.getParameter("mondayStart").length()>0)
					field.setMondayStartTime(Integer.valueOf(request.getParameter("mondayStart")));
				if(request.getParameter("mondayEnd") != null && request.getParameter("mondayEnd").length()>0)
					field.setMondayEndTime(Integer.valueOf(request.getParameter("mondayEnd")));
			}else{
				field.setAvailableMonday(false);
			}
			
			String tuesday = request.getParameter("tuesday");
			if(tuesday != null && tuesday.equals("on")){
				field.setAvailableTuesday(true);
				if(request.getParameter("tuesdayStart") != null && request.getParameter("tuesdayStart").length()>0)
					field.setTuesdayStartTime(Integer.valueOf(request.getParameter("tuesdayStart")));
				if(request.getParameter("tuesdayEnd") != null && request.getParameter("tuesdayEnd").length()>0)
					field.setTuesdayEndTime(Integer.valueOf(request.getParameter("tuesdayEnd")));
			}else{
				field.setAvailableTuesday(false);
			}
			
			String wednesday = request.getParameter("wednesday");
			if(wednesday != null && wednesday.equals("on")){
				field.setAvailableWednesday(true);
				if(request.getParameter("wednesdayStart") != null && request.getParameter("wednesdayStart").length()>0)
					field.setWednesdayStartTime(Integer.valueOf(request.getParameter("wednesdayStart")));
				if(request.getParameter("wednesdayEnd") != null && request.getParameter("wednesdayEnd").length()>0)
					field.setWednesdayEndTime(Integer.valueOf(request.getParameter("wednesdayEnd")));
			}else{
				field.setAvailableWednesday(false);
			}
			
			String thursday = request.getParameter("thursday");
			if(thursday != null && thursday.equals("on")){
				field.setAvailableThursday(true);
				if(request.getParameter("thursdayStart") != null && request.getParameter("thursdayStart").length()>0)
					field.setThursdayStartTime(Integer.valueOf(request.getParameter("thursdayStart")));
				if(request.getParameter("thursdayEnd") != null && request.getParameter("thursdayEnd").length()>0)
					field.setThursdayEndTime(Integer.valueOf(request.getParameter("thursdayEnd")));
			}else{
				field.setAvailableThursday(false);
			}
			
			String friday = request.getParameter("friday");
			if(friday != null && friday.equals("on")){
				field.setAvailableFriday(true);
				if(request.getParameter("fridayStart") != null && request.getParameter("fridayStart").length()>0)
					field.setFridayStartTime(Integer.valueOf(request.getParameter("fridayStart")));
				if(request.getParameter("fridayEnd") != null && request.getParameter("fridayEnd").length()>0)
					field.setFridayEndTime(Integer.valueOf(request.getParameter("fridayEnd")));
			}else{
				field.setAvailableFriday(false);
			}
			
			String saturday = request.getParameter("saturday");
			if(saturday != null && saturday.equals("on")){
				field.setAvailableSaturday(true);
				if(request.getParameter("saturdayStart") != null && request.getParameter("saturdayStart").length()>0)
					field.setSaturdayStartTime(Integer.valueOf(request.getParameter("saturdayStart")));
				if(request.getParameter("saturdayEnd") != null && request.getParameter("saturdayEnd").length()>0)
					field.setSaturdayEndTime(Integer.valueOf(request.getParameter("saturdayEnd")));
			}else{
				field.setAvailableSaturday(false);
			}
			
			service.save(field);
		}

		model.addAttribute("fields", service.findAll());
		return "fields/list";
	}
	
}
