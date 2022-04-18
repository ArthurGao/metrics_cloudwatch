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
        "project_id", "project_name", "sponsor", "estimated_hours", "status", "start_date", "end_date",
        "delivered_date", "sponsored", "account_id", "project_time_sheets"
})

public class ProjectResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -2345521868683861683L;

    @JsonProperty("project_id")
    private Integer idProject;

    @JsonProperty("account_id")
    private Integer idAccount;

    @JsonProperty("project_name")
    private String projectName;

    @JsonProperty("estimated_hours")
    private Integer estimatedHours;

    @JsonProperty("status")
    private String status;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("estimated_end_date")
    private String estimatedEndDate;

    @JsonProperty("delivered_date")
    private String deliveredDate;

    @JsonProperty("sponsor")
    private String sponsor;

    @JsonProperty("project_time_sheets")
    private List<TimeSheetResponse> projectTimeSheets;

    @JsonProperty("project_hours")
    private Double projectHours;

    private String link;

    private String feedback;

    private Integer sponsored;

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public Integer getIdProject()
    {
        return idProject;
    }

    public void setIdProject(Integer idProject)
    {
        this.idProject = idProject;
    }

    public String getProjectName()
    {
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public Integer getEstimatedHours()
    {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours)
    {
        this.estimatedHours = estimatedHours;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEstimatedEndDate()
    {
        return estimatedEndDate;
    }

    public void setEstimatedEndDate(String estimatedEndDate)
    {
        this.estimatedEndDate = estimatedEndDate;
    }

    public String getDeliveredDate()
    {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate)
    {
        this.deliveredDate = deliveredDate;
    }

    public List<TimeSheetResponse> getProjectTimeSheets()
    {
        return projectTimeSheets;
    }

    public void setProjectTimeSheets(List<TimeSheetResponse> projectTimeSheets)
    {
        this.projectTimeSheets = projectTimeSheets;
    }

    public Double getProjectHours()
    {
        return projectHours;
    }

    public void setProjectHours(Double projectHours)
    {
        this.projectHours = projectHours;
    }

    public String getSponsor()
    {
        return sponsor;
    }

    public void setSponsor(String sponsor)
    {
        this.sponsor = sponsor;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getFeedback()
    {
        return feedback;
    }

    public void setFeedback(String feedback)
    {
        this.feedback = feedback;
    }

    public Integer getSponsored()
    {
        return sponsored;
    }

    public void setSponsored(Integer sponsored)
    {
        this.sponsored = sponsored;
    }

}
