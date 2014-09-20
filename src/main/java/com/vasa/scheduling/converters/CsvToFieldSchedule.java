package com.vasa.scheduling.converters;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.AgeGroup;
import com.vasa.scheduling.domain.Game;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

public class CsvToFieldSchedule {
	
	public List<Game> readCsv(ScheduleService service, TeamService teamService, BufferedReader br, ArrayList<String> errors) throws NumberFormatException, IOException, ParseException{
		
		List<Game> games = new ArrayList<Game>();
		
		String line = "";
		String splitBy = ",";
		int rowPosition = 0;

		while ((line = br.readLine()) != null) {

				if(rowPosition==0){
					rowPosition++;
					continue;
				}
				
				String[] row = line.split(splitBy);
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = sdf.parse(row[1].trim());
				
				Calendar date = Calendar.getInstance();
				date.setTime(d);
				
				String time = row[2];
				String hour = time.substring(0, time.indexOf(':')).trim();
				String minute = time.substring(time.indexOf(':')+1, time.length()).trim();
				
				date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
				date.set(Calendar.MINUTE, Integer.valueOf(minute));
				
				Game schedule = new Game();
				schedule.setCreationDate(new Date());
				schedule.setField(service.findFieldByName(row[0].trim()));
				schedule.setDate(date.getTime());
				schedule.setDuration(row[3]);
				
				AgeGroup ag = teamService.findAgeGroupByName(row[4].trim());
				schedule.setAgeGroup(ag);
				if(schedule.getField()==null){
					errors.add("Incorrect Field "+ row[0]+ " at line "+(rowPosition+1));
					return null;
				}
				
				schedule.setHomeTeam(row[5].trim());
				schedule.setAwayTeam(row[6].trim());
				
				games.add(schedule);
				//System.out.println(schedule.toString());
				
				rowPosition++;
			}
		return games;
	}
}
