package com.parrotanalytics.api.request.project;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * REST API {@link Endpoint} create user request.
 * 
 * @author Jackson Lee
 *
 */
public class ProjectRequest extends APIRequest
{

    protected OperationType projectAction;

    @JsonProperty("project_id")
    protected Integer idProject;

    @JsonProperty("account_id")
    protected Integer accountId;

    @JsonProperty("project_name")
    protected String projectName;

    @JsonProperty("estimated_hours")
    protected Integer estimatedHours;

    @JsonProperty("start_date")
    protected String startDate;

    @JsonProperty("estimated_end_date")
    protected String estimatedEndDate;

    @JsonProperty("delivered_date")
    protected String deliveredDate;

    @JsonProperty("status")
    protected String status;

    @JsonProperty("sponsor")
    protected String sponsor;

    @JsonProperty("link")
    protected String link;

    protected String feedback;

    protected Integer sponsored;

    @JsonProperty("project_time_sheets")
    protected List<Object> timeSheets;

    public List<Object> getTimeSheets()
    {
        return timeSheets;
    }

    public void setTimeSheets(List<Object> timeSheets)
    {
        this.timeSheets = timeSheets;
    }

    public OperationType getProjectAction()
    {
        return projectAction;
    }

    public void setProjectAction(OperationType projectAction)
    {
        this.projectAction = projectAction;
    }

    public Integer getIdProject()
    {
        return idProject;
    }

    public void setIdProject(Integer idProject)
    {
        this.idProject = idProject;
    }

    public Integer getAccountId()
    {
        return accountId;
    }

    public void setAccountId(Integer accountId)
    {
        this.accountId = accountId;
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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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
