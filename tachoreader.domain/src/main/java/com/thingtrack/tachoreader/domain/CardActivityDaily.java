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
@Table(name="CARD_ACTIVITY_DAILY")
public class CardActivityDaily extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="CARD_ACTIVITY_DAILY_VEHICLE",
			   joinColumns=@JoinColumn(name="CARD_ACTIVITY_DAILY_ID"),
			   inverseJoinColumns=@JoinColumn(name="VEHICLE_ID"))
	private List<Vehicle> vehicles = new ArrayList<Vehicle>();
	
	@Column(name="DISTANCE", nullable=false)
	private float distance;

	@Column(name="DAILY_DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dailyDate;
	
	@OneToMany(mappedBy="cardActivityDaily", cascade={CascadeType.ALL})	
	@OrderBy("recordDate ASC")
	private List<CardActivityChange> cardActivityDailyChanges = new ArrayList<CardActivityChange>();
	
	@ManyToMany(mappedBy="cardsActivityDaily")
    private List<TachoDriver> tachosDriver = new ArrayList<TachoDriver>();
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public Date getDailyDate() {
		return dailyDate;
	}

	public void setDailyDate(Date dailyDate) {
		this.dailyDate = dailyDate;
	}
	
	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}

	public void addVehicle(Vehicle vehicle) {
		if (vehicles.contains(vehicle))
			return;
		
		vehicles.add(vehicle);		
	}
	
	public List<CardActivityChange> getCardActivityDailyChanges() {
		return Collections.unmodifiableList(cardActivityDailyChanges);
	}
	
	public void addCardActivityDailyChange(CardActivityChange cardActivityDailyChange) {
		if (cardActivityDailyChanges.contains(cardActivityDailyChange))
				return;
		
		cardActivityDailyChange.setCardActivityDaily(this);		
		cardActivityDailyChanges.add(cardActivityDailyChange);		
	}
	
	public List<TachoDriver> getTachosDriver() {
		return Collections.unmodifiableList(tachosDriver);
	}
	
	public void addTachoDriver(TachoDriver tachoDriver) {
		if (tachosDriver.contains(tachoDriver))
			return;
		
		tachosDriver.add(tachoDriver);		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dailyDate == null) ? 0 : dailyDate.hashCode());
		result = prime * result + Float.floatToIntBits(distance);
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
		if (dailyDate == null) {
			if (other.dailyDate != null)
				return false;
		} else if (!dailyDate.equals(other.dailyDate))
			return false;
		if (Float.floatToIntBits(distance) != Float
				.floatToIntBits(other.distance))
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
				+ ", distance=" + distance + ", dailyDate=" + dailyDate + "]";
	}
}
