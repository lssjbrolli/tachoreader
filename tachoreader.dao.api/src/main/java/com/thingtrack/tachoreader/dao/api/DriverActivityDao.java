package com.thingtrack.tachoreader.dao.api;

import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.DriverActivity;
import com.thingtrack.tachoreader.domain.Organization;

public interface DriverActivityDao extends Dao<DriverActivity, Integer> {
	public List<DriverActivity> getAll(Organization organization) throws Exception;
}
