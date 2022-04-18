package com.parrotanalytics.api.response.parrotratings;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "warning", "interval", "datefrom", "pageinfo", "demandexpressions"
})
@JsonInclude(Include.NON_NULL)
public class CustomQueryResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 4528426131912117750L;

    /*
     * Specifies the density of data points for a particular title.
     * 
     * Valid values are 3600 (hourly) & 86400 (daily)
     */
    protected String interval = Interval.DAILY.value();

    /*
     * 
     */
    @JsonProperty("datefrom")
    protected String dateFrom;

    @JsonProperty("percapita")
    protected Integer percapita;

    /*
     * 
     */
    protected List<BaseDemandResponse> demandexpressions;

    /**
     * @return the interval
     */
    public String getInterval()
    {
        return interval;
    }

    /**
     * @param interval
     *            the interval to set
     */
    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    /**
     * @return the percapita
     */
    public Integer getPercapita()
    {
        return percapita;
    }

    /**
     * @param percapita
     *            the percapita to set
     */
    public void setPercapita(Integer percapita)
    {
        this.percapita = percapita;
    }

    /**
     * @return the dateFrom
     */
    public String getDateFrom()
    {
        return dateFrom;
    }

    /**
     * @param dateFrom
     *            the dateFrom to set
     */
    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the demandexpressions
     */
    public List<BaseDemandResponse> getDemandexpressions()
    {
        return demandexpressions;
    }

    /**
     * @param demandexpressions
     *            the demandexpressions to set
     */
    public void setDemandexpressions(List<BaseDemandResponse> demandexpressions)
    {
        this.demandexpressions = demandexpressions;
    }

}
