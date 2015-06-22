package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.DriverDao;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public class DriverDaoImpl extends JpaDao<Driver, Integer> implements DriverDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Driver> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.active = 1");
		
		if (organization != null)
			queryString.append(" AND p.organization = :organization");
		
		queryString.append(" ORDER BY p.name");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
	
	@Override
	public Driver getByUser(User user) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.user = :user");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("user", user);
			
		return (Driver) query.getSingleResult();
	}
	
	@Override
	public Driver getByCardNumber(String cardNumber) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.cardNumber = :cardNumber");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("cardNumber", cardNumber);
			
		return (Driver) query.getSingleResult();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Driver> getAll(int pageNumber, int pageSize, User user,									  
							    Integer id, String name, String email, String cardNumber, 
							    Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo,
								Date cardExpiryDateFrom, Date cardExpiryDateTo, 
								Date creationDateFrom, Date creationDateTo, Boolean active) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (name != null)
			queryString.append(" AND p.name LIKE :name");
		if (email != null)
			queryString.append(" AND p.email LIKE :email");		
		if (cardNumber != null)
			queryString.append(" AND p.cardNumber LIKE :cardNumber");
		if (cardHolderBirthDateFrom != null)
			queryString.append(" AND p.cardHolderBirthDate >= :cardHolderBirthDateFrom");
		if (cardHolderBirthDateTo != null)
			queryString.append(" AND p.cardHolderBirthDate <= :cardHolderBirthDateTo");		
		if (cardExpiryDateFrom != null)
			queryString.append(" AND p.cardExpiryDate >= :cardExpiryDateFrom");
		if (cardExpiryDateTo != null)
			queryString.append(" AND p.cardExpiryDate <= :cardExpiryDateTo");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");
		if (active != null)
			queryString.append(" AND p.active = :active");			
		queryString.append(" ORDER BY p.id ASC");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		query.setParameter("organization", user.getOrganizationDefault());
		query.setFirstResult((pageNumber-1) * pageSize);
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (name != null)
			query.setParameter("name", "%" + name + "%");
		if (email != null)
			query.setParameter("email", "%" + email + "%");		
		if (cardNumber != null)
			query.setParameter("cardNumber", "%" + cardNumber + "%");
		if (cardHolderBirthDateFrom != null)
			query.setParameter("cardHolderBirthDateFrom", cardHolderBirthDateFrom);		
		if (cardHolderBirthDateTo != null)
			query.setParameter("cardHolderBirthDateTo", cardHolderBirthDateTo);
		if (cardExpiryDateFrom != null)
			query.setParameter("cardExpiryDateFrom", cardExpiryDateFrom);		
		if (cardExpiryDateTo != null)
			query.setParameter("creationDateTo", cardExpiryDateTo);
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		if (active != null)
			query.setParameter("active", active);
		
		return query.getResultList();				
	}
	
	@Override
	public int getCount(int pageSize, User user,									  
						 Integer id, String name, String email, String cardNumber, 
						 Date cardHolderBirthDateFrom, Date cardHolderBirthDateTo,
						 Date cardExpiryDateFrom, Date cardExpiryDateTo, 
						 Date creationDateFrom, Date creationDateTo, Boolean active) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT  COUNT(p) FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.organization = :organization");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (name != null)
			queryString.append(" AND p.name LIKE :name");
		if (email != null)
			queryString.append(" AND p.email LIKE :email");		
		if (cardNumber != null)
			queryString.append(" AND p.cardNumber LIKE :cardNumber");
		if (cardHolderBirthDateFrom != null)
			queryString.append(" AND p.cardHolderBirthDate >= :cardHolderBirthDateFrom");
		if (cardHolderBirthDateTo != null)
			queryString.append(" AND p.cardHolderBirthDate <= :cardHolderBirthDateTo");		
		if (cardExpiryDateFrom != null)
			queryString.append(" AND p.cardExpiryDate >= :cardExpiryDateFrom");
		if (cardExpiryDateTo != null)
			queryString.append(" AND p.cardExpiryDate <= :cardExpiryDateTo");		
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");		
		if (active != null)
			queryString.append(" AND p.active = :active");
		
		Query query = getEntityManager().createQuery(queryString.toString(), Integer.class);
		query.setParameter("organization", user.getOrganizationDefault());
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (cardNumber != null)
			query.setParameter("cardNumber", "%" + cardNumber + "%");
		if (name != null)
			query.setParameter("name", "%" + name + "%");
		if (cardHolderBirthDateFrom != null)
			query.setParameter("cardHolderBirthDateFrom", cardHolderBirthDateFrom);		
		if (cardHolderBirthDateTo != null)
			query.setParameter("cardHolderBirthDateTo", cardHolderBirthDateTo);
		if (cardExpiryDateFrom != null)
			query.setParameter("cardExpiryDateFrom", cardExpiryDateFrom);		
		if (cardExpiryDateTo != null)
			query.setParameter("creationDateTo", cardExpiryDateTo);
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		if (active != null)
			query.setParameter("active", active);
		
		Long totRegisters = (Long)query.getSingleResult();
		int count = (int) Math.ceil(totRegisters.intValue() / pageSize) + 1;
		
		return count;				
	}	
}
