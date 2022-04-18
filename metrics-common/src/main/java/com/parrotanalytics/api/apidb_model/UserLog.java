package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The persistent class for the Account database table.
 * 
 */
@Entity
@Table(name = "userlog_new", schema = "portal")
@NamedQuery(name = "UserLog.findAll", query = "SELECT a FROM UserLog a")
public class UserLog implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id analytic portal. */
    @Id
    @Column(name = "id_analytic_portal")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer idAnalyticPortal;

    /** The date time. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "event_time")
    private Date eventTime;

    /** The user id. */
    @Column(name = "id_user")
    private Integer userId;

    /** The ip address. */
    @Column(name = "ip_address")
    private String ipAddress;

    /** The user agent. */
    @Column(name = "user_agent")
    private String userAgent;

    /** The calling URL. */
    @Column(name = "calling_URL")
    private String callingURL;

    /** The portal section. */
    @Column(name = "portal_section")
    private String portalSection;

    /** The event type. */
    @Column(name = "portal_sub_section")
    private String portalSubSection;

    /** The dataview type. */
    @Column(name = "event_type")
    private String eventType;

    /** The title. */
    @Column(name = "payload")
    private String payload;

    /**
     * Instantiates a new account.
     */
    public UserLog()
    {
    }

    public Integer getIdAnalyticPortal()
    {
        return idAnalyticPortal;
    }

    public void setIdAnalyticPortal(Integer idAnalyticPortal)
    {
        this.idAnalyticPortal = idAnalyticPortal;
    }

    public Date getEventTime()
    {
        return eventTime;
    }

    public void setEventTime(Date eventTime)
    {
        this.eventTime = eventTime;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getUserAgent()
    {
        return userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    public String getCallingURL()
    {
        return callingURL;
    }

    public void setCallingURL(String callingURL)
    {
        this.callingURL = callingURL;
    }

    public String getPortalSection()
    {
        return portalSection;
    }

    public void setPortalSection(String portalSection)
    {
        this.portalSection = portalSection;
    }

    public String getPortalSubSection()
    {
        return portalSubSection;
    }

    public void setPortalSubSection(String portalSubSection)
    {
        this.portalSubSection = portalSubSection;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getPayload()
    {
        return payload;
    }

    public void setPayload(String payload)
    {
        this.payload = payload;
    }

}