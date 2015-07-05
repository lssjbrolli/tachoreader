package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.TachoDriverDao;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.TachoDriver;
import com.thingtrack.tachoreader.domain.Vehicle;

public class TachoDriverDaoImpl extends JpaDao<TachoDriver, Integer> implements TachoDriverDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<TachoDriver> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.agent.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}
	
	@Override
	public TachoDriver getByFile(String file) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.file = :file");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("file", file);
			
		return (TachoDriver) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TachoDriver> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM CardActivityDaily c JOIN c.tachos p");
		queryString.append(" WHERE 1=1");
		
		if (vehicles.size() > 0)
			queryString.append(" AND c.vehicles IN :vehicles");
		if (startActivityDate != null)
			queryString.append(" AND c.dailyDate >= :startActivityDate");
		if (endActivityDate != null)
			queryString.append(" AND c.dailyDate <= :endActivityDate");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		
		if (vehicles.size() > 0)
			query.setParameter("vehicles", vehicles);
		if (startActivityDate != null)
			query.setParameter("startActivityDate", startActivityDate);		
		if (endActivityDate != null)
			query.setParameter("endActivityDate", endActivityDate);
		
		return query.getResultList();
	}
}
