package com.thingtrack.tachoreader.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.thingtrack.tachoreader.dao.api.CardActivityDailyDao;
import com.thingtrack.tachoreader.dao.api.DriverDao;
import com.thingtrack.tachoreader.dao.api.TachoVehicleDao;
import com.thingtrack.tachoreader.dao.api.UserDao;
import com.thingtrack.tachoreader.dao.api.VehicleDao;
import com.thingtrack.tachoreader.domain.TachoVehicle;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.TachoVehicleService;

public class TachoVehicleServiceImpl implements TachoVehicleService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private DriverDao driverDao;

	@Autowired
	private VehicleDao vehicleDao;
	
	@Autowired
	private TachoVehicleDao tachoVehicleDao;

	@Autowired
	private CardActivityDailyDao cardActivityDailyDao;	
	
	final static Logger logger = Logger.getLogger("TachoVehicleServiceImpl");
	
	final static String ZERO = "0";
	final static String ONE = "1";
	
	@Override
	public TachoVehicle getByFile(String file) throws Exception {
		return this.tachoVehicleDao.getByFile(file);
	}
			 
	@Override
	public TachoVehicle save(TachoVehicle tacho) throws Exception {
		return this.tachoVehicleDao.save(tacho);
	}

	@Override
	public void delete(TachoVehicle tacho) throws Exception {
		this.tachoVehicleDao.delete(tacho);	
	}
	
	@Override
	public List<TachoVehicle> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception {
		// TODO Auto-generated method stub
		return this.tachoVehicleDao.getAll(vehicles, startActivityDate, endActivityDate);
	}
		
	@Override
	public TachoVehicle setRegisterTacho(User user, String code, String password, File tachoFile, String fileName, String tachoRepository) throws Exception {						
		throw new Exception("Is not Implemented!");
	}
	
	@Override
	public FileInputStream setZipTachos(User user, List<TachoVehicle> tachos, String tachosRepository) throws Exception {
		// check if exist the temp zip directory for this user
		File tachosRepositoryFile = new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId());
		
		if (!tachosRepositoryFile.exists() && !tachosRepositoryFile.isDirectory())
			FileUtils.forceMkdir(new File(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId()));
		
		FileOutputStream fos = new FileOutputStream(tachosRepository + "/" + user.getOrganizationDefault().getId() + "/tmp/" + user.getId() + "/Tachos.zip");
    	ZipOutputStream zos = new ZipOutputStream(fos);
    	
    	boolean isSelected = false;
    	for (TachoVehicle tacho : tachos) {
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