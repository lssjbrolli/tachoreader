package com.thingtrack.tachoreader.service.api;

import java.io.File;

import com.thingtrack.tachoreader.domain.User;

public interface TachoService {
	public Object setRegisterTacho(User user, String code, String password, File file, String fileName, String tachoRepository) throws Exception;
}
