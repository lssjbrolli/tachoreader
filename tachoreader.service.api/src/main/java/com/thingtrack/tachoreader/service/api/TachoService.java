package com.thingtrack.tachoreader.service.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;

public interface TachoService {
    public List<Tacho> getAll(Organization organization) throws Exception;
    public Tacho getByFile(String file) throws Exception;
    public List<Tacho> getAll(int pageNumber, int pageSize, User user,									  
			  Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			  Date creationDateFrom, Date creationDateTo) throws Exception;
    public int getCount(int pageSize, User user,									  
			 Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			 Date creationDateFrom, Date creationDateTo) throws Exception;
	public Tacho save(Tacho tacho) throws Exception;
	public void delete(Tacho tacho) throws Exception;
	public List<Tacho> setRegisterTacho(String code, String password, File file, String fileName, String tachoRepository) throws Exception;
	public FileInputStream setZipTachos(User user, List<Tacho> tachos, String tachosRepository) throws Exception;
}
