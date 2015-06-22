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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="CARD_ACTIVITY_DAILY")
public class CardActivityDaily extends Audit implements Serializable {

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
	
	@Column(name="DISTANCE", nullable=false)
	private float distance;

	@Column(name="DAILY_DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dailyDate;
	
	@OneToMany(mappedBy="cardActivityDaily", cascade={CascadeType.ALL})	
	@OrderBy("recordDate ASC")
	private List<CardActivityDailyChange> cardActivityDailyChanges = new ArrayList<CardActivityDailyChange>();
	
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

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public List<CardActivityDailyChange> getCardActivityDailyChanges() {
		return Collections.unmodifiableList(cardActivityDailyChanges);
	}
	
	public void addCardActivityDailyChange(CardActivityDailyChange cardActivityDailyChange) {
		if (!cardActivityDailyChanges.contains(cardActivityDailyChange)) {
			cardActivityDailyChange.setCardActivityDaily(this);
			
			cardActivityDailyChanges.add(cardActivityDailyChange);
		}
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
		if (!(obj instanceof CardActivityDaily))
			return false;
		CardActivityDaily other = (CardActivityDaily) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	} 
}
