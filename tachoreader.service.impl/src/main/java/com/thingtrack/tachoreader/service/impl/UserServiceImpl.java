package com.thingtrack.tachoreader.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.domain.Agent;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.UserService;

public class UserServiceImpl implements UserService {	
	@Autowired
	private UserDao userDao;

	@Override
	public User getByUsername(String username) throws Exception {
		return this.userDao.getByUsername(username);
	}
	
	@Override
	public User save(User user) throws Exception {
		return this.userDao.save(user);
	}

	@Override
	public void delete(User user) throws Exception {
		this.userDao.delete(user);	
	}
	
	@Override
	public User createNewEntity(Organization organization, Agent agent, String language) {
		User user = new User();	
		
		user.setAgent(agent);
		user.setLanguage(language);
		user.setActive(true);
		
		return user;
	}
}
