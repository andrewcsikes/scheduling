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
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.ImportantDates;
import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.DailyMessageService;
import com.vasa.scheduling.services.FieldService;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/messages/modify")
@Controller
public class ImportantDatesModifyController extends DefaultHandlerController{
	
	@Autowired
	private DailyMessageService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(@RequestParam(required=true, value="messageId") Integer id,
			Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		ImportantDates f = service.findImportantDatesById(id);
		
		String date = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(f.getDate());
		
		model.addAttribute("date", date);
		model.addAttribute("message", f);
		return "messages/modify";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		String dateString = request.getParameter("date");
		
		Date d = null;
		try {
			d = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar date = Calendar.getInstance();
		date.setTime(d);
		
		ImportantDates message = null;		
		if(request.getParameter("id") != null){
			message = service.findImportantDatesById(Integer.valueOf(request.getParameter("id")));
		}
		
		if(request.getParameter("delete") != null){
			service.delete(message);
		}else{
			message.setDate(date.getTime());
			message.setMessage(request.getParameter("message"));
			service.save(message);
		}
				
		return "user/home";
	}
	
}
