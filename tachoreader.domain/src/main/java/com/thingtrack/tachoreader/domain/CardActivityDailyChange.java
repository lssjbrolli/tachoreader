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
@Table(name="CARD_ACTIVITY_DAILY_CHANGE")
public class CardActivityDailyChange extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="SLOT", nullable=false)
	@Enumerated(EnumType.STRING)
	private SLOT slot;
	
	@Column(name="DRIVING_SYSTEM", nullable=false)
	@Enumerated(EnumType.STRING)
	private DRIVING_SYSTEM drivingSystem;

	@Column(name="CARD_STATUS", nullable=false)
	@Enumerated(EnumType.STRING)
	private CARD_STATUS cardStatus;
	
	@Column(name="TYPE", nullable=false)
	@Enumerated(EnumType.STRING)
	private TYPE type;
	
	@Column(name="RECORD_DATE", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date recordDate;
	
	@ManyToOne
	@JoinColumn(name="CARD_ACTIVITY_DAILY_ID", nullable=false)
	private CardActivityDaily cardActivityDaily;
	
	public enum SLOT {
		FIRST_DRIVER,
		SECOND_DRIVER
	}
	
	public enum DRIVING_SYSTEM {
		SOLO,
		TEAM,
		INDETERMINATE,
		DETERMIDED
	}

	public enum CARD_STATUS {
		INSERTED,
		NOT_INSERTED
	}
	
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

	public SLOT getSlot() {
		return slot;
	}

	public void setSlot(SLOT slot) {
		this.slot = slot;
	}

	public DRIVING_SYSTEM getDrivingSystem() {
		return drivingSystem;
	}

	public void setDrivingSystem(DRIVING_SYSTEM drivingSystem) {
		this.drivingSystem = drivingSystem;
	}

	public CARD_STATUS getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(CARD_STATUS cardStatus) {
		this.cardStatus = cardStatus;
	} 
	
	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public CardActivityDaily getCardActivityDaily() {
		return cardActivityDaily;
	}

	public void setCardActivityDaily(CardActivityDaily cardActivityDaily) {
		this.cardActivityDaily = cardActivityDaily;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cardActivityDaily == null) ? 0 : cardActivityDaily
						.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((recordDate == null) ? 0 : recordDate.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CardActivityDailyChange))
			return false;
		CardActivityDailyChange other = (CardActivityDailyChange) obj;
		if (cardActivityDaily == null) {
			if (other.cardActivityDaily != null)
				return false;
		} else if (!cardActivityDaily.equals(other.cardActivityDaily))
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
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CardActivityDailyChange [id=" + id + ", type=" + type
				+ ", recordDate=" + recordDate + ", cardActivityDaily="
				+ cardActivityDaily + "]";
	}
}
