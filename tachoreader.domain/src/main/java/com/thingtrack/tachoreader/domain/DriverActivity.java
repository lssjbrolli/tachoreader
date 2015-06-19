package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="DRIVER_ACTIVITY")
public class DriverActivity extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
		
	@ManyToOne
	@JoinColumn(name="DRIVER_ID", nullable=false)	
	private Driver driver;

	@ManyToOne
	@JoinColumn(name="VEHICLE_ID", nullable=false)	
	private Vehicle vehicle;

	@ManyToOne
	@JoinColumn(name="TACHO_ID", nullable=false)	
	private Tacho tacho;
	
	@Column(name="TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private TYPE type;
	
	@Column(name="DISTANCE", nullable=false)
	private float distance;
	
	@Column(name="RECORD_DATE_FROM", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDateFrom;
	
	@Column(name="RECORD_DATE_TO", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDateTo;
	
	public enum TYPE {
		AVAILABLE,
		DRIVING,
	    WORKING,
	    SHORT_BREAK,
	    BREAK_REST,
	    UNKNOWN
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Tacho getTacho() {
		return tacho;
	}

	public void setTacho(Tacho tacho) {
		this.tacho = tacho;
	}	
	
	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public Date getRecordDateFrom() {
		return recordDateFrom;
	}

	public void setRecordDateFrom(Date recordDateFrom) {
		this.recordDateFrom = recordDateFrom;
	}

	public Date getRecordDateTo() {
		return recordDateTo;
	}

	public void setRecordDateTo(Date recordDateTo) {
		this.recordDateTo = recordDateTo;
	}

	@Override
	public String toString() {
		return "DriverActivity [id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DriverActivity))
			return false;
		DriverActivity other = (DriverActivity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	 
}
