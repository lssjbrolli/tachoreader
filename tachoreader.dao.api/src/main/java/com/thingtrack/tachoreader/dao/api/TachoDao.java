package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoDao extends Dao<Tacho, Integer> {
	public List<Tacho> getAll(Organization organization) throws Exception;
	public Tacho getByFile(String file) throws Exception;
	public List<Tacho> getAll(List<Vehicle> Vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
}
