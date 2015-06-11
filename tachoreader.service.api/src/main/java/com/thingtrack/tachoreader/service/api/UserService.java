package com.thingtrack.tachoreader.service.api;

import com.thingtrack.tachoreader.domain.Agent;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface UserService {
	public User getByUsername(String username) throws Exception;
	public User save(User user) throws Exception;
	public void delete(User user) throws Exception;
	public User createNewEntity(Organization organization, Agent agent, String language);
}
