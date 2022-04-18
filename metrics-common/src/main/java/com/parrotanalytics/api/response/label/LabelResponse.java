package com.parrotanalytics.api.response.label;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

public class LabelResponse extends APIResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7497368492605635907L;

	@JsonProperty("labelitems")
	private List<LabelItemResponse> labelitems;

	public LabelResponse() {

	}

	public List<LabelItemResponse> getLabelitems() {
		return labelitems;
	}

	public void setLabelitems(List<LabelItemResponse> labelitems) {
		this.labelitems = labelitems;
	}

}
