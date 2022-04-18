package com.parrotanalytics.api.request.sysops;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;
import org.joda.time.DateTime;

/**
 * @author Minh Vu
 */
public class PrecomputationRequest extends APIRequest
{

    private static final long serialVersionUID = -3436983387542376933L;
    /**
     * range_key is a predefined key for given precomputation period
     * i.e last90days, jun2019, w132019 etc
     */
    @JsonProperty("range_key")
    private String rangeKey;
    /**
     * if range_key is defined, date_from and date_to is ignore
     */
    @JsonProperty("dateFrom")
    private DateTime dateFrom;

    /**
     * if range_key is defined, date_from and date_to is ignore
     */
    @JsonProperty("dateTo")
    private DateTime dateTo;
    /**
     * daily, weekly, monthly ,quarterly, yearly
     */
    private String interval;

    public PrecomputationRequest()
    {

    }

    public String getRangeKey()
    {
        return rangeKey;
    }

    public void setRangeKey(String rangeKey)
    {
        this.rangeKey = rangeKey;
    }

    public DateTime getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo)
    {
        this.dateTo = dateTo;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }
}
