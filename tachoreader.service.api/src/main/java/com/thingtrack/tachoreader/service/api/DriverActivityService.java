package com.thingtrack.tachoreader.service.api;

import java.util.List;

import com.thingtrack.tachoreader.domain.DriverActivity;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;

public interface DriverActivityService {
    public List<DriverActivity> getAll(Organization organization) throws Exception;
	public DriverActivity createNewEntity(User user);
}
