package com.parrotanalytics.api.response.customevent;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * 
 * @author Minh Vu
 *
 */
@JsonPropertyOrder(
{
        "eventId", "parrotID", "idAccount", "eventName", "createdBy", "startDate", "endDate"
})
public class CustomEventResponse implements Serializable
{

    private static final long serialVersionUID = 7512880037283760484L;

    public CustomEventResponse(Integer eventId, Integer idAccount, String parrotID, String eventName, Integer createdBy,
            String startDate, String endDate)
    {
        super();
        this.eventId = eventId;
        this.idAccount = idAccount;
        this.parrotID = parrotID;
        this.eventName = eventName;
        this.createdBy = createdBy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @JsonProperty("event_id")
    private Integer eventId;

    @JsonProperty("created_by")
    private Integer createdBy;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("id_account")
    private Integer idAccount;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("parrot_id")
    private String parrotID;

    public Integer getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getParrotID()
    {
        return parrotID;
    }

    public void setParrotID(String parrotID)
    {
        this.parrotID = parrotID;
    }

}
