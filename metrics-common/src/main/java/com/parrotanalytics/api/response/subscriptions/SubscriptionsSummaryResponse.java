package com.parrotanalytics.api.response.subscriptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonInclude(Include.NON_NULL)
public class SubscriptionsSummaryResponse
{

    private String type;

    private String subscribed;

    private String used;

    private String remaining;

    private String sponsored;

    private String unit;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("expiry_date")
    private String expirtyDate;

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getSubscribed()
    {
        return subscribed;
    }

    public void setSubscribed(String subscribed)
    {
        this.subscribed = subscribed;
    }

    public String getUsed()
    {
        return used;
    }

    public void setUsed(String used)
    {
        this.used = used;
    }

    public String getRemaining()
    {
        return remaining;
    }

    public void setRemaining(String remaining)
    {
        this.remaining = remaining;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getExpirtyDate()
    {
        return expirtyDate;
    }

    public void setExpirtyDate(String expirtyDate)
    {
        this.expirtyDate = expirtyDate;
    }

    public String getSponsored()
    {
        return sponsored;
    }

    public void setSponsored(String sponsored)
    {
        this.sponsored = sponsored;
    }

}
