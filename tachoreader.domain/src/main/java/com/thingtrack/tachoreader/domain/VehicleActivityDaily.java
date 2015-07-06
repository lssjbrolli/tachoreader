package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="VEHICLE_ACTIVITY_DAILY")
public class VehicleActivityDaily extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="VEHICLE_ACTIVITY_DAILY_DRIVER",
			   joinColumns=@JoinColumn(name="VEHICLE_ACTIVITY_DAILY_ID"),
			   inverseJoinColumns=@JoinColumn(name="DRIVER_ID"))
	private List<Driver> drivers = new ArrayList<Driver>();
		
	@Column(name="ODOMETER", nullable=false)
	private float odometer;

	@Column(name="DAILY_DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dailyDate;
	
	@OneToMany(mappedBy="vehicleActivityDaily", cascade={CascadeType.ALL})	
	@OrderBy("recordDate ASC")
	private List<VehicleActivityChange> vehicleActivitiesDailyChange = new ArrayList<VehicleActivityChange>();
	
	@ManyToMany(mappedBy="vehicleActivitiesDaily")
    private List<TachoVehicle> tachosVehicle = new ArrayList<TachoVehicle>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getOdometer() {
		return odometer;
	}

	public void setOdometer(float odometer) {
		this.odometer = odometer;
	}

	public Date getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(Date dailyDate) {
		this.dailyDate = dailyDate;
	}
	
	public List<Driver> getDrivers() {
		return Collections.unmodifiableList(drivers);
	}

	public void addVehicle(Driver driver) {
		if (drivers.contains(driver))
			return;
		
		drivers.add(driver);		
	}
	
	public List<VehicleActivityChange> getVehicleActivityDailyChanges() {
		return Collections.unmodifiableList(vehicleActivitiesDailyChange);
	}
	
	public void addVehicleActivityDailyChange(VehicleActivityChange vehicleActivityChange) {
		if (vehicleActivitiesDailyChange.contains(vehicleActivityChange))
				return;
		
		vehicleActivityChange.setVehicleActivityDaily(this);		
		vehicleActivitiesDailyChange.add(vehicleActivityChange);		
	}
	
	public List<TachoVehicle> getTachosDriver() {
		return Collections.unmodifiableList(tachosVehicle);
	}
	
	public void addTachoVehicle(TachoVehicle tachoVehicle) {
		if (tachosVehicle.contains(tachoVehicle))
			return;
		
		tachosVehicle.add(tachoVehicle);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dailyDate == null) ? 0 : dailyDate.hashCode());
		result = prime * result + Float.floatToIntBits(odometer);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VehicleActivityDaily))
			return false;
		VehicleActivityDaily other = (VehicleActivityDaily) obj;
		if (dailyDate == null) {
			if (other.dailyDate != null)
				return false;
		} else if (!dailyDate.equals(other.dailyDate))
			return false;
		if (Float.floatToIntBits(odometer) != Float
				.floatToIntBits(other.odometer))
			return false;		
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardActivityDaily [id=" + id
				+ ", odometer=" + odometer + ", dailyDate=" + dailyDate + "]";
	}
}
