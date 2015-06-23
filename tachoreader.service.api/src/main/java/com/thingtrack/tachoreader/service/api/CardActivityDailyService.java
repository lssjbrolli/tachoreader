package com.thingtrack.tachoreader.service.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange.TYPE;

public interface CardActivityDailyService {
    public List<CardActivityDaily> getAll(Organization organization) throws Exception;
    public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date registerDate) throws Exception;
    public Map<TYPE, Float> getCardActivityDailyGraphByDriver(Driver driver, Date dailyDate) throws Exception;
	public CardActivityDaily createNewEntity(User user);
}
