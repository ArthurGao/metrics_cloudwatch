package com.parrotanalytics.api.request.label;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * Request body for label management
 * 
 * @author minhvu
 *
 */
public class LabelRequest extends APIRequest {

	private OperationType operationType;

	public LabelRequest() {
		super();
	}

	public LabelRequest(OperationType operationType, String labels, String content) {
		super();
		this.operationType = operationType;
		this.labels = labels;
		this.content = content;
	}

	@JsonProperty("labels")
	private String labels;

	@JsonProperty("content")
	private String content;

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

}
