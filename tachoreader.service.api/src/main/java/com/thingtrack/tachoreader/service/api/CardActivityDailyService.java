package com.thingtrack.tachoreader.service.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface CardActivityDailyService {
    public List<CardActivityDaily> getAll(Organization organization) throws Exception;
    public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date registerDate) throws Exception;
	public CardActivityDaily createNewEntity(User user);
}
