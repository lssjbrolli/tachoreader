package com.thingtrack.tachoreader.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.VehicleService;

public class VehicleServiceImpl implements VehicleService {
	@Autowired
	private VehicleDao vehicleDao;

	@Autowired
	private OrganizationDao organizationDao;
	
	@Override
	public List<Vehicle> getAll(Organization organization) throws Exception {
		return this.vehicleDao.getAll(organization);
	}
		
	@Override
	public Vehicle getByUser(User user) throws Exception {
		return this.vehicleDao.getByUser(user);
	}
	
	@Override
	public Vehicle getByRegistration(String registration) throws Exception {
		return this.vehicleDao.getByRegistration(registration);
	}
	
	@Override
	public List<Vehicle> getAll(int pageNumber, int pageSize, User user,
			Integer id, String resgistration, String description, Boolean active,
			Date creationDateFrom, Date creationDateTo) throws Exception {
		return this.vehicleDao.getAll(pageNumber, pageSize, user, id, 
									   resgistration, description, active,									  
									   creationDateFrom, creationDateTo);
	}

	@Override
	public int getCount(int pageSize, User user, Integer id, 
						String resgistration, String description, Boolean active,
						Date creationDateFrom, Date creationDateTo)
						throws Exception {
		return this.vehicleDao.getCount(pageSize, user, id, 
										resgistration, description, active,									   
									    creationDateFrom, creationDateTo);
	}
	
	@Override
	public Vehicle save(Vehicle vehicle) throws Exception {
		return this.vehicleDao.save(vehicle);
	}

	@Override
	public void delete(Vehicle vehicle) throws Exception {
		this.vehicleDao.delete(vehicle);	
	}
	
	@Override
	public Vehicle createNewEntity(User user) {
		Vehicle vehicle = new Vehicle();	
		
		vehicle.setOrganization(user.getOrganizationDefault());
		vehicle.setCreatedBy(user);
		vehicle.setActive(true);
		
		return vehicle;
	}
}
