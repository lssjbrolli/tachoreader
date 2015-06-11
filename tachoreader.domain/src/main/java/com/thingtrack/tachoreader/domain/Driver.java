package com.thingtrack.tachoreader.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@Table(name="DRIVER")
public class Driver extends Agent {
	@Column(name="CARD_NUMBER", length=20, nullable=false)
	@NotNull
	@NotEmpty
	@Size(max=20)
	private String cardNumber;
			
	@Column(name="CARD_EXPIRATION_DATE", nullable=false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date cardExpiryDate;

	@Column(name="CARD_HOLDER_BIRTHDATE", nullable=false)
	@Temporal(TemporalType.DATE)
	@NotNull
	private Date cardHolderBirthDate;

	@ManyToOne
	@JoinColumn(name="ORGANIZATION_ID", nullable=false)
	@NotNull
	private Organization organization;
	
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(Date cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public Date getCardHolderBirthDate() {
		return cardHolderBirthDate;
	}

	public void setCardHolderBirthDate(Date cardHolderBirthDate) {
		this.cardHolderBirthDate = cardHolderBirthDate;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
