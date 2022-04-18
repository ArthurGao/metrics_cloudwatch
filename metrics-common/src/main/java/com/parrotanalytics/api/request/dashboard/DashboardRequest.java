package com.parrotanalytics.api.request.dashboard;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Deprecated
public class DashboardRequest extends APIRequest {

	private Integer appliedAccount;

	private boolean clone;

	private boolean archive;

	private boolean userLeave;

	private boolean getManaged;

	/** The operation type. */
	protected OperationType operationType;

	private Integer idUser;

	/** The id. */
	private Integer id;

	/** The id account. */
	private Integer idAccount;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The users. */
	private List<DashboardUsersRequest> users;

	/** The insights. */
	private List<AssignedInsights> insights;

	private Integer status;

	public Integer getAppliedAccount() {
		return appliedAccount;
	}

	public void setAppliedAccount(Integer appliedAccount) {
		this.appliedAccount = appliedAccount;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isUserLeave() {
		return userLeave;
	}

	public void setUserLeave(boolean userLeave) {
		this.userLeave = userLeave;
	}

	public boolean isGetManaged() {
		return getManaged;
	}

	public void setGetManaged(boolean getManaged) {
		this.getManaged = getManaged;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<DashboardUsersRequest> getUsers() {
		return users;
	}

	public void setUsers(List<DashboardUsersRequest> users) {
		this.users = users;
	}

	public List<AssignedInsights> getInsights() {
		return insights;
	}

	public void setInsights(List<AssignedInsights> insights) {
		this.insights = insights;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
