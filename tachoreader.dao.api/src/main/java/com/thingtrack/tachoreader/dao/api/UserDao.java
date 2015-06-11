package com.thingtrack.tachoreader.dao.api;

import com.thingtrack.konekti.dao.template.Dao;
import com.thingtrack.tachoreader.domain.User;

public interface UserDao extends Dao<User, Integer> {
	public User getByUsername(String username) throws Exception;
}
