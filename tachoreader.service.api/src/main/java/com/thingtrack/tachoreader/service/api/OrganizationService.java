package com.thingtrack.tachoreader.service.api;

import java.util.List;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface OrganizationService {
	public List<Organization> getAll(User user) throws Exception;
	public Organization getByCode(String code) throws Exception;
	public Organization save(Organization organization) throws Exception;
	public void delete(Organization organization) throws Exception;
	public Organization createNewEntity(User user);
}
