package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.OrganizationService;

public class OrganizationServiceImpl implements OrganizationService {
	@Autowired
	private OrganizationDao organizationDao;

	@Override
	public List<Organization> getAll(User user) throws Exception {
		return this.organizationDao.getAll(user);
	}
	
	@Override
	public Organization getByCode(String code) throws Exception {
		return this.organizationDao.getByCode(code);
	}
	
	@Override
	public Organization save(Organization organization) throws Exception {		
		return this.organizationDao.save(organization);
	}
	
	@Override
	public void delete(Organization organization) throws Exception {
		this.organizationDao.delete(organization);	
	}
	
	@Override
	public Organization createNewEntity(User user) {
		Organization organization = new Organization();	
		
		organization.setCreatedBy(user);
		organization.setCreationDate(new Date());
		organization.setActive(true);
		
		return organization;
	}
}
