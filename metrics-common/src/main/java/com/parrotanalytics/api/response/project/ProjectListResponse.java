package com.parrotanalytics.api.response.project;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

@JsonPropertyOrder(
{
        "account_id", "account_name", "agreed_hours", "used_hours", "sponsored_hours", "unit", "remaining_hours",
        "end_date", "projects"
})

public class ProjectListResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("account_id")
    int accountId;

    @JsonProperty("account_name")
    String accountName;

    @JsonProperty("agreed_hours")
    int monthlyHours;

    @JsonProperty("remaining_hours")
    double remainingHours;

    @JsonProperty("used_hours")
    double usedHours;

    @JsonProperty("sponsored_hours")
    double sponsoredHours;

    @JsonProperty("end_date")
    String endDate;

    @JsonProperty("projects")
    List<ProjectResponse> projects;

    @JsonProperty("unit")
    String unit;

    public List<ProjectResponse> getProjects()
    {
        return projects;
    }

    public void setProjects(List<ProjectResponse> projects)
    {
        this.projects = projects;
    }

    public int getAccountId()
    {
        return accountId;
    }

    public void setAccountId(int accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public int getMonthlyHours()
    {
        return monthlyHours;
    }

    public void setMonthlyHours(int monthlyHours)
    {
        this.monthlyHours = monthlyHours;
    }

    public double getRemainingHours()
    {
        return remainingHours;
    }

    public void setRemainingHours(double remainingHours)
    {
        this.remainingHours = remainingHours;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public double getUsedHours()
    {
        return usedHours;
    }

    public void setUsedHours(double usedHours)
    {
        this.usedHours = usedHours;
    }

    public double getSponsoredHours()
    {
        return sponsoredHours;
    }

    public void setSponsoredHours(double sponsoredHours)
    {
        this.sponsoredHours = sponsoredHours;
    }

}
