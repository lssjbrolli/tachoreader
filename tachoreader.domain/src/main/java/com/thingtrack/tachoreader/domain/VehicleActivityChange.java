package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="VEHICLE_ACTIVITY_CHANGE")
public class VehicleActivityChange extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;	
	
	@Column(name="RECORD_DATE", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;
	
	@ManyToOne
	@JoinColumn(name="VEHICLE_ACTIVITY_DAILY_ID", nullable=false)
	private VehicleActivityDaily vehicleActivityDaily;
		
	public final static String AVAILABLE_HEX_COLOR = "#000000";
	public final static String DRIVING_HEX_COLOR = "#00CC00";
	public final static String WORKING_HEX_COLOR = "#FFFF00";
	public final static String SHORT_BREAK_HEX_COLOR = "#FF9933";
	public final static String BREAK_REST_HEX_COLOR = "#FF0000";
	public final static String UNKNOWN_HEX_COLOR = "#B266FF";
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	public VehicleActivityDaily getVehicleActivityDaily() {
		return vehicleActivityDaily;
	}

	public void setVehicleActivityDaily(VehicleActivityDaily vehicleActivityDaily) {
		this.vehicleActivityDaily = vehicleActivityDaily;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((vehicleActivityDaily == null) ? 0 : vehicleActivityDaily
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((recordDate == null) ? 0 : recordDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof VehicleActivityChange))
			return false;
		VehicleActivityChange other = (VehicleActivityChange) obj;
		if (vehicleActivityDaily == null) {
			if (other.vehicleActivityDaily != null)
				return false;
		} else if (!vehicleActivityDaily.equals(other.vehicleActivityDaily))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recordDate == null) {
			if (other.recordDate != null)
				return false;
		} else if (!recordDate.equals(other.recordDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardActivityDailyChange [id=" + id
				+ ", recordDate=" + recordDate + ", vehicleActivityDaily="
				+ vehicleActivityDaily + "]";
	}
}
