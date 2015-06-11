package com.thingtrack.tachoreader.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SuppressWarnings("serial")
@MappedSuperclass
public class Audit implements Serializable { 

	@ManyToOne
	@JoinColumn(name="CREATED_BY_ID")
	private User createdBy;
	
	@Column(name="CREATION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToOne
	@JoinColumn(name="UPDATED_BY_ID")
	private User updatedBy;
	
	@Column(name="UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;
	
    @PrePersist
    protected void onPrePersist() {
    	//populateUser();
    			
        populateTimestamp();
    }
     
    @PreUpdate
    protected void onPreUpdate() {
    	//populateUser();
    	
        populateTimestamp();
    }
    
    protected void populateUser() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (authentication == null)
    		return;
    	
    	UserSecurityContext userSecurityContext = (UserSecurityContext)authentication.getPrincipal();
    	
    	if (createdBy != null)
    		updatedBy = userSecurityContext.getUser();
        else
    		createdBy = userSecurityContext.getUser();
    }
    
    protected void populateTimestamp() {
        if (creationDate != null)
        	updatedDate = new Date(); 
        else
        	creationDate = new Date();    
    }

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
}
