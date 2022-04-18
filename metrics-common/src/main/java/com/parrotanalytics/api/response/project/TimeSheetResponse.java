package com.parrotanalytics.api.response.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class TimeSheetResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @JsonProperty("timesheet_id")
    private Integer idTimeSheet;

    @JsonProperty("user_id")
    private Integer idUser;

    @JsonProperty("project_id")
    private Integer projectId;

    @JsonProperty("task")
    private String task;

    @JsonProperty("hours")
    private String hours;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("added_date")
    protected String addedDate;

    @JsonProperty("updated_date")
    protected String updatedDate;

    protected Integer billable;

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getIdTimeSheet()
    {
        return idTimeSheet;
    }

    public void setIdTimeSheet(Integer idTimeSheet)
    {
        this.idTimeSheet = idTimeSheet;
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }

    public String getHours()
    {
        return hours;
    }

    public void setHours(String hours)
    {
        this.hours = hours;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getAddedDate()
    {
        return addedDate;
    }

    public void setAddedDate(String addedDate)
    {
        this.addedDate = addedDate;
    }

    public String getUpdatedDate()
    {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    public Integer getBillable()
    {
        return billable;
    }

    public void setBillable(Integer billable)
    {
        this.billable = billable;
    }

}
