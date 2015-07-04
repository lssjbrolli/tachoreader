package com.thingtrack.tachoreader.service.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoService {
    public List<Tacho> getAll(Organization organization) throws Exception;
    public List<Tacho> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
    public Tacho getByFile(String file) throws Exception;
	public Tacho save(Tacho tacho) throws Exception;
	public void delete(Tacho tacho) throws Exception;
	public List<Tacho> setRegisterTacho(User user, String code, String password, File file, String fileName, String tachoRepository) throws Exception;
	public FileInputStream setZipTachos(User user, List<Tacho> tachos, String tachosRepository) throws Exception;
}
