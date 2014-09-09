package com.vasa.scheduling.converters;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.vasa.scheduling.domain.AgeGroup;
import com.vasa.scheduling.domain.FieldSchedule;
import com.vasa.scheduling.domain.Team;
import com.vasa.scheduling.services.ScheduleService;
import com.vasa.scheduling.services.TeamService;

public class CsvToFieldSchedule {
	
	public List<FieldSchedule> readCsv(ScheduleService service, TeamService teamService, BufferedReader br, ArrayList<String> errors) throws NumberFormatException, IOException, ParseException{
		
		List<FieldSchedule> schedules = new ArrayList<FieldSchedule>();
		
		String line = "";
		String splitBy = ",";
		int rowPosition = 0;

		while ((line = br.readLine()) != null) {

				if(rowPosition==0){
					rowPosition++;
					continue;
				}
				
				String[] row = line.split(splitBy);
				
				String duration = row[3];
				int multiple = 0;
				
				if(duration.indexOf("h")>0){
					String hours = duration.substring(0, duration.indexOf("h")).trim();
					multiple = Integer.valueOf(hours) * 2;
				}
				if(duration.indexOf("m")>0){
					multiple += 1;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date d = sdf.parse(row[1].trim());
				
				Calendar date = Calendar.getInstance();
				date.setTime(d);
				
				String time = row[2];
				String hour = time.substring(0, time.indexOf(':')).trim();
				String minute = time.substring(time.indexOf(':')+1, time.length()).trim();
				
				date.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
				date.set(Calendar.MINUTE, Integer.valueOf(minute));
								
				if(multiple>0){
					date.add(Calendar.MINUTE, -30);
					for(int x=0; x<multiple; x++){
						FieldSchedule schedule = new FieldSchedule();
						schedule.setCreationDate(new Date());
						schedule.setField(service.findFieldByName(row[0].trim()));
						date.add(Calendar.MINUTE, 30);
						schedule.setDate(date.getTime());
						schedule.setGame(true);
						
						AgeGroup ag = teamService.findAgeGroupByName(row[4].trim());
						if(ag != null){
							Team t = teamService.findByNameAndAgeGroup(row[5].trim(), ag);
							if(t==null){
								t = teamService.findByNameAndAgeGroup(row[6].trim(), ag);
							}
							if(t != null){
								schedule.setTeam(t);
							}
						}
						
						if(x==0){
							schedule.setGameDescription(row[4].trim() + " Game: "+ row[5].trim() + " vs "+ row[6].trim());
						}
						else{
							schedule.setGameDescription("Game");
						}
						if(schedule.getField()==null){
							errors.add("Incorrect Field "+ row[0]+ " at line "+(rowPosition+1));
							return null;
						}
						schedules.add(schedule);
						System.out.println(schedule.toString());
					}
				}else{
					FieldSchedule schedule = new FieldSchedule();
					schedule.setCreationDate(new Date());
					schedule.setDate(date.getTime());
					schedule.setGame(true);
					schedule.setGameDescription(row[4].trim() + " Game: "+ row[5].trim() + " vs "+ row[6].trim());
					schedule.setField(service.findFieldByName(row[0].trim()));
					if(schedule.getField()==null){
						errors.add("Incorrect Field "+ row[0]+ " at line "+(rowPosition+1));
						break;
					}
					schedules.add(schedule);
					System.out.println(schedule.toString());
				}				
				rowPosition++;
			}
		return schedules;
	}
}
