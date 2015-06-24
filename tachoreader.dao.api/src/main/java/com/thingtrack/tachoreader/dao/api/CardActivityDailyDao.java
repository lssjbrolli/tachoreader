package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange.TYPE;

public interface CardActivityDailyDao extends Dao<CardActivityDaily, Integer> {
	public List<CardActivityDaily> getAll(Organization organization) throws Exception;
	public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date registerDate) throws Exception;	
	public Map<TYPE, Float> getCardActivityDailyResumeByDriver(Driver driver, Date dailyDate) throws Exception;
}
