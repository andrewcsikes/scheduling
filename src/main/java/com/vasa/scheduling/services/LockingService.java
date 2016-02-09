package com.vasa.scheduling.services;

import java.util.ArrayList;
import java.util.Date;

import com.vasa.scheduling.domain.Fields;
import com.vasa.scheduling.domain.Team;

public interface LockingService {

	boolean getLocked(Team team, Fields field, Date startOfWeek);

	void setBlockedTimesBasedOnRules(Fields field, Team team, Date date, ArrayList<String> day);

}
