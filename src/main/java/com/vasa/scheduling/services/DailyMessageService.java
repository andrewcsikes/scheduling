package com.vasa.scheduling.services;

import java.util.Calendar;
import java.util.List;

import org.springframework.ui.Model;

import com.vasa.scheduling.domain.ImportantDates;

public interface DailyMessageService {

	public void addDailyMessages(Model model, Calendar sunday);

	public void addImportantMessage(ImportantDates message);

	public List<ImportantDates> findAll();

	public void save(ImportantDates message);

	public ImportantDates findImportantDatesById(Integer id);

	public void delete(ImportantDates message);

}
