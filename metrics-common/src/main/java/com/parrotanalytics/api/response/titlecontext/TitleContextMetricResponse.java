package com.parrotanalytics.api.response.titlecontext;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.LiveMetricStatus;
import com.parrotanalytics.api.response.parrotratings.ContextTitleDataPayload;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * @author Minh Vu
 */
@JsonPropertyOrder(
{
        "warning", "metric", "genre", "labeltype", "interval", "datefrom", "dateto", "filters", "pageinfo", "data",
})
@JsonInclude(Include.NON_NULL)
public class TitleContextMetricResponse extends APIResponse
{
    private static final long serialVersionUID = 492015247089388474L;

    @JsonProperty("context_metric")
    private String contextMetric;

    private String labeltype;

    private String dateFrom;

    private String dateTo;

    @JsonProperty("metric_type")
    private String metricType;

    @JsonProperty("order")
    private String order;

    @JsonProperty("sort_by")
    private String sortBy;

    @JsonProperty("period")
    private String period;

    @JsonProperty("genre")
    private String genre;

    private List<ContextTitleDataPayload> data;

    private boolean showranks;

    public List<ContextTitleDataPayload> getData()
    {
        return data;
    }

    public String getDateFrom()
    {
        return dateFrom;
    }

    public String getDateTo()
    {
        return dateTo;
    }

    public String getLabeltype()
    {
        return labeltype;
    }

    public void setData(List<ContextTitleDataPayload> data)
    {
        this.data = data;
    }

    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
    }

    public void setLabeltype(String labeltype)
    {
        this.labeltype = labeltype;
    }

    public void addPayload(ContextTitleDataPayload payload)
    {
        if (data == null)
        {
            data = new ArrayList<ContextTitleDataPayload>();
        }
        data.add(payload);
    }

    public boolean isShowranks()
    {
        return showranks;
    }

    public void setShowranks(boolean showranks)
    {
        this.showranks = showranks;
    }

    public String getContextMetric()
    {
        return contextMetric;
    }

    public void setContextMetric(String contextMetric)
    {
        this.contextMetric = contextMetric;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getSortBy()
    {
        return sortBy;
    }

    public void setSortBy(String sortBy)
    {
        this.sortBy = sortBy;
    }

    public String getMetricType()
    {
        return metricType;
    }

    public void setMetricType(String metricType)
    {
        this.metricType = metricType;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

}
