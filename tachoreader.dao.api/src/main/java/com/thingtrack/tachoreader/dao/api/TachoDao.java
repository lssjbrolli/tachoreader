package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;

public interface TachoDao extends Dao<Tacho, Integer> {
	public List<Tacho> getAll(Organization organization) throws Exception;
	public Tacho getByFile(String file) throws Exception;
	public List<Tacho> getAll(int pageNumber, int pageSize, User user,									  
			  Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			  Date creationDateFrom, Date creationDateTo) throws Exception;
	public int getCount(int pageSize, User user,									  
			 Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			 Date creationDateFrom, Date creationDateTo) throws Exception;	
}
