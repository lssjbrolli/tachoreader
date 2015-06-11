package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.TachoDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;

public class TachoDaoImpl extends JpaDao<Tacho, Integer> implements TachoDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Tacho> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.agent.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
	
	@Override
	public Tacho getByFile(String file) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.file = :file");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("file", file);
			
		return (Tacho) query.getSingleResult();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Tacho> getAll(int pageNumber, int pageSize, User user,									  
									  Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
									  Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.driver.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (cardNumber != null)
			queryString.append(" AND p.driver.cardNumber LIKE :cardNumber");
		if (driverName != null)
			queryString.append(" AND p.driver.name LIKE :driverName");		
		if (vehicleRegistrationNumber != null)
			queryString.append(" AND p.vehicle.registration LIKE :vehicleRegistrationNumber");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");		
		queryString.append(" ORDER BY p.id DESC");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		query.setParameter("organization", user.getOrganizationDefault());
		query.setFirstResult((pageNumber-1) * pageSize);
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (cardNumber != null)
			query.setParameter("cardNumber", "%" + cardNumber + "%");
		if (driverName != null)
			query.setParameter("driverName", "%" + driverName + "%");
		if (vehicleRegistrationNumber != null)
			query.setParameter("vehicleRegistrationNumber", "%" + vehicleRegistrationNumber + "%");
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		
		return query.getResultList();				
	}
	
	@Override
	public int getCount(int pageSize, User user,									  
						 Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
						 Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT  COUNT(p) FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.driver.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (cardNumber != null)
			queryString.append(" AND p.driver.cardNumber LIKE :cardNumber");
		if (driverName != null)
			queryString.append(" AND p.driver.name LIKE :driverName");		
		if (vehicleRegistrationNumber != null)
			queryString.append(" AND p.vehicle.registration LIKE :vehicleRegistrationNumber");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");
		
		Query query = getEntityManager().createQuery(queryString.toString(), Integer.class);
		query.setParameter("organization", user.getOrganizationDefault());
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (cardNumber != null)
			query.setParameter("cardNumber", "%" + cardNumber + "%");
		if (driverName != null)
			query.setParameter("driverName", "%" + driverName + "%");
		if (vehicleRegistrationNumber != null)
			query.setParameter("vehicleRegistrationNumber", "%" + vehicleRegistrationNumber + "%");
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		
		Long totRegisters = (Long)query.getSingleResult();
		int count = (int) Math.ceil(totRegisters.intValue() / pageSize) + 1;
		
		return count;				
	}	
}
