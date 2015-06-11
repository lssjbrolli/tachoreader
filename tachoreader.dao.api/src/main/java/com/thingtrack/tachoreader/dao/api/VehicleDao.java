package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface VehicleDao extends Dao<Vehicle, Integer> {
	public List<Vehicle> getAll(Organization organization) throws Exception;
	public Vehicle getByUser(User user) throws Exception;
	public Vehicle getByRegistration(String registration) throws Exception;
	public List<Vehicle> getAll(int pageNumber, int pageSize, User user,									  
		    Integer id, String registration, String description, Boolean active,
			Date creationDateFrom, Date creationDateTo) throws Exception;
	public int getCount(int pageSize, User user,									  
			 Integer id, String registration, String description, Boolean active,
			 Date creationDateFrom, Date creationDateTo) throws Exception;
}
