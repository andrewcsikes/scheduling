package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.vasa.scheduling.domain.Log;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;

@Controller
@RequestMapping("/logs/list")
public class LogListController extends DefaultHandlerController {

	@Autowired private ScheduleService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(required=false, value="clear") Boolean clear, Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		if(clear){
			service.clearLogs();
		}else{	
			List<Log> logs = service.findAllLogs();	
			model.addAttribute("logs", logs);
		}
	    
	    return "logs/list";
	}
}