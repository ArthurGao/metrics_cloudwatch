package com.parrotanalytics.api.response.insight;

import java.util.List;

import com.parrotanalytics.apicore.model.response.APIResponse;

@Deprecated
public class InsightListResponse extends APIResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	List<InsightResponse> insights;

	public List<InsightResponse> getInsights() {
		return insights;
	}

	public void setInsights(List<InsightResponse> insights) {
		this.insights = insights;
	}

}
