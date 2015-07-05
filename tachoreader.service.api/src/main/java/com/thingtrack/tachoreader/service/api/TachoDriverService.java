package com.thingtrack.tachoreader.service.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.TachoDriver;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoDriverService {
    public List<TachoDriver> getAll(Organization organization) throws Exception;
    public List<TachoDriver> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
    public TachoDriver getByFile(String file) throws Exception;
	public TachoDriver save(TachoDriver tacho) throws Exception;
	public void delete(TachoDriver tacho) throws Exception;
	public TachoDriver setRegisterTacho(User user, String code, String password, File file, String fileName, String tachoRepository) throws Exception;
	public FileInputStream setZipTachos(User user, List<TachoDriver> tachos, String tachosRepository) throws Exception;
}
