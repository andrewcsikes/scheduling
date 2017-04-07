package com.vasa.scheduling.interfaces.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.domain.BaseballPositions;

@RequestMapping("/baseball/sikes")
@Controller
public class BaseballPositionsController {

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, HttpServletRequest request) {
		return "baseball/sikes";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String form(Model model, HttpServletRequest request) {
		
		String pitcher = request.getParameter("pitcher");
		String catcher = request.getParameter("catcher");
		String bench1 = request.getParameter("bench1");
		String bench2 = request.getParameter("bench2");
		BaseballPositions bp = buildPositions(pitcher,catcher,bench1,bench2);
		model.addAttribute("positions", bp);
		
		return "baseball/sikes";
	}
	
	private static BaseballPositions buildPositions(String pitcher, String catcher, String bench1, String bench2){
		
		List<String> firstList = new ArrayList(java.util.Arrays.asList("Garrett","Rhett","Mitch", "Brady"));
		List<String> secondList = new ArrayList(java.util.Arrays.asList("Tucker","Nick","Brady", "Rhett", "Mitch"));
		List<String> ssList = new ArrayList(java.util.Arrays.asList("Brayden","Tucker","Brady", "Rhett", "Nick"));
		List<String> thirdList = new ArrayList(java.util.Arrays.asList("Rhett","Garrett","Brady", "Mitch", "Nick", "James"));
		List<String> lfList = new ArrayList(java.util.Arrays.asList("Conner","Brady","Mitch","Nick","Robert","Luke"));
		List<String> cfList = new ArrayList(java.util.Arrays.asList("James","Brady","Mitch","Robert","Conner","Luke"));
		List<String> rfList = new ArrayList(java.util.Arrays.asList("Luke","Conner","Robert","Brady","Mitch","Nick"));
		List<String> playerList = new ArrayList(java.util.Arrays.asList("Garrett","Brayden","Tucker","Rhett","Nick","Brady","Mitch","James","Robert","Luke","Conner"));
		
		BaseballPositions bp = new BaseballPositions();
		bp.setPitcher(pitcher);
		bp.setCatcher(catcher);
		bp.setBench1(bench1);
		bp.setBench2(bench2);
		
		for(String player : firstList){
			if(!pitcher.contains(player) && 
					!pitcher.contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setFirst(player);
				break;
			}
		}
		
		for(String player : ssList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setSs(player);
				break;
			}
		}
		
		for(String player : secondList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bp.getSs().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setSecond(player);
				break;
			}
		}
		
		for(String player : thirdList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bp.getSs().contains(player) &&
					!bp.getSecond().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setThird(player);
				break;
			}
		}
		
		for(String player : lfList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bp.getSs().contains(player) &&
					!bp.getSecond().contains(player) &&
					!bp.getThird().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setLf(player);
				break;
			}
		}
		
		for(String player : cfList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bp.getSs().contains(player) &&
					!bp.getSecond().contains(player) &&
					!bp.getThird().contains(player) &&
					!bp.getLf().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setCf(player);
				break;
			}
		}
		
		for(String player : rfList){
			if(!pitcher.contains(player) && 
					!catcher.contains(player) &&
					!bp.getFirst().contains(player) &&
					!bp.getSs().contains(player) &&
					!bp.getSecond().contains(player) &&
					!bp.getThird().contains(player) &&
					!bp.getLf().contains(player) &&
					!bp.getCf().contains(player) &&
					!bench1.contains(player) &&
					!bench2.contains(player)){
				bp.setRf(player);
				break;
			}
		}
		
		return bp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args){
		
		String pitcher = "Garrett";
		String catcher = "Brady";
		String bench1 = "Conner";
		String bench2 = "Luke";
		
		BaseballPositions bp = buildPositions(pitcher,catcher,bench1,bench2);
		
		System.out.println("Pitcher: "+pitcher);
		System.out.println("Catcher: "+catcher);
		System.out.println("1st: "+bp.getFirst());
		System.out.println("2nd: "+bp.getSecond());
		System.out.println("SS: "+bp.getSs());
		System.out.println("3rd: "+bp.getThird());
		System.out.println("LF: "+bp.getLf());
		System.out.println("CF: "+bp.getCf());
		System.out.println("RF: "+bp.getRf());
		System.out.println("Bench 1: "+bench1);
		System.out.println("Bench 2: "+bench2);
		
	}
}
