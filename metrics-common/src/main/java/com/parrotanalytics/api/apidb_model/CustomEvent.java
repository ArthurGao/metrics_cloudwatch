package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the custom_event database table.
 *
 */
@Entity
@Table(name = "custom_event", schema = "portal")
public class CustomEvent implements Cloneable, Serializable
{

    private static final long serialVersionUID = 1120335514449380706L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("event_id")
    @Column(name = "event_id")
    private Integer eventId;

    @JsonProperty("created_by")
    @Column(name = "created_by")
    private Integer createdBy;

    @JsonProperty("end_date")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @JsonProperty("event_name")
    @Column(name = "event_name")
    private String eventName;

    @JsonProperty("id_account")
    @Column(name = "idAccount")
    private Integer idAccount;

    @JsonProperty("start_date")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @JsonProperty("short_id")
    @Column(name = "short_id")
    private Long shortId;

    public CustomEvent()
    {

    }

    public CustomEvent(Integer idAccount, Integer createdBy, String eventName, Long shortId, Date startDate,
            Date endDate)
    {
        this.idAccount = idAccount;
        this.createdBy = createdBy;
        this.eventName = eventName;
        this.shortId = shortId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getCreatedBy()
    {
        return createdBy;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public String getEventName()
    {
        return eventName;
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setCreatedBy(Integer createdBy)
    {
        this.createdBy = createdBy;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setEventName(String eventName)
    {
        this.eventName = eventName;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Long getShortId()
    {
        return shortId;
    }

    public void setShortId(Long shortId)
    {
        this.shortId = shortId;
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
