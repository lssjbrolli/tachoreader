package com.thingtrack.tachoreader.dao.impl;

import com.thingtrack.konekti.dao.template.JpaDao;
import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.domain.User;

public class UserDaoImpl extends JpaDao<User, Integer> implements UserDao {
	@Override
	public User getByUsername(String username) throws Exception {
		User user = (User)getEntityManager()
				.createQuery("SELECT p FROM " + getEntityName() + " p WHERE p.username = :username")
				.setParameter("username", username).getSingleResult();

				return user;
	}	
}
