package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.TachoDriver;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoDriverDao extends Dao<TachoDriver, Integer> {
	public List<TachoDriver> getAll(Organization organization) throws Exception;
	public TachoDriver getByFile(String file) throws Exception;
	public List<TachoDriver> getAll(List<Vehicle> Vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
}
