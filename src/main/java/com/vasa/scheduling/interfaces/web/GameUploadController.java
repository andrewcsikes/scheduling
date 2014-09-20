package com.vasa.scheduling.interfaces.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vasa.scheduling.converters.CsvToFieldSchedule;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.domain.User;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

@RequestMapping("/game/upload")
@Controller
public class GameUploadController extends DefaultHandlerController{
	
	@Autowired
	private ScheduleService scheduleService;
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) {
		
		User user = verifyUser(request.getSession());
		model.addAttribute("user", user);
		
		if(user== null){
			return "login";
		}
		
		return "game/upload";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String save(Model model, HttpServletRequest request) {
		
		
		User realUser = verifyUser(request.getSession());
		
		if(realUser== null){
			return "login";
		}
		
		try{
		
		String contentType = request.getContentType();
	      if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
	            DataInputStream in = new DataInputStream(request.getInputStream());
	            int formDataLength = request.getContentLength();
	            byte dataBytes[] = new byte[formDataLength];
	            int byteRead = 0;
	            int totalBytesRead = 0;
	            while (totalBytesRead < formDataLength) {
	            	  byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
	                  totalBytesRead += byteRead;
	            }
	            String file = new String(dataBytes);
	            int lastIndex = contentType.lastIndexOf("=");
	            String boundary = contentType.substring(lastIndex + 1, contentType.length());
	            int pos;
	            pos = file.indexOf("filename=\"");
	            pos = file.indexOf("\n", pos) + 1;
	            pos = file.indexOf("\n", pos) + 1;
	            pos = file.indexOf("\n", pos) + 1;
	            int boundaryLocation = file.indexOf(boundary, pos) - 4;
	            int startPos = ((file.substring(0, pos)).getBytes()).length;
	            int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
	            InputStream is = new ByteArrayInputStream(file.substring(startPos, endPos).getBytes());
	            BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            
	            CsvToFieldSchedule converter = new CsvToFieldSchedule();
	            ArrayList<String> errors = new ArrayList<String>();
	            
	            try{
	            	List<Game> schedules = converter.readCsv(scheduleService, teamService, br, errors);
	            	for(Game s : schedules){
	            		try{
	            			scheduleService.save(s);
	            		}catch(Exception e){
	            			if(e.getMessage().contains("UNIQUE")){
		            			errors.add("Time slot already taken for "+s.toString());
	            			}else{
	            				errors.add(e.getCause()+ ": "+ e.getMessage());
	            			}
	            		}
	            	}
	            	if(errors.size()>0){
	            		model.addAttribute("error", errors.toString());
	            	}
	            }catch(Exception e){
	            	model.addAttribute("error", e.getCause()+ ": "+ e.getMessage());
	            } finally {
	    			if (br != null) {
	    				try {
	    					br.close();
	    				} catch (IOException e) {
	    					e.printStackTrace();
	    				}
	    			}
	    		}
	      }	
		
		}catch(Exception e){
			
		}
		
		return "schedule/list";
	}
	
}
