package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecentUserLog
{

    /**
     * 
     */
    private static final long serialVersionUID = 2207861120318385027L;

    @JsonProperty("eventTime")
    private Date eventTime;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("ipAddress")
    private String ipAddress;

    @JsonProperty("userAgent")
    private String userAgent;

    @JsonProperty("callingURL")
    private String callingURL;

    @JsonProperty("portalSection")
    private String portalSection;

    @JsonProperty("portalSubSection")
    private String portalSubSection;

    private String eventType;

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

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((callingURL == null) ? 0 : callingURL.hashCode());
        result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        result = prime * result + ((ipAddress == null) ? 0 : ipAddress.hashCode());
        result = prime * result + ((portalSection == null) ? 0 : portalSection.hashCode());
        result = prime * result + ((portalSubSection == null) ? 0 : portalSubSection.hashCode());
        result = prime * result + ((userAgent == null) ? 0 : userAgent.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    public RecentUserLog(Integer userId, Date eventTime, String ipAddress, String userAgent, String callingURL,
            String portalSection, String portalSubSection, String eventType)
    {
        super();
        this.eventTime = eventTime;
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.callingURL = callingURL;
        this.portalSection = portalSection;
        this.portalSubSection = portalSubSection;
        this.eventType = eventType;
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
        RecentUserLog other = (RecentUserLog) obj;
        if (callingURL == null)
        {
            if (other.callingURL != null)
                return false;
        }
        else if (!callingURL.equals(other.callingURL))
            return false;
        if (eventTime == null)
        {
            if (other.eventTime != null)
                return false;
        }
        else if (!eventTime.equals(other.eventTime))
            return false;
        if (eventType == null)
        {
            if (other.eventType != null)
                return false;
        }
        else if (!eventType.equals(other.eventType))
            return false;
        if (ipAddress == null)
        {
            if (other.ipAddress != null)
                return false;
        }
        else if (!ipAddress.equals(other.ipAddress))
            return false;
        if (portalSection == null)
        {
            if (other.portalSection != null)
                return false;
        }
        else if (!portalSection.equals(other.portalSection))
            return false;
        if (portalSubSection == null)
        {
            if (other.portalSubSection != null)
                return false;
        }
        else if (!portalSubSection.equals(other.portalSubSection))
            return false;
        if (userAgent == null)
        {
            if (other.userAgent != null)
                return false;
        }
        else if (!userAgent.equals(other.userAgent))
            return false;
        if (userId == null)
        {
            if (other.userId != null)
                return false;
        }
        else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

}
