package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;

public class VehicleDaoImpl extends JpaDao<Vehicle, Integer> implements VehicleDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicle> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
	
	@Override
	public Vehicle getByUser(User user) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.user = :user");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("user", user);
			
		return (Vehicle) query.getSingleResult();
	}
	
	@Override
	public Vehicle getByRegistration(String registration) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.registration = :registration");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("registration", registration);
			
		return (Vehicle) query.getSingleResult();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Vehicle> getAll(int pageNumber, int pageSize, User user,									  
							    Integer id, String registration, String description, Boolean active, 
								Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (registration != null)
			queryString.append(" AND p.registration LIKE :registration");
		if (description != null)
			queryString.append(" AND p.description LIKE :description");
		if (active != null)
			queryString.append(" AND p.active = :active");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");		
		queryString.append(" ORDER BY p.id ASC");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		query.setParameter("organization", user.getOrganizationDefault());
		query.setFirstResult((pageNumber-1) * pageSize);
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (registration != null)
			query.setParameter("registration", "%" + registration + "%");
		if (description != null)
			query.setParameter("description", "%" + description + "%");
		if (active != null)
			query.setParameter("active", active);		
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		
		return query.getResultList();				
	}
	
	@Override
	public int getCount(int pageSize, User user,									  
						 Integer id, String registration, String description, Boolean active,
						 Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT  COUNT(p) FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (registration != null)
			queryString.append(" AND p.registration LIKE :registration");
		if (description != null)
			queryString.append(" AND p.description LIKE :description");
		if (active != null)
			queryString.append(" AND p.active = :active");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");		
		
		Query query = getEntityManager().createQuery(queryString.toString(), Integer.class);
		query.setParameter("organization", user.getOrganizationDefault());
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (registration != null)
			query.setParameter("registration", "%" + registration + "%");
		if (description != null)
			query.setParameter("description", "%" + description + "%");
		if (active != null)
			query.setParameter("active", active);		
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		
		Long totRegisters = (Long)query.getSingleResult();
		int count = (int) Math.ceil(totRegisters.intValue() / pageSize) + 1;
		
		return count;				
	}	
}
