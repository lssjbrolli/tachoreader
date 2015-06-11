package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.DriverDao;
import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.DriverService;

public class DriverServiceImpl implements DriverService {
	@Autowired
	private DriverDao driverDao;

	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public List<Driver> getAll(Organization organization) throws Exception {
		return this.driverDao.getAll(organization);
	}
		
	@Override
	public Driver getByUser(User user) throws Exception {
		return this.driverDao.getByUser(user);
	}
	
	@Override
	public Driver getByCardNumber(String cardNumber) throws Exception {
		return this.driverDao.getByCardNumber(cardNumber);
	}
	
	@Override
	public List<Driver> getAll(int pageNumber, int pageSize, User user,
			Integer id,  String name,  String email, String cardNumber,
			Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo,
			Date cardExpiryDateFrom, Date cardExpiryDateTo,
			Date creationDateFrom, Date creationDateTo, Boolean active) throws Exception {
		return this.driverDao.getAll(pageNumber, pageSize, user, id, name, email, cardNumber, 
									  cardHolderBirthDateFrom, cardHolderBirthDateTo, 
									  cardExpiryDateFrom, cardExpiryDateTo, 
									  creationDateFrom, creationDateTo, active);
	}

	@Override
	public int getCount(int pageSize, User user, Integer id, 
			String name, String email, String cardNumber,
			Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo, 
			Date cardExpiryDateFrom, Date cardExpiryDateTo, 
			Date creationDateFrom, Date creationDateTo, Boolean active)
			throws Exception {
		return this.driverDao.getCount(pageSize, user, id, name, email, cardNumber,  
									    cardHolderBirthDateFrom, cardHolderBirthDateTo, 
									    cardExpiryDateFrom, cardExpiryDateTo, 
									    creationDateFrom, creationDateTo, active);
	}
	
	@Override
	public Driver save(Driver driver) throws Exception {
		return this.driverDao.save(driver);
	}

	@Override
	public void delete(Driver driver) throws Exception {
		this.driverDao.delete(driver);	
	}
	
	@Override
	public Driver createNewEntity(User user) {
		Driver driver = new Driver();	
		
		driver.setOrganization(user.getOrganizationDefault());
		driver.setCreatedBy(user);
		driver.setCreationDate(new Date());
		driver.setActive(true);
		
		return driver;
	}
}
