package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity
@Table(name="TACHO")
public class Tacho extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
 	
	@Column(name="FILE", nullable=false, length=255)
	private String file;

	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="TACHO_CARD_ACTIVITY_DAILY",
			   joinColumns=@JoinColumn(name="TACHO_ID"),
			   inverseJoinColumns=@JoinColumn(name="CARD_ACTIVITY_DAILY_ID"))
	@OrderBy("dailyDate ASC")
	private List<CardActivityDaily> cardsActivityDaily = new ArrayList<CardActivityDaily>();
	
	@Column(name="TYPE", nullable=false, length=55)
	@Enumerated(EnumType.STRING)
	private TYPE type;
	
	@Transient
	private boolean selected = false;
	
	public enum TYPE {
		DRIVER,
		VEHICLE,
		COMPANY,
		TEST_CENTER
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public List<CardActivityDaily> getCardsActivityDaily() {
		return Collections.unmodifiableList(cardsActivityDaily);
	}

	public void addCardActivityDaily(CardActivityDaily cardActivityDaily) {
		if (cardsActivityDaily.contains(cardActivityDaily))
			return;		
		
		cardsActivityDaily.add(cardActivityDaily);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tacho))
			return false;
		Tacho other = (Tacho) obj;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (selected != other.selected)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tacho [id=" + id + ", file=" + file + ", type=" + type
				+ ", selected=" + selected + "]";
	}	
}
