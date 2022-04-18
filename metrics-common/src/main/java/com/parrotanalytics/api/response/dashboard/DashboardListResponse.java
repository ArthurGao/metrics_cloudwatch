package com.parrotanalytics.api.response.dashboard;

import java.util.List;

import com.parrotanalytics.apicore.model.response.APIResponse;

@Deprecated
public class DashboardListResponse extends APIResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5016962542327738199L;
	List<DashboardResponse> dashboards;

	public List<DashboardResponse> getDashboards() {
		return dashboards;
	}

	public void setDashboards(List<DashboardResponse> dashboards) {
		this.dashboards = dashboards;
	}

}
