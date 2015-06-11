package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface DriverDao extends Dao<Driver, Integer> {
	public List<Driver> getAll(Organization organization) throws Exception;
	public Driver getByUser(User user) throws Exception;
	public Driver getByCardNumber(String cardNumber) throws Exception;
	public List<Driver> getAll(int pageNumber, int pageSize, User user,									  
		    Integer id, String name, String email, String cardNumber,
		    Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo,
			Date cardExpiryDateFrom, Date cardExpiryDateTo, 
			Date creationDateFrom, Date creationDateTo, Boolean active) throws Exception;
	public int getCount(int pageSize, User user,									  
			 Integer id, String name, String email, String cardNumber, 
			 Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo,
			 Date cardExpiryDateFrom, Date cardExpiryDateTo, 
			 Date creationDateFrom, Date creationDateTo, Boolean active) throws Exception;
}
