package com.parrotanalytics.api.request.report;

public class ReportUsersRequest
{
    private Integer userId;
    public Integer getUserId()
    {
        return userId;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }
    public String getPermission()
    {
        return permission;
    }
    public void setPermission(String permission)
    {
        this.permission = permission;
    }
    private String permission;
}
