package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * The persistent class for the Dashboard database table.
 * 
 */
@Entity
@Table(name = "insight", schema = "portal")
@NamedQuery(name = "Insight.findAll", query = "SELECT a FROM Insight a")
@Deprecated
public class Insight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idInsight;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idUser")
	@JsonBackReference
	private InternalUser user;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	private String data;

	private String config;

	private String visualization;

	private String dashboards;

	/**
	 * Instantiates a new account.
	 */
	public Insight() {
	}

	public Insight(InternalUser user, String name, Date createdDate, Date updatedDate, String data, String config,
			String visualization, String dashboards) {
		super();
		this.user = user;
		this.name = name;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
		this.data = data;
		this.config = config;
		this.visualization = visualization;
		this.dashboards = dashboards;
	}

	public Integer getIdInsight() {
		return idInsight;
	}

	public void setIdInsight(Integer idInsight) {
		this.idInsight = idInsight;
	}

	public InternalUser getUser() {
		return user;
	}

	public void setUser(InternalUser user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getVisualization() {
		return visualization;
	}

	public void setVisualization(String visualization) {
		this.visualization = visualization;
	}

	public String getDashboards() {
		return dashboards;
	}

	public void setDashboards(String dashboards) {
		this.dashboards = dashboards;
	}

}