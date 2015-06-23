package com.thingtrack.tachoreader.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tacografo.file.FileBlockTGD;
import org.tacografo.file.cardblockdriver.CardIdentification;
import org.tacografo.file.cardblockdriver.CardVehiclesUsed;
import org.tacografo.file.cardblockdriver.subblock.ActivityChangeInfo;
import org.tacografo.file.cardblockdriver.subblock.CardActivityDailyRecord;
import org.tacografo.file.cardblockdriver.subblock.CardVehicleRecord;
import org.tacografo.file.error.ErrorFile;
import org.tacografo.file.exception.ExceptionDriverNotExist;
import org.tacografo.file.exception.ExceptionFileExist;
import org.tacografo.file.exception.ExceptionVehicleNotExist;

import com.thingtrack.tachoreader.dao.api.CardActivityDailyDao;
import com.thingtrack.tachoreader.dao.api.DriverDao;
import com.thingtrack.tachoreader.dao.api.TachoDao;
import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.CardActivityDailyService;
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

	@Autowired
	private CardActivityDailyDao driverActivityDao;
	
	@Autowired
	private CardActivityDailyService cardActivityDailyService;
	
	final static Logger logger = Logger.getLogger("TachoServiceImpl");
	
	final static String ZERO = "0";
	final static String ONE = "1";
	
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
	
	private List<CardActivityDaily> registerDriverActivity(User user, Driver driver, Vehicle vehicle, Tacho tacho, ArrayList<CardActivityDailyRecord> cardActivityDailyRecords) {
		List<CardActivityDaily> cardActivityDailys = new ArrayList<CardActivityDaily>();
		Calendar cal = Calendar.getInstance();
		
		for (CardActivityDailyRecord cardActivityDailyRecord : cardActivityDailyRecords) {
			CardActivityDaily cardActivityDaily = cardActivityDailyService.createNewEntity(user);
			
			cardActivityDaily.setDriver(driver);
			cardActivityDaily.setVehicle(vehicle);
			cardActivityDaily.setTacho(tacho);
			cardActivityDaily.setDistance(cardActivityDailyRecord.getActivityDayDistance());			
			cardActivityDaily.setDailyDate(cardActivityDailyRecord.getActivityRecordDate());
			
			for (ActivityChangeInfo activityChangeInfo : cardActivityDailyRecord.getActivityChangeInfo()) {
				CardActivityDailyChange cardActivityDailyChange = new CardActivityDailyChange();
				
				if (activityChangeInfo.getS().equals("conductor"))
					cardActivityDailyChange.setSlot(CardActivityDailyChange.SLOT.FIRST_DRIVER);
				else if (activityChangeInfo.getS().equals("segundo conductor"))
					cardActivityDailyChange.setSlot(CardActivityDailyChange.SLOT.SECOND_DRIVER);
				
				if (activityChangeInfo.getC().equals("solitario"))
					cardActivityDailyChange.setDrivingSystem(CardActivityDailyChange.DRIVING_SYSTEM.SOLO);
				else if (activityChangeInfo.getC().equals("en equipo"))
					cardActivityDailyChange.setDrivingSystem(CardActivityDailyChange.DRIVING_SYSTEM.TEAM);
				else if (activityChangeInfo.getC().equals("indeterminado"))
					cardActivityDailyChange.setDrivingSystem(CardActivityDailyChange.DRIVING_SYSTEM.INDETERMINATE);
				else if (activityChangeInfo.getC().equals("entrada manual"))
					cardActivityDailyChange.setDrivingSystem(CardActivityDailyChange.DRIVING_SYSTEM.DETERMIDED);
				
				if (activityChangeInfo.getP().equals("insertada"))
					cardActivityDailyChange.setCardStatus(CardActivityDailyChange.CARD_STATUS.INSERTED);
				else if (activityChangeInfo.getP().equals("no insertada"))
					cardActivityDailyChange.setCardStatus(CardActivityDailyChange.CARD_STATUS.NOT_INSERTED);
				
				if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("PAUSA/DESCANSO"))
					cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.BREAK_REST);
				else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("DISPONIBILIDAD"))
					cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.AVAILABLE);
				else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("TRABAJO"))
					cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.WORKING);
				else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("CONDUCCIÓN"))
					cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.DRIVING);									
				else {
					if (activityChangeInfo.getP().equals("entrada manual"))
						cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.SHORT_BREAK);
					else
						cardActivityDailyChange.setType(CardActivityDailyChange.TYPE.UNKNOWN);
				}
				
				// set time value
				String timeTacho = activityChangeInfo.getT();
				String[] timeSplit = timeTacho.split(":");
				
				cal.setTime(cardActivityDailyRecord.getActivityRecordDate());
				cal.set(Calendar.HOUR_OF_DAY, 0);
			    cal.set(Calendar.MINUTE, 0);
			    cal.set(Calendar.SECOND, 0);
			    cal.set(Calendar.MILLISECOND, 0);
				cal.add(Calendar.HOUR, Integer.parseInt(timeSplit[0]));
				cal.add(Calendar.MINUTE, Integer.parseInt(timeSplit[1]));
				cardActivityDailyChange.setRecordDate(cal.getTime());
				
				cardActivityDaily.addCardActivityDailyChange(cardActivityDailyChange);
			}
			
			cardActivityDailys.add(cardActivityDaily);
		}
		
		return cardActivityDailys;
	}
	
	@Override
	public List<Tacho> setRegisterTacho(User user, String code, String password, File tachoFile, String fileName, String tachoRepository) throws Exception {				
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
					
					// check the organization of the tacho
					if (tachoDriver.getOrganization().getId() != user.getOrganizationDefault().getId())
						throw new Exception("The driver card " + tachoDriverIdentification + " from your tacho is not registered in your organization " + user.getOrganizationDefault().getName());
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
				throw new Exception("Error reading tacho file " + fileName, ex);
			}
			catch (Exception ex) {								
			}
			
			// get vehicle from tacho
			Vehicle tachoVehicle = null;
			try {
				tachoVehicle = vehicleDao.getByRegistration(cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber());
			} catch (Exception ex) {		
				throw new ExceptionVehicleNotExist(cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber());
			}
			
			// STEP04: copy tacho file to the tachos organization repository			
			FileUtils.copyFile(tachoFile, FileUtils.getFile(tachoRepository + "/" + tachoUser.getOrganizationDefault().getId(), fileName));			
			
			// STEP05: register the tacho file
			Tacho tacho = null;
			try {						
				tacho = new Tacho();				
				tacho.setDriver(tachoDriver);
				tacho.setVehicle(tachoVehicle); // get the last vehicle used
				tacho.setFile(fileName);							
				tacho.setCreatedBy(tachoUser);	
				tacho.setCreationDate(new Date());
				
				tachos.add(save(tacho)); //TODO: how are registered the vehicles used in the driver card??
			}
			catch (Exception ex) {
				throw new Exception("The vehicle " + cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber() + " is not registered", ex);					
			}
			
			//STEP06: register driver activity
			try {
				List<CardActivityDaily> cardActivityDailys = registerDriverActivity(user, tachoDriver, tachoVehicle, tacho, fileBlockTGD.getDriver_activity_data().getActivityDailyRecords());
				
				for (CardActivityDaily cardActivityDaily : cardActivityDailys)
					driverActivityDao.save(cardActivityDaily);								
				
			} catch (Exception ex) {
				throw new Exception("Error insert driver activity", ex);					
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
