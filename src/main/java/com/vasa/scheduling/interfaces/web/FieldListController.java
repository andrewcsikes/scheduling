package com.vasa.scheduling.interfaces.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.FieldService;

@Controller
@RequestMapping("/fields/list")
public class FieldListController extends DefaultHandlerController {

	@Autowired private FieldService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		List<Fields> fields = service.findAll();	
	    model.addAttribute("fields", fields);
	    
	    return "fields/list";
	}
}