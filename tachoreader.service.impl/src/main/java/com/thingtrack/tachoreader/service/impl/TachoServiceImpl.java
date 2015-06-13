package com.thingtrack.tachoreader.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tacografo.file.FileBlockTGD;
import org.tacografo.file.cardblockdriver.CardIdentification;
import org.tacografo.file.cardblockdriver.CardVehiclesUsed;
import org.tacografo.file.cardblockdriver.subblock.CardVehicleRecord;
import org.tacografo.file.error.ErrorFile;
import org.tacografo.file.exception.ExceptionDriverNotExist;
import org.tacografo.file.exception.ExceptionFileExist;

import com.thingtrack.tachoreader.dao.api.DriverDao;
import com.thingtrack.tachoreader.dao.api.TachoDao;
import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.TachoService;

public class TachoServiceImpl implements TachoService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private DriverDao driverDao;

	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private TachoDao tachoDao;

	@Override
	public List<Tacho> getAll(Organization organization) throws Exception {
		return this.tachoDao.getAll(organization);
	}
	
	@Override
	public Tacho getByFile(String file) throws Exception {
		return this.tachoDao.getByFile(file);
	}
	
	@Override
	public List<Tacho> getAll(int pageNumber, int pageSize, User user,									  
			  Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			  Date creationDateFrom, Date creationDateTo) throws Exception {
		return this.tachoDao.getAll(pageNumber, pageSize, user, 
									 id, cardNumber, driverName, vehicleRegistrationNumber, 
									 creationDateFrom, creationDateTo);
	}
	
	@Override
	public int getCount(int pageSize, User user,									  
			 Integer id, String cardNumber, String driverName, String vehicleRegistrationNumber, 
			 Date creationDateFrom, Date creationDateTo) throws Exception {
		return this.tachoDao.getCount(pageSize, user, id, 
									   cardNumber, driverName, vehicleRegistrationNumber,  
									   creationDateFrom, creationDateTo);
	}
			 
	@Override
	public Tacho save(Tacho tacho) throws Exception {
		return this.tachoDao.save(tacho);
	}

	@Override
	public void delete(Tacho tacho) throws Exception {
		this.tachoDao.delete(tacho);	
	}
	
	@Override
	public List<Tacho> setRegisterTacho(String code, String password, File tachoFile, String fileName, String tachoRepository) throws Exception {				
		List<Tacho> tachos = new ArrayList<Tacho>();		
		
		try {    	   							
			// STEP01: check agent data and get driver entity
			User tachoUser;
			try {
				tachoUser = userDao.getByUsername(code);
			}
			catch(Exception ex) {
				throw new Exception("The user is not registered", ex);
			}
			
			if (!tachoUser.isActive())
				throw new Exception("The user is not active");			
			
			if (!tachoUser.getPassword().equals(password))
				throw new Exception("The user password is not correct");
			
			// STEP02: parse download file and get data	blocks		
			FileBlockTGD fileBlockTGD = new FileBlockTGD(tachoFile.getPath());
			
			CardIdentification cardBlockIdentification = fileBlockTGD.getIdentification();
			CardVehiclesUsed cardVehiclesUsed = fileBlockTGD.getVehicles_used();			
			
			String tachoHolderName = cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderName().getHolderFirstNames() + " " + cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderName().getHolderSurname();
			String tachoDriverIdentification = cardBlockIdentification.getCardNumber().toString();
			Date tachoCardExpiryDate = cardBlockIdentification.getCardExpiryDate();
			SimpleDateFormat formatterDriverBithDate = new SimpleDateFormat("yyyy-MM-dd");
			Date tachoDriverBithDate = formatterDriverBithDate.parse(cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderBirthDate());
			
			CardVehicleRecord cardVehicleRecord = cardVehiclesUsed.getCardVehicleRecords().get(cardVehiclesUsed.getVehiclePointerNewestRecord()-1);
			
			// STEP03: get driver from user or from card (the tachos could be register from drivers or from administrators)
			Driver tachoDriver = null;
			if (tachoUser.getAgent() instanceof Driver) {
				tachoDriver = (Driver) tachoUser.getAgent();
				
				// the tacho identification card is not the same that the driver one
				if (!tachoDriver.getCardNumber().equals(tachoDriverIdentification)) 
					throw new Exception("The identification card " + tachoDriverIdentification + " from your tacho is not the same as yours " + tachoDriver.getCardNumber() + " identification card registered. The Tacho is from " + tachoHolderName);
			}
			else {
				try {
					tachoDriver = driverDao.getByCardNumber(tachoDriverIdentification);
				}
				catch(Exception ex) {
					throw new ExceptionDriverNotExist(tachoHolderName, tachoDriverIdentification, tachoCardExpiryDate, tachoDriverBithDate);
				}
			}
						
			// check the driver expiry card is out of date
			if (!tachoDriver.getCardExpiryDate().after(new Date())) {	
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				throw new Exception("The expiry " + formatter.format(tachoDriver.getCardExpiryDate()) + " of your identification card " + tachoDriverIdentification + " is out of date");
			}
			
			try
			{
				tachoDao.getByFile(fileName);
				
				throw new ExceptionFileExist(fileName);
			} catch (ExceptionFileExist ex) {	
				throw ex;
			}
			catch (Exception ex) {								
			}
			
			// STEP04: copy tacho file to the tachos organization repository			
			FileUtils.copyFile(tachoFile, FileUtils.getFile(tachoRepository + "/" + tachoUser.getOrganizationDefault().getId(), fileName));			
			
			// STEP05: register the tacho file	
			try {						
				Tacho tacho = new Tacho();
				
				tacho.setDriver(tachoDriver);
				tacho.setVehicle(vehicleDao.getByRegistration(cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber())); // get the last vehicle used
				tacho.setFile(fileName);							
				tacho.setCreatedBy(tachoUser);	
				tacho.setCreationDate(new Date());
				
				tachos.add(save(tacho)); //TODO: how are registered the vehicles used in the driver card??
			}
			catch (Exception ex) {
				throw new Exception("The vehicle " + cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber() + " is not registered");				
			} 												
		} catch (ErrorFile e) {
			throw new Exception("Error uploading the tacho", e);
		} catch (IOException e) {			
			throw new Exception("Error copying the tacho", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Exception("Tacho is corrupted", e);
		}
		
		return tachos;
	}
	
	@Override
	public FileInputStream setZipTachos(User user, List<Tacho> tachos, String tachosRepository) throws Exception {
		// check if exist the temp zip directory for this user
		File tachosRepositoryFile = new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId());
		
		if (!tachosRepositoryFile.exists() && !tachosRepositoryFile.isDirectory())
			FileUtils.forceMkdir(new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId()));
		
		FileOutputStream fos = new FileOutputStream(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId() + "/Tachos.zip");
    	ZipOutputStream zos = new ZipOutputStream(fos);
    	
    	boolean isSelected = false;
    	for (Tacho tacho : tachos) {
			if (tacho.isSelected()) {
				byte[] buffer = new byte[1024];
				
            	ZipEntry ze= new ZipEntry(tacho.getFile());
            	zos.putNextEntry(ze);
            	
            	FileInputStream in = new FileInputStream(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/" + tacho.getFile());
            	 
        		int len;
        		while ((len = in.read(buffer)) > 0) {
        			zos.write(buffer, 0, len);
        		}
        		
        		in.close();
        		
				isSelected = true;
			}
		}
	            	     
    	zos.closeEntry();	            	 
		zos.close();
			     
		if (!isSelected)
			return null;
		
		return new FileInputStream(new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId() + "/Tachos.zip"));				
	}
}
