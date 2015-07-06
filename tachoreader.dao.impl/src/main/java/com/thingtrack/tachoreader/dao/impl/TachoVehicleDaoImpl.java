package com.thingtrack.tachoreader.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.TachoVehicleDao;
import com.thingtrack.tachoreader.domain.TachoVehicle;
import com.thingtrack.tachoreader.domain.Vehicle;

public class TachoVehicleDaoImpl extends JpaDao<TachoVehicle, Integer> implements TachoVehicleDao {
	@Override
	public TachoVehicle getByFile(String file) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM " + getEntityName() + " p");
		
		queryString.append(" WHERE p.file = :file");
		
		Query query = (Query) getEntityManager().createQuery(queryString.toString());
		
		query.setParameter("file", file);
			
		return (TachoVehicle) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TachoVehicle> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception {
		StringBuffer queryString = new StringBuffer("SELECT p FROM TachoVehicle p");
		queryString.append(" WHERE 1=1");
		
		if (vehicles.size() > 0)
			queryString.append(" AND p.vehicle IN :vehicles");
		if (startActivityDate != null)
			queryString.append(" AND p.dailyDate >= :startActivityDate");
		if (endActivityDate != null)
			queryString.append(" AND p.dailyDate <= :endActivityDate");
		
		Query query = getEntityManager().createQuery(queryString.toString());
		query.setMaxResults(1);
		
		if (vehicles.size() > 0)
			query.setParameter("vehicles", vehicles);
		if (startActivityDate != null)
			query.setParameter("startActivityDate", startActivityDate);		
		if (endActivityDate != null)
			query.setParameter("endActivityDate", endActivityDate);
		
		queryString.append(" ORDER BY p.creationDate DESC");
		
		return query.getResultList();
	}
}
