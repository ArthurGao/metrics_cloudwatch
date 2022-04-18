package com.parrotanalytics.api.request.customevent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.CustomEvent;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * 
 * @author Minh Vu
 *
 */
public class CustomEventRequest extends APIRequest
{
    @JsonProperty("event_id")
    private Integer eventId;

    @JsonProperty("end_date")
    private Date endDate;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("parrot_id")
    private String parrotID;

    @JsonProperty("start_date")
    private Date startDate;

    @JsonProperty("custom_events")
    private List<CustomEvent> customEvents = new ArrayList<CustomEvent>();

    private OperationType operationType;

    public CustomEventRequest()
    {

    }

    public CustomEventRequest(String eventName, String parrotID, Date startDate, Date endDate)
    {
        super();
        this.eventName = eventName;
        this.parrotID = parrotID;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomEventRequest other = (CustomEventRequest) obj;
        if (customEvents == null)
        {
            if (other.customEvents != null)
                return false;
        }
        else if (!customEvents.equals(other.customEvents))
            return false;
        if (endDate == null)
        {
            if (other.endDate != null)
                return false;
        }
        else if (!endDate.equals(other.endDate))
            return false;
        if (eventId == null)
        {
            if (other.eventId != null)
                return false;
        }
        else if (!eventId.equals(other.eventId))
            return false;
        if (eventName == null)
        {
            if (other.eventName != null)
                return false;
        }
        else if (!eventName.equals(other.eventName))
            return false;
        if (operationType != other.operationType)
            return false;
        if (parrotID == null)
        {
            if (other.parrotID != null)
                return false;
        }
        else if (!parrotID.equals(other.parrotID))
            return false;
        if (startDate == null)
        {
            if (other.startDate != null)
                return false;
        }
        else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public String getEventName()
    {
        return eventName;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((customEvents == null) ? 0 : customEvents.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
        result = prime * result + ((eventName == null) ? 0 : eventName.hashCode());
        result = prime * result + ((operationType == null) ? 0 : operationType.hashCode());
        result = prime * result + ((parrotID == null) ? 0 : parrotID.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public OperationType getOperationType()
    {
        return operationType;
    }

    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    public String getParrotID()
    {
        return parrotID;
    }

    public void setParrotID(String parrotID)
    {
        this.parrotID = parrotID;
    }

    public Integer getEventId()
    {
        return eventId;
    }

    public void setEventId(Integer eventId)
    {
        this.eventId = eventId;
    }

}
