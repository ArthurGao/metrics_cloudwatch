package com.parrotanalytics.api.request.project;

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
public class TimeSheetRequest extends APIRequest
{

    protected OperationType timeSheetAction;

    protected Integer idProject;

    @JsonProperty("timesheet_id")
    private Integer idTimeSheet;

    @JsonProperty("user_id")
    private Integer idUser;

    @JsonProperty("task")
    private String task;

    @JsonProperty("hours")
    private String hours;

    @JsonProperty("notes")
    private String notes;

    @JsonProperty("billable")
    private Integer billable;

    public OperationType getTimeSheetAction()
    {
        return timeSheetAction;
    }

    public void setTimeSheetAction(OperationType timeSheetAction)
    {
        this.timeSheetAction = timeSheetAction;
    }

    public Integer getIdProject()
    {
        return idProject;
    }

    public void setIdProject(Integer idProject)
    {
        this.idProject = idProject;
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

    public Integer getBillable()
    {
        return billable;
    }

    public void setBillable(Integer billable)
    {
        this.billable = billable;
    }

}
