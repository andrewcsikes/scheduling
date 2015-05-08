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

import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;

@Controller
@RequestMapping("/game/list")
public class GameListController extends DefaultHandlerController {

	@Autowired private ScheduleService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		List<Game> games = null;//service.findAllGames();
		games = service.findGameByWeek(new Date());
	    model.addAttribute("games", games);
	    
	    model.addAttribute("fields", service.findAllFields());
	    return "game/list";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String search(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		String fieldName = request.getParameter("field");
		String dateString = request.getParameter("date");
		
		Date d = null;
		try {
			d = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		
		List<Game> games = null;
		
		
		if(fieldName.equals("All")){
			games = service.findGamesByDayField(date.getTime(), null);
		}else{
			games = service.findGamesByDayField(date.getTime(), fieldName);
		}
		
		
		model.addAttribute("filterField", fieldName);
		model.addAttribute("filterDate", dateString);
	    model.addAttribute("fields", service.findAllFields());
		model.addAttribute("games", games);
		return "game/list";
	}
}