package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.DriverActivityDao;
import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.DriverActivity;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.DriverActivityService;

public class DriverActivityServiceImpl implements DriverActivityService {
	@Autowired
	private DriverActivityDao driverDao;

	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public List<DriverActivity> getAll(Organization organization) throws Exception {
		return this.driverDao.getAll(organization);
	}
	
	@Override
	public DriverActivity createNewEntity(User user) {
		DriverActivity driverActivity = new DriverActivity();	
		
		driverActivity.setCreatedBy(user);
		driverActivity.setCreationDate(new Date());
		
		return driverActivity;
	}
}
