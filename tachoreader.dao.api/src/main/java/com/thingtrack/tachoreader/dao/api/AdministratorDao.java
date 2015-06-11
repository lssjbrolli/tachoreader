package com.thingtrack.tachoreader.dao.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface AdministratorDao extends Dao<Administrator, Integer> {
	public List<Administrator> getAll(Organization organization) throws Exception;
	public Administrator getByUser(User user) throws Exception;
	public List<Administrator> getAll(int pageNumber, int pageSize, User user,									  
		    Integer id, String name, String email, Boolean active,
			Date creationDateFrom, Date creationDateTo) throws Exception;
	public int getCount(int pageSize, User user,									  
			Integer id, String name, String email, Boolean active,
			Date creationDateFrom, Date creationDateTo) throws Exception;
}
