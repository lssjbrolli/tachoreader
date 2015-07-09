package com.thingtrack.tachoreader.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.tacografo.file.FileBlockTGD;
import org.tacografo.file.error.ErrorFile;

import com.thingtrack.tachoreader.domain.Tacho.TYPE;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.TachoDriverService;
import com.thingtrack.tachoreader.service.api.TachoService;
import com.thingtrack.tachoreader.service.api.TachoVehicleService;

public class TachoServiceImpl implements TachoService {	
	@Autowired
	private TachoDriverService tachoDriverService;
	
	@Autowired
	private TachoVehicleService tachoVehicleService;
	
	@Override
	public Object setRegisterTacho(User user, String code, String password, File tachoFile, String fileName, String tachoRepository) throws Exception {		
		Object tacho = null;
		TYPE tachoType =  null;
		
		// check the type of tacho uploaded
		try {
			new FileBlockTGD(tachoFile.getPath());
			
			tachoType = TYPE.DRIVER;
		} catch (ErrorFile e) {
			throw new Exception("Error uploading the tacho", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Exception("Tacho is corrupted", e);
		} catch (Exception e) {
			tachoType = TYPE.VEHICLE;
		} 

		if (tachoType.equals(TYPE.DRIVER))
			tacho = tachoDriverService.setRegisterTacho(user, code, password, tachoFile, fileName, tachoRepository);		
		else if (tachoType.equals(TYPE.VEHICLE))
			tacho = tachoVehicleService.setRegisterTacho(user, code, password, tachoFile, fileName, tachoRepository);		
		else if (tachoType.equals(TYPE.COMPANY))
			throw new Exception("Is not Implemented!");		
		else if (tachoType.equals(TYPE.TEST_CENTER))
			throw new Exception("Is not Implemented!");		
		else
			throw new Exception("Is not Implemented!");		
		
		return tacho;
	}
}
