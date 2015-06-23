package com.thingtrack.tachoreader.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.CardActivityDailyDao;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange.TYPE;
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
	
	@Override
	public Map<TYPE, Float> getCardActivityDailyGraphByDriver(Driver driver, Date dailyDate) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		queryString.append(" WHERE p.driver = :driver");
		queryString.append(" AND p.dailyDate = :dailyDate");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("driver", driver);
		query.setParameter("dailyDate", dailyDate, TemporalType.DATE);
		
		CardActivityDaily cardActivityDaily = (CardActivityDaily) query.getSingleResult();
		List<CardActivityDailyChange> cardActivityDailyChanges = cardActivityDaily.getCardActivityDailyChanges();
				
		Map<TYPE, Float> cardActivityDailyChangeGraphs = new HashMap<TYPE, Float>();
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.AVAILABLE, 0.0f);
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.BREAK_REST, 0.0f);
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.DRIVING, 0.0f);
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.SHORT_BREAK, 0.0f);
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.UNKNOWN, 0.0f);
		cardActivityDailyChangeGraphs.put(CardActivityDailyChange.TYPE.WORKING, 0.0f);
		
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < cardActivityDailyChanges.size(); i++) {
			Date startCardActivityDailyChangeTime = null;
			Date endCardActivityDailyChangeTime = null;
			
			if (i < cardActivityDailyChanges.size()-1) {								
				startCardActivityDailyChangeTime = cardActivityDailyChanges.get(i).getRecordDate();
				endCardActivityDailyChangeTime = cardActivityDailyChanges.get(i+1).getRecordDate();			
			}
			else {
				startCardActivityDailyChangeTime = cardActivityDailyChanges.get(i).getRecordDate();
				calendar.setTime(startCardActivityDailyChangeTime);
				calendar.set(Calendar.HOUR_OF_DAY, 24);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				endCardActivityDailyChangeTime = calendar.getTime();
			}
			
        	// calculate hours gap
        	long diff = endCardActivityDailyChangeTime.getTime() - startCardActivityDailyChangeTime.getTime();
        	long diffHours = diff / (60 * 60 * 1000) % 24;
        	long diffMinutes = diff / (60 * 1000) % 60;
        	
        	float width = Float.valueOf(diffHours) + Float.valueOf(diffMinutes)/60;  
        	
        	// round two decimals
        	float val = Math.round(cardActivityDailyChangeGraphs.get(cardActivityDailyChanges.get(i).getType()) + width) * 100;
        	Math.round(val);
        	val = val / 100;
        	
        	cardActivityDailyChangeGraphs.put(cardActivityDailyChanges.get(i).getType(), val); 
		}
		
		return cardActivityDailyChangeGraphs;
	}
}
