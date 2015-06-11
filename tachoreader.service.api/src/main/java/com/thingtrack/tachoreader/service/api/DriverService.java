package com.thingtrack.tachoreader.service.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface DriverService {
    public Driver getByUser(User user) throws Exception;
    public Driver getByCardNumber(String cardNumber) throws Exception;
    public List<Driver> getAll(Organization organization) throws Exception;
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
	public Driver save(Driver driver) throws Exception;
	public void delete(Driver driver) throws Exception;
	public Driver createNewEntity(User user);
}
