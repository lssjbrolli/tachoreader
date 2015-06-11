package com.thingtrack.tachoreader.service.api;

import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface AdministratorService {
    public List<Administrator> getAll(Organization organization) throws Exception;
    public List<Administrator> getAll(int pageNumber, int pageSize, User user,									  
		    Integer id, String name, String email, Boolean filterActive,
			Date creationDateFrom, Date creationDateTo) throws Exception;
    public int getCount(int pageSize, User user,									  
			Integer id, String name, String email, Boolean filterActive,
			Date creationDateFrom, Date creationDateTo) throws Exception;
    public Administrator getByUser(User user) throws Exception;
	public Administrator save(Administrator administrator) throws Exception;
	public void delete(Administrator administrator) throws Exception;
	public Administrator createNewEntity(User user);
	public Administrator setRegisterAgent(String organizationCode, String organizationName, String name, String surname, String email, String password, String language) throws Exception;
}
