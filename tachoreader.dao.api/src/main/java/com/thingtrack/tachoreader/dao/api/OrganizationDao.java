package com.thingtrack.tachoreader.dao.api;

import java.util.List;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.konekti.dao.template.Dao;

public interface OrganizationDao extends Dao<Organization, Integer> {
	public List<Organization> getAll(User user) throws Exception;
	public Organization getByCode(String code) throws Exception;
}
