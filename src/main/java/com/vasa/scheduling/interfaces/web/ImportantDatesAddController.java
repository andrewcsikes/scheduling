package com.vasa.scheduling.interfaces.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.ImportantDates;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.enums.Status;
import com.vasa.scheduling.services.DailyMessageService;
import com.vasa.scheduling.services.FieldService;
import com.vasa.scheduling.services.SportService;

@RequestMapping("/messages/add")
@Controller
public class ImportantDatesAddController extends DefaultHandlerController{
	
	@Autowired
	private DailyMessageService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		return "messages/add";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		ImportantDates message = new ImportantDates();
		
		message.setMessage(request.getParameter("message"));
		
		String dateString = request.getParameter("date");
		Date d = null;
		try {
			d = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		
		message.setDate(date.getTime());
		
		service.save(message);
		
		return "user/home";
	}
	
}
