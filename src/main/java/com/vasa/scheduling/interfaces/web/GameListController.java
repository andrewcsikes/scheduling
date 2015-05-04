package com.vasa.scheduling.interfaces.web;

import java.util.List;

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
		
		List<Game> games = service.findAllGames();	
	    model.addAttribute("games", games);
	    
	    return "game/list";
	}
}