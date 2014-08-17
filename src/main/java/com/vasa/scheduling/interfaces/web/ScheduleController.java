package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.User;

@Controller
@RequestMapping("/schedule/list")
public class ScheduleController extends DefaultHandlerController {

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		Date startDate = new Date();
		
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("FM North");
		fields.add("FM South");
		
		// schedule contains the entire schedule for every field
		HashMap<String, HashMap<String,ArrayList<String>>> schedule = new HashMap<String, HashMap<String,ArrayList<String>>>();
		
		for(String field: fields){
			// week - key is the day, value is the fields
			HashMap<String,ArrayList<String>> week = new HashMap<String, ArrayList<String>>();
			week.put("Sunday", getSunday());
			week.put("Monday", getSunday());
			week.put("Tuesday", getSunday());
			week.put("Wednesday", getSunday());
			week.put("Thursday", getSunday());
			week.put("Friday", getSunday());
			week.put("Saturday", getSunday());
			schedule.put(field, week);
		}
		
		model.addAttribute("sunday", startDate);
		model.addAttribute("fields", fields);
		model.addAttribute("schedule",schedule);
		
	    return "schedule/list";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			Model model, 
			HttpServletRequest request) {
		 return list(model, request);
	}
	
	@RequestMapping(value="/delete", method = RequestMethod.GET)
	public String delete(@RequestParam(required=true, value="date") String date,
			@RequestParam(required=true, value="field") String field,
			Model model, 
			HttpServletRequest request) {
		 return list(model, request);
	}

	private ArrayList<String> getSunday() {
		ArrayList<String> sunday = new ArrayList<String>();

		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add("VA2 - Fowler - 6U");
		sunday.add("VA2 - Fowler - 6U");
		sunday.add(null);
		sunday.add(null);
		sunday.add("VA1 - Sikes - 6U");
		sunday.add("VA1 - Sikes - 6U");
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		sunday.add(null);
		return sunday;
	}
}