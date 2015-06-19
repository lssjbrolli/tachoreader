package com.thingtrack.tachoreader.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.DriverActivityDao;
import com.thingtrack.tachoreader.domain.DriverActivity;
import com.thingtrack.tachoreader.domain.Organization;

public class DriverActivityDaoImpl extends JpaDao<DriverActivity, Integer> implements DriverActivityDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<DriverActivity> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.driver.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
}
