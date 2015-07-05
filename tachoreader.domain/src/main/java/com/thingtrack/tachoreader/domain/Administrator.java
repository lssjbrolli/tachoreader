package com.thingtrack.tachoreader.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="ADMINISTRATOR")
public class Administrator extends Agent {

	@ManyToMany
	@JoinTable(name="ADMINISTRATOR_ORGANIZATION",
			   joinColumns=@JoinColumn(name="ADMINISTRATOR_ID"),
			   inverseJoinColumns=@JoinColumn(name="ORGANIZATION_ID"))
	private List<Organization> organizations = new ArrayList<Organization>();

	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}
		
	public void addOrganization(Organization organization) {
		organizations.add(organization);		
	}
}
