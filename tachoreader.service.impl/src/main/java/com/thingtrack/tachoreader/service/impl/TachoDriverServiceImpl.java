package com.thingtrack.tachoreader.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tacografo.file.FileBlockTGD;
import org.tacografo.file.cardblockdriver.CardDriverActivity;
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
import com.thingtrack.tachoreader.dao.api.TachoDriverDao;
import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.CardActivityChange;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.CardActivityDaily;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.TachoDriver;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.TachoDriverService;

public class TachoDriverServiceImpl implements TachoDriverService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private DriverDao driverDao;

	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private TachoDriverDao tachoDriverDao;

	@Autowired
	private CardActivityDailyDao cardActivityDailyDao;	
	
	final static Logger logger = Logger.getLogger("TachoServiceImpl");
	
	final static String ZERO = "0";
	final static String ONE = "1";
	
	@Override
	public List<TachoDriver> getAll(Organization organization) throws Exception {
		return this.tachoDriverDao.getAll(organization);
	}
	
	@Override
	public TachoDriver getByFile(String file) throws Exception {
		return this.tachoDriverDao.getByFile(file);
	}
			 
	@Override
	public TachoDriver save(TachoDriver tacho) throws Exception {
		return this.tachoDriverDao.save(tacho);
	}

	@Override
	public void delete(TachoDriver tacho) throws Exception {
		this.tachoDriverDao.delete(tacho);	
	}
	
	@Override
	public List<TachoDriver> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception {
		return this.tachoDriverDao.getAll(vehicles, startActivityDate, endActivityDate);
	}
	
	private CardActivityDaily registerDriverActivity(User user, Driver driver, TachoDriver tacho, CardActivityDailyRecord cardActivityDailyRecord, CardVehiclesUsed cardVehiclesUsed) throws ExceptionVehicleNotExist {
		Calendar cal = Calendar.getInstance();
					
		CardActivityDaily cardActivityDaily = new CardActivityDaily();
		cardActivityDaily.setCreatedBy(user);
		cardActivityDaily.setCreationDate(new Date());
		cardActivityDaily.setDriver(driver);
		cardActivityDaily.addTachos(tacho);
		cardActivityDaily.setDistance(cardActivityDailyRecord.getActivityDayDistance());			
		cardActivityDaily.setDailyDate(cardActivityDailyRecord.getActivityRecordDate());
		
		// set vehicles used in this daily activity		
		for (CardVehicleRecord cardVehicleRecord : cardVehiclesUsed.getCardVehicleRecords()) {			
			if (DateUtils.isSameDay(cardVehicleRecord.getVehicleFirstUse(), cardActivityDailyRecord.getActivityRecordDate())) {
				try {
					cardActivityDaily.addVehicle(vehicleDao.getByRegistration(cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber()));
				} catch (Exception ex) {		
					throw new ExceptionVehicleNotExist(cardVehicleRecord.getVehicleRegistration().getVehicleRegistrationNumber());
				}
			}				
		}
		
		// generate daily change activity
		for (int i = 0; i < cardActivityDailyRecord.getActivityChangeInfo().size(); i++) {
			ActivityChangeInfo activityChangeInfo = cardActivityDailyRecord.getActivityChangeInfo().get(i);
			
			CardActivityChange cardActivityDailyChange = new CardActivityChange();
			cardActivityDailyChange.setCreatedBy(user);
			
			if (activityChangeInfo.getS().equals("conductor"))
				cardActivityDailyChange.setSlot(CardActivityChange.SLOT.FIRST_DRIVER);
			else if (activityChangeInfo.getS().equals("segundo conductor"))
				cardActivityDailyChange.setSlot(CardActivityChange.SLOT.SECOND_DRIVER);
			
			if (activityChangeInfo.getC().equals("solitario"))
				cardActivityDailyChange.setDrivingSystem(CardActivityChange.DRIVING_SYSTEM.SOLO);
			else if (activityChangeInfo.getC().equals("en equipo"))
				cardActivityDailyChange.setDrivingSystem(CardActivityChange.DRIVING_SYSTEM.TEAM);
			else if (activityChangeInfo.getC().equals("indeterminado"))
				cardActivityDailyChange.setDrivingSystem(CardActivityChange.DRIVING_SYSTEM.INDETERMINATE);
			else if (activityChangeInfo.getC().equals("entrada manual"))
				cardActivityDailyChange.setDrivingSystem(CardActivityChange.DRIVING_SYSTEM.DETERMIDED);
			
			if (activityChangeInfo.getP().equals("insertada"))
				cardActivityDailyChange.setCardStatus(CardActivityChange.CARD_STATUS.INSERTED);
			else if (activityChangeInfo.getP().equals("no insertada"))
				cardActivityDailyChange.setCardStatus(CardActivityChange.CARD_STATUS.NOT_INSERTED);
			
			if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("PAUSA/DESCANSO")) {
				if (i < cardActivityDailyRecord.getActivityChangeInfo().size()-1) {								
					String[] startTime = cardActivityDailyRecord.getActivityChangeInfo().get(i).getT().split(":");
					String[] endTime = cardActivityDailyRecord.getActivityChangeInfo().get(i+1).getT().split(":"); 

					int interval = (Integer.parseInt(endTime[0]) - Integer.parseInt(startTime[0])) * 60 + Integer.parseInt(endTime[1]) - Integer.parseInt(startTime[1]);
					
					if (!activityChangeInfo.getC().equals("indeterminado")) {
						if (interval < 15)
							cardActivityDailyChange.setType(CardActivityChange.TYPE.SHORT_BREAK);
						else
							cardActivityDailyChange.setType(CardActivityChange.TYPE.BREAK_REST);
					}
					else
						cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);
				}
				else {
					if (!activityChangeInfo.getC().equals("indeterminado"))
						cardActivityDailyChange.setType(CardActivityChange.TYPE.BREAK_REST);
					else
						cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);
				}
			}
			else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("DISPONIBILIDAD")) {
				if (!activityChangeInfo.getC().equals("indeterminado"))
					cardActivityDailyChange.setType(CardActivityChange.TYPE.AVAILABLE);
				else
					cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);
			}
			else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("TRABAJO")) {
				if (!activityChangeInfo.getC().equals("indeterminado"))
					cardActivityDailyChange.setType(CardActivityChange.TYPE.WORKING);
				else
					cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);
			}
			else if (activityChangeInfo.getP().equals("insertada") && activityChangeInfo.getAa().equals("CONDUCCIÓN")) {
				if (!activityChangeInfo.getC().equals("indeterminado"))
					cardActivityDailyChange.setType(CardActivityChange.TYPE.DRIVING);
				else
					cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);
			}
			else
				cardActivityDailyChange.setType(CardActivityChange.TYPE.UNKNOWN);				
			
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
		
		return cardActivityDaily;
	}
		
	@Override
	public TachoDriver setRegisterTacho(User user, String code, String password, File tachoFile, String fileName, String tachoRepository) throws Exception {				
		TachoDriver tachoDriver = null;		
		
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
			CardDriverActivity cardDriverActivity = fileBlockTGD.getDriver_activity_data();
			CardVehiclesUsed cardVehiclesUsed = fileBlockTGD.getVehicles_used();
			
			// parse driver data
			String tachoHolderName = cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderName().getHolderFirstNames() + " " + cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderName().getHolderSurname();
			String tachoDriverIdentification = cardBlockIdentification.getCardNumber().toString();
			Date tachoCardExpiryDate = cardBlockIdentification.getCardExpiryDate();
			SimpleDateFormat formatterDriverBithDate = new SimpleDateFormat("yyyy-MM-dd");
			Date tachoDriverBithDate = formatterDriverBithDate.parse(cardBlockIdentification.getDriverCardHolderIdentification().getCardHolderBirthDate());
						
			// STEP03: get driver from user or from card (the tachos could be register from drivers or from administrators)
			Driver driver = null;
			if (tachoUser.getAgent() instanceof Driver) {
				driver = (Driver) tachoUser.getAgent();
								
				// the tacho identification card is not the same that the driver one
				if (!driver.getCardNumber().equals(tachoDriverIdentification)) 
					throw new Exception("The identification card " + tachoDriverIdentification + " from your tacho is not the same as yours " + driver.getCardNumber() + " identification card registered. The Tacho is from " + tachoHolderName);
			}
			else {
				try {
					driver = driverDao.getByCardNumber(tachoDriverIdentification);
					
					// check the organization of the tacho
					if (driver.getOrganization().getId() != user.getOrganizationDefault().getId())
						throw new Exception("The driver card " + tachoDriverIdentification + " from your tacho is not registered in your organization " + user.getOrganizationDefault().getName());
				}
				catch(Exception ex) {
					throw new ExceptionDriverNotExist(tachoHolderName, tachoDriverIdentification, tachoCardExpiryDate, tachoDriverBithDate);
				}
			}
						
			// check the driver expiry card is out of date
			if (!driver.getCardExpiryDate().after(new Date())) {	
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				throw new Exception("The expiry " + formatter.format(driver.getCardExpiryDate()) + " of your identification card " + tachoDriverIdentification + " is out of date");
			}
							
			try
			{
				tachoDriverDao.getByFile(fileName);
				
				throw new ExceptionFileExist(fileName);
			} catch (ExceptionFileExist ex) {	
				throw new Exception("The tacho file " + fileName + " already exist");				
			} catch (Exception ex) {								
				
			}
							
			// STEP04: copy tacho file to the tachos organization repository			
			FileUtils.copyFile(tachoFile, FileUtils.getFile(tachoRepository + "/" + tachoUser.getOrganizationDefault().getId(), fileName));			
			
			// STEP05: register the tacho file
			try {						
				tachoDriver = new TachoDriver();				
				tachoDriver.setFile(fileName);
				tachoDriver.setCreatedBy(tachoUser);	
				tachoDriver.setCreationDate(new Date());
				
				// configure activity daily for this tacho
				for (CardActivityDailyRecord cardActivityDailyRecord : cardDriverActivity.getActivityDailyRecords()) {
					try {
						tachoDriver.addCardActivityDaily(cardActivityDailyDao.getCardActivityDailyByDriver(driver, cardActivityDailyRecord.getActivityRecordDate()));
					}
					catch(Exception ex) {		
						tachoDriver.addCardActivityDaily(registerDriverActivity(user, driver, tachoDriver, cardActivityDailyRecord, cardVehiclesUsed));						
					}
				}
				
				//tachos.add(save(tachoDriver));
			}
			catch (Exception ex) {
				throw new Exception("Error registering Tacho", ex);					
			}		
		} catch (ErrorFile e) {
			throw new Exception("Error uploading the tacho", e);
		} catch (IOException e) {			
			throw new Exception("Error copying the tacho", e);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new Exception("Tacho is corrupted", e);
		}
		
		return save(tachoDriver);
	}
	
	@Override
	public FileInputStream setZipTachos(User user, List<TachoDriver> tachos, String tachosRepository) throws Exception {
		// check if exist the temp zip directory for this user
		File tachosRepositoryFile = new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId());
		
		if (!tachosRepositoryFile.exists() && !tachosRepositoryFile.isDirectory())
			FileUtils.forceMkdir(new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId()));
		
		FileOutputStream fos = new FileOutputStream(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId() + "/Tachos.zip");
    	ZipOutputStream zos = new ZipOutputStream(fos);
    	
    	boolean isSelected = false;
    	for (TachoDriver tacho : tachos) {
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