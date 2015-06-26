package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.CardActivityDailyDao;
import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.CardActivityChange.TYPE;
import com.thingtrack.tachoreader.service.api.CardActivityDailyService;

public class CardActivityDailyServiceImpl implements CardActivityDailyService {
	@Autowired
	private CardActivityDailyDao cardActivityDailyDao;

	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date registerDate) throws Exception {
		return this.cardActivityDailyDao.getCardActivityDailyByDriver(driver, registerDate);
	}
	
	@Override
	public Map<TYPE, Float> getCardActivityDailyResumeByDriver(Driver driver, Date dailyDate) throws Exception {
		return this.cardActivityDailyDao.getCardActivityDailyResumeByDriver(driver, dailyDate);
	}
	
	@Override
	public CardActivityDaily createNewEntity(User user) {
		CardActivityDaily cardActivityDaily = new CardActivityDaily();	
		
		cardActivityDaily.setCreatedBy(user);
		cardActivityDaily.setCreationDate(new Date());
		
		return cardActivityDaily;
	}
}
