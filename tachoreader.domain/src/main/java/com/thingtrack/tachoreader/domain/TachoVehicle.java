package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TACHO_VEHICLE")
public class TachoVehicle extends Tacho implements Serializable {
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="TACHO_VEHICLE_ACTIVITY_DAILY",
			   joinColumns=@JoinColumn(name="TACHO_ID"),
			   inverseJoinColumns=@JoinColumn(name="VEHICLE_ACTIVITY_DAILY_ID"))
	@OrderBy("dailyDate ASC")
	private List<VehicleActivityDaily> vehicleActivitiesDaily = new ArrayList<VehicleActivityDaily>();
		
	public List<VehicleActivityDaily> getVehicleActivitiesDaily() {
		return Collections.unmodifiableList(vehicleActivitiesDaily);
	}

	public void addVehicleActivityDaily(VehicleActivityDaily vehicleActivityDaily) {
		if (vehicleActivitiesDaily.contains(vehicleActivityDaily))
			return;		
		
		vehicleActivitiesDaily.add(vehicleActivityDaily);
	}	
	
	public Date getStartActivity() {
		if (vehicleActivitiesDaily.size() > 0)
			return vehicleActivitiesDaily.get(0).getDailyDate();
		
		return null;
	}
	
	public Date getEndActivity() {
		if (vehicleActivitiesDaily.size() > 1)
			return vehicleActivitiesDaily.get(vehicleActivitiesDaily.size() - 1).getDailyDate();
		
		return null;
	}
}
