package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.AdministratorDao;
import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public class AdministratorDaoImpl extends JpaDao<Administrator, Integer> implements AdministratorDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Administrator> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
	
	@Override
	public Administrator getByUser(User user) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.user = :user");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("user", user);
			
		return (Administrator) query.getSingleResult();
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Administrator> getAll(int pageNumber, int pageSize, User user,									  
							    Integer id, String name, String email, Boolean active,
								Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE :organization MEMBER OF p.organizations");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (name != null)
			queryString.append(" AND p.name LIKE :name");
		if (email != null)
			queryString.append(" AND p.email LIKE :email");
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
		if (name != null)
			query.setParameter("name", "%" + name + "%");
		if (email != null)
			query.setParameter("email", "%" + email + "%");
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
			Integer id, String name, String email, Boolean active,
			Date creationDateFrom, Date creationDateTo) throws Exception {		
		StringBuffer queryString = new StringBuffer("SELECT  COUNT(p) FROM " + getEntityName() + " p");
		queryString.append(" WHERE :organization MEMBER OF p.organizations");
		
		if (id != null)
			queryString.append(" AND p.id = :id");
		if (name != null)
			queryString.append(" AND p.name LIKE :name");
		if (email != null)
			queryString.append(" AND p.email LIKE :email");			
		if (creationDateFrom != null)
			queryString.append(" AND p.creationDate >= :creationDateFrom");
		if (creationDateTo != null)
			queryString.append(" AND p.creationDate <= :creationDateTo");	
		
		Query query = getEntityManager().createQuery(queryString.toString(), Integer.class);
		query.setParameter("organization", user.getOrganizationDefault());
		query.setMaxResults(pageSize);
		
		if (id != null)
			query.setParameter("id", id);
		if (name != null)
			query.setParameter("name", "%" + name + "%");
		if (email != null)
			query.setParameter("email", "%" + email + "%");
		if (creationDateFrom != null)
			query.setParameter("creationDateFrom", creationDateFrom);		
		if (creationDateTo != null)
			query.setParameter("creationDateTo", creationDateTo);
		
		Long totRegisters = (Long)query.getSingleResult();
		int count = (int) Math.ceil(totRegisters.intValue() / pageSize) + 1;
		
		return count;				
	}	
}
