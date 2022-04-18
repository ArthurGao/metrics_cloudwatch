package com.parrotanalytics.api.request.insightbuilder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class InsightRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Deprecated
public class InsightRequest extends APIRequest {

	private boolean clone;

	private Integer idDashboard;

	private String typeDashboard;

	/** The operation type. */
	protected OperationType operationType;

	/** The id. */
	private Integer id;

	/** The id. */
	private List<Integer> idList;

	/** The id user. */
	private Integer idUser;

	/** The name. */
	private String name;

	/** The data. */
	private JsonNode data;

	/** The config. */
	private JsonNode config;

	/** The visualization. */
	private JsonNode visualization;

	/** The dashboards. */
	private List<AssignedDashboards> dashboards;

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	public String getTypeDashboard() {
		return typeDashboard;
	}

	public void setTypeDashboard(String typeDashboard) {
		this.typeDashboard = typeDashboard;
	}

	/**
	 * Gets the operation type.
	 *
	 * @return the operation type
	 */

	public OperationType getOperationType() {
		return operationType;
	}

	/**
	 * Sets the operation type.
	 *
	 * @param operationType the new operation type
	 */
	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Integer getIdDashboard() {
		return idDashboard;
	}

	public void setIdDashboard(Integer idDashboard) {
		this.idDashboard = idDashboard;
	}

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

	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
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
	public List<AssignedDashboards> getDashboards() {
		return dashboards;
	}

	/**
	 * Sets the dashboards.
	 *
	 * @param dashboards the new dashboards
	 */
	public void setDashboards(List<AssignedDashboards> dashboards) {
		this.dashboards = dashboards;
	}

}
