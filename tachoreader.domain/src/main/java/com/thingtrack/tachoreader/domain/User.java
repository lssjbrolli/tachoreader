package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")
@Entity
@Table(name="USER")
public class User extends Audit implements Serializable {

	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="USERNAME", nullable=false, unique=true, length=50)
	private String username;
	
	@Column(name="PASSWORD", nullable=false, length=50)
	private String password;
		
	@Column(name="LANGUAGE", nullable=false, length=255)
	private String language;
	
	@Column(name="EXPIRATION_DATE")
	@Temporal(TemporalType.DATE)
	private Date expirationDate;
	
	@OneToOne
	@JoinColumn(name="ORGANIZATION_DEFAULT_ID")	
	private Organization organizationDefault;
	
	@OneToOne(mappedBy="user")	
	private Agent agent;
	
	@Column(name="ACTIVE", nullable=false)
	private boolean active = true;
	
	public enum LANGUAGE { 
		Español("es_ES"),
		English("en_EN"),
		Français("fr_FR");
		
		private final String id;
		
		LANGUAGE(String id) { 
			this.id = id; 
		}
		
	    public String getValue() {	    	
	    	return id; 
	    }
	    
	    public String getDescription() {
	    	return name();
	    }	  
	    
	    public static LANGUAGE parse(String id) {
	    	LANGUAGE language = null; // Default
            for (LANGUAGE item : LANGUAGE.values()) {
                if (item.getValue()==id) {
                	language = item;
                    break;
                }
            }
            
            return language;
        }
    }
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Organization getOrganizationDefault() {
		return organizationDefault;
	}

	public void setOrganizationDefault(Organization organizationDefault) {
		this.organizationDefault = organizationDefault;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + "]";
	}
}
