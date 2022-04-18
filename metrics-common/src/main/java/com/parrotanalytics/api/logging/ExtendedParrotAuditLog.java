package com.parrotanalytics.api.logging;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.JsonNode;
import com.parrotanalytics.logging.helpers.PL;

/**
 * The Class ExtendedParrotLog adds the message, level and class fields to the
 * regular ParrotLog.
 * 
 * @author chris
 * @author Jackson Lee
 */
public class ExtendedParrotAuditLog extends PL
{

    private JsonNode payload;

    @NotNull
    private String userLogon, ipAddress, userAgent, callingURL, portalSection, portalSubSection, eventType, dateTime,
            accountName, customerName;

    private Integer userId;

    public String getUserLogon()
    {
        return userLogon;
    }

    public void setUserLogon(String userLogon)
    {
        this.userLogon = userLogon;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getPortalSubSection()
    {
        return portalSubSection;
    }

    public void setPortalSubSection(String portalSubSection)
    {
        this.portalSubSection = portalSubSection;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public Integer getUserId()
    {
        return userId;
    }

    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public JsonNode getPayload()
    {
        return payload;
    }

    public void setPayload(JsonNode payload)
    {
        this.payload = payload;
    }

}
