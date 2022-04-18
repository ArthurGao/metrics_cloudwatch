package com.parrotanalytics.api.response.label;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.response.titles.SubscriptionTitleResponse;

@JsonInclude(Include.NON_NULL)
public class LabelItemResponse extends SubscriptionTitleResponse {

	@JsonProperty("labels")
	private List<String> labels = new ArrayList<String>();

	public LabelItemResponse(List<String> labels, String parrotID) {
		super(parrotID);
		this.labels = labels;
	}
	public LabelItemResponse(String parrotID) {
		super(parrotID);
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public void addLabel(String label) {
		this.labels.add(label);
	}
}
