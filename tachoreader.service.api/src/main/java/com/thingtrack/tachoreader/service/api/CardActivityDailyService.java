package com.thingtrack.tachoreader.service.api;

import java.util.Date;
import java.util.Map;

import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange.TYPE;

public interface CardActivityDailyService {
    public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date registerDate) throws Exception;
    public Map<TYPE, Float> getCardActivityDailyResumeByDriver(Driver driver, Date dailyDate) throws Exception;
	public CardActivityDaily createNewEntity(User user);
}
