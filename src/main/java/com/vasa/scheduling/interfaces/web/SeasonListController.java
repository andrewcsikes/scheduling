package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Season;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.SeasonService;

@Controller
@RequestMapping("/season/list")
public class SeasonListController extends DefaultHandlerController {

	@Autowired private SeasonService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		List<Season> seasons = service.findAll();	
	    model.addAttribute("seasons", seasons);
	    
	    return "season/list";
	}
}