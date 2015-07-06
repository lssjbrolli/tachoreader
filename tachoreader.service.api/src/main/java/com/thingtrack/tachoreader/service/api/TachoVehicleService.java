package com.thingtrack.tachoreader.service.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.TachoVehicle;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.domain.Vehicle;

public interface TachoVehicleService {
    public List<TachoVehicle> getAll(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) throws Exception;
    public TachoVehicle getByFile(String file) throws Exception;
	public TachoVehicle save(TachoVehicle tacho) throws Exception;
	public void delete(TachoVehicle tacho) throws Exception;
	public TachoVehicle setRegisterTacho(User user, String code, String password, File file, String fileName, String tachoRepository) throws Exception;
	public FileInputStream setZipTachos(User user, List<TachoVehicle> tachos, String tachosRepository) throws Exception;
}
