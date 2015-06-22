package com.thingtrack.tachoreader.dao.api;

import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Organization;

public interface CardActivityDailyDao extends Dao<CardActivityDaily, Integer> {
	public List<CardActivityDaily> getAll(Organization organization) throws Exception;
}
