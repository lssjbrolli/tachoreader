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
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TACHO_DRIVER")
public class TachoDriver extends Tacho implements Serializable {
	@ManyToOne
	@JoinColumn(name="DRIVER_ID", nullable=false)	
	private Driver driver;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="TACHO_CARD_ACTIVITY_DAILY",
			   joinColumns=@JoinColumn(name="TACHO_ID"),
			   inverseJoinColumns=@JoinColumn(name="CARD_ACTIVITY_DAILY_ID"))
	@OrderBy("dailyDate ASC")
	private List<CardActivityDaily> cardsActivityDaily = new ArrayList<CardActivityDaily>();
	
	public List<CardActivityDaily> getCardsActivityDaily() {
		return Collections.unmodifiableList(cardsActivityDaily);
	}

	public void addCardActivityDaily(CardActivityDaily cardActivityDaily) {
		if (cardsActivityDaily.contains(cardActivityDaily))
			return;		
		
		cardsActivityDaily.add(cardActivityDaily);
	}	
	
	public Date getStartActivity() {
		if (cardsActivityDaily.size() > 0)
			return cardsActivityDaily.get(0).getDailyDate();
		
		return null;
	}
	
	public Date getEndActivity() {
		if (cardsActivityDaily.size() > 1)
			return cardsActivityDaily.get(cardsActivityDaily.size() - 1).getDailyDate();
		
		return null;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}
