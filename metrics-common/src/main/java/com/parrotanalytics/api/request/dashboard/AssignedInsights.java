package com.parrotanalytics.api.request.dashboard;

import com.fasterxml.jackson.databind.JsonNode;

// TODO: Auto-generated Javadoc
/**
 * The Class AssignedInsights.
 */
@Deprecated
public class AssignedInsights {

	/** The id. */
	private Integer id;

	/** The config. */
	private JsonNode config;

	/**
	 * Instantiates a new assigned insights.
	 */
	public AssignedInsights() {

	}

	/**
	 * Instantiates a new assigned insights.
	 *
	 * @param id     the id
	 * @param config the config
	 */
	public AssignedInsights(Integer id, JsonNode config) {
		super();
		this.id = id;
		this.config = config;
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

}
