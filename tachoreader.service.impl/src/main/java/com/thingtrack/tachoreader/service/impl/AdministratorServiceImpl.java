package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.AdministratorDao;
import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.AdministratorService;
import com.thingtrack.tachoreader.service.api.UserService;

public class AdministratorServiceImpl implements AdministratorService {
	@Autowired
	private AdministratorDao administratorDao;

	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<Administrator> getAll(Organization organization) throws Exception {
		return this.administratorDao.getAll(organization);
	}
		
	@Override
	public Administrator getByUser(User user) throws Exception {
		return this.administratorDao.getByUser(user);
	}
	
	@Override
	public List<Administrator> getAll(int pageNumber, int pageSize, User user,
			Integer id, String name, String email, Boolean filterActive, Date creationDateFrom,
			Date creationDateTo) throws Exception {
		return this.administratorDao.getAll(pageNumber, pageSize, user, id, 
											 name, email, filterActive, creationDateFrom, creationDateTo);
	}



	@Override
	public int getCount(int pageSize, User user, Integer id, String name,
			String email, Boolean filterActive, Date creationDateFrom, Date creationDateTo)
			throws Exception {
		return this.administratorDao.getCount(pageSize, user, id, 
											   name, email, filterActive, creationDateFrom, creationDateTo);
	}
	
	@Override
	public Administrator save(Administrator administrator) throws Exception {
		return this.administratorDao.save(administrator);
	}

	@Override
	public void delete(Administrator administrator) throws Exception {
		this.administratorDao.delete(administrator);	
	}
	
	@Override
	public Administrator createNewEntity(User user) {
		Administrator administrator = new Administrator();	
		
		//administrator.addOrganization(user.getOrganizationDefault());
		administrator.setCreatedBy(user);
		administrator.setCreationDate(new Date());
		administrator.setActive(true);
		administrator.setUser(userService.createNewEntity(user.getOrganizationDefault(), administrator, user.getLanguage()));	
		
		return administrator;
	}
	
	@Override
	public Administrator setRegisterAgent(String organizationCode, String organizationName, String name, String surname, String email, String password, String language) throws Exception {
		User user = new User();
		Administrator administrator = new Administrator();
		
		// register the organization if not exist from VAT
		Organization organization = null;
		try
		{
			organization = organizationDao.getByCode(organizationCode);
		}
		catch(Exception ex) {
			// create the new user organization	
			organization = new Organization();
			
			organization.setCode(organizationCode);
			organization.setName(organizationName);
			organization.setActive(true);
			organization.setCreationDate(new Date());
			organization.setCreatedBy(user);
			
			organizationDao.save(organization);
		}
				
		// create the new administrator
		administrator.setName(name + " " + surname);
		administrator.setEmail(email);
		administrator.setUser(user);
		administrator.addOrganization(organization);
		administrator.setActive(true);
		administrator.setCreatedBy(user);
		administrator.setCreationDate(new  Date());
		
		// create the new user account		
		user.setUsername(email);
		user.setPassword(password);
		user.setOrganizationDefault(organization);
		user.setLanguage(language);		
		user.setAgent(administrator);
		user.setActive(true);
		user.setCreationDate(new Date());
		
		return this.administratorDao.save(administrator);
	}
}
