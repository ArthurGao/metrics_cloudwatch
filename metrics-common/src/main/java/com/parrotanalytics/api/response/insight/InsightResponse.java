package com.parrotanalytics.api.response.insight;

import com.fasterxml.jackson.databind.JsonNode;
import com.parrotanalytics.apicore.model.response.APIResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class InsightResponse.
 */
@Deprecated
public class InsightResponse extends APIResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The id user. */
	private Integer idUser;

	/** The name. */
	private String name;

	/** The created date. */
	private String createdDate;

	/** The updated date. */
	private String updatedDate;

	private String type;

	/** The data. */
	private JsonNode data;

	/** The config. */
	private JsonNode config;

	/** The visualization. */
	private JsonNode visualization;

	/** The dashboards. */
	private JsonNode dashboards;

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
	 * Gets the id user.
	 *
	 * @return the id user
	 */
	public Integer getIdUser() {
		return idUser;
	}

	/**
	 * Sets the id user.
	 *
	 * @param idUser the new id user
	 */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
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
	 * Gets the data.
	 *
	 * @return the data
	 */
	public JsonNode getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(JsonNode data) {
		this.data = data;
	}

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	public JsonNode getConfig() {
		return config;
	}

	/**
	 * Sets the config.
	 *
	 * @param config the new config
	 */
	public void setConfig(JsonNode config) {
		this.config = config;
	}

	/**
	 * Gets the visualization.
	 *
	 * @return the visualization
	 */
	public JsonNode getVisualization() {
		return visualization;
	}

	/**
	 * Sets the visualization.
	 *
	 * @param visualization the new visualization
	 */
	public void setVisualization(JsonNode visualization) {
		this.visualization = visualization;
	}

	/**
	 * Gets the dashboards.
	 *
	 * @return the dashboards
	 */
	public JsonNode getDashboards() {
		return dashboards;
	}

	/**
	 * Sets the dashboards.
	 *
	 * @param dashboards the new dashboards
	 */
	public void setDashboards(JsonNode dashboards) {
		this.dashboards = dashboards;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
