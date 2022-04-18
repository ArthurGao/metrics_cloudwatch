package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the Dashboard database table.
 * 
 */
@Entity
@Table(name = "dashboard", schema = "portal")
@NamedQuery(name = "Dashboard.findAll", query = "SELECT a FROM Dashboard a")
@Deprecated
public class Dashboard implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String OWNER_STRING = "owner";
	public static final String RW_STRING = "rw";
	public static final String R_STRING = "r";
	public static final String PRIVATE_STRING = "private";
	public static final String PUBLIC_STRING = "public";
	public static final String MANAGED_STRING = "managed";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idDashboard;

	private Integer idAccount;

	private String name;

	private String type;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String users;

	private String insights;

	private Integer status;

	public Dashboard(Integer idAccount, String name, String type, Date createdDate, Date updatedDate, String users,
			String insights, Integer status) {
		super();
		this.idAccount = idAccount;
		this.name = name;
		this.type = type;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.users = users;
		this.insights = insights;
		this.status = status;
	}

	/**
	 * Instantiates a new account.
	 */
	public Dashboard() {
	}

	public Integer getIdDashboard() {
		return idDashboard;
	}

	public void setIdDashboard(Integer idDashboard) {
		this.idDashboard = idDashboard;
	}

	public Integer getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getInsights() {
		return insights;
	}

	public void setInsights(String insights) {
		this.insights = insights;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}