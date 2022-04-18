package com.parrotanalytics.api.request.dashboard;

@Deprecated
public class DashboardUsersRequest {
	private Integer userId;
	private String permission;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

}
