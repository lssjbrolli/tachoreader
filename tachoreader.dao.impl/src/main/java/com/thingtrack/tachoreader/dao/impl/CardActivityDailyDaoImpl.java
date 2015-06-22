package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.CardActivityDailyDao;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;

public class CardActivityDailyDaoImpl extends JpaDao<CardActivityDaily, Integer> implements CardActivityDailyDao {
	@SuppressWarnings("unchecked")
	@Override
	public List<CardActivityDaily> getAll(Organization organization) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		if (organization != null)
			queryString.append(" WHERE p.driver.organization = :organization");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		if (organization != null)
			query.setParameter("organization", organization);
			
		return query.getResultList();
	}

	@Override
	public CardActivityDaily getCardActivityDailyByDriver(Driver driver, Date dailyDate) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.driver = :driver");
		queryString.append(" AND p.dailyDate = :dailyDate");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("driver", driver);
		query.setParameter("dailyDate", dailyDate, TemporalType.DATE);
		
		return (CardActivityDaily) query.getSingleResult();
	}
}
