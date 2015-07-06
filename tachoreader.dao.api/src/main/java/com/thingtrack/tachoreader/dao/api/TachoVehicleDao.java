package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.TachoVehicle;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoVehicleDao extends Dao<TachoVehicle, Integer> {
	public TachoVehicle getByFile(String file) throws Exception;
	public List<TachoVehicle> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
}
