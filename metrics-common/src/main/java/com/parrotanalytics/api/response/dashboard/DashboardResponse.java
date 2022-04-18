package com.parrotanalytics.api.response.dashboard;

import com.fasterxml.jackson.databind.JsonNode;
import com.parrotanalytics.apicore.model.response.APIResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardResponse.
 */
@Deprecated
public class DashboardResponse extends APIResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The id account. */
	private Integer idAccount;

	/** The name. */
	private String name;

	/** The type. */
	private String type;

	/** The created date. */
	private String createdDate;

	/** The updated date. */
	private String updatedDate;

	/** The users. */
	private JsonNode users;

	/** The insights. */
	private JsonNode insights;

	private Integer status;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the id account.
	 *
	 * @return the id account
	 */
	public Integer getIdAccount() {
		return idAccount;
	}

	/**
	 * Sets the id account.
	 *
	 * @param idAccount the new id account
	 */
	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * Gets the updated date.
	 *
	 * @return the updated date
	 */
	public String getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * Sets the updated date.
	 *
	 * @param updatedDate the new updated date
	 */
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public JsonNode getUsers() {
		return users;
	}

	/**
	 * Sets the users.
	 *
	 * @param users the new users
	 */
	public void setUsers(JsonNode users) {
		this.users = users;
	}

	/**
	 * Gets the insights.
	 *
	 * @return the insights
	 */
	public JsonNode getInsights() {
		return insights;
	}

	/**
	 * Sets the insights.
	 *
	 * @param insights the new insights
	 */
	public void setInsights(JsonNode insights) {
		this.insights = insights;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
