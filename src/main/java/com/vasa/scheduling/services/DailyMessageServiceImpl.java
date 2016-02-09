package com.vasa.scheduling.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.vasa.scheduling.domain.ImportantDates;
import com.vasa.scheduling.repositiories.ImportantDatesRepository;
import com.vasa.scheduling.weather.Weather;
import com.vasa.scheduling.weather.YahooWeatherParser;

@Service("dailyMessageService")
public class DailyMessageServiceImpl implements DailyMessageService {
	
	@Autowired
	private	ImportantDatesRepository impDatesRepo;

	@Override
	public void addDailyMessages(Model model, Calendar sunday) {
		Weather w = new Weather();
		
		addImportandDateMessages(w);
		addWeather(w, model, sunday);
	}

	private void addWeather(Weather w, Model model, Calendar sunday) {
		try {
			YahooWeatherParser weatherParser = new YahooWeatherParser();
			weatherParser.parse(w, "75495");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String date = formatter.format(sunday.getTime());
		String condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("SundayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("MondayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("TuesdayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("WednesdayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("ThursdayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("FridayWeather",date +": "+condition);
		}
		
		sunday.add(Calendar.DAY_OF_YEAR, 1);
		formatter = new SimpleDateFormat("MM/dd/yyyy");
		date = formatter.format(sunday.getTime());
		condition = w.getWeather(date);
		if(condition != null && condition.length()>0){
			model.addAttribute("SaturdayWeather",date +": "+condition);
		}		
	}

	private void addImportandDateMessages(Weather w) {
		
		List<ImportantDates> messages = impDatesRepo.findAll();
		for(ImportantDates id : messages){
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
			String date = sdf.format(id.getDate());
			w.addMessage(date, id.getMessage());
		}
	}
	
	@Override
	public void addImportantMessage(ImportantDates message){
		impDatesRepo.save(message);
	}
	
	@Override
	public List<ImportantDates> findAll(){
		return impDatesRepo.findAll();
	}
	
	@Override
	public void save(ImportantDates message){
		impDatesRepo.save(message);
	}
	
	@Override
	public ImportantDates findImportantDatesById(Integer id){
		return impDatesRepo.findOne(id);
	}
	
	@Override
	public void delete(ImportantDates message){
		impDatesRepo.delete(message);
	}
}
