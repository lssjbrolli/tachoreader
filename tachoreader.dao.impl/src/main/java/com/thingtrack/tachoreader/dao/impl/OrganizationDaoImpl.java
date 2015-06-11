package com.thingtrack.tachoreader.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.OrganizationDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public class OrganizationDaoImpl extends JpaDao<Organization, Integer> implements OrganizationDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getAll(User user) {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.createdBy = :user");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		query.setParameter("user", user);		
		
		return query.getResultList();
	}

	@Override
	public Organization getByCode(String code) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.code = :code");
		   
		Query query = (Query) getEntityManager().createQuery(queryString.toString())
					.setParameter("code", code);
		
		return (Organization) query.getSingleResult();
	}
}
