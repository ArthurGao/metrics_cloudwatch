package com.parrotanalytics.api.response.metricrank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.response.parrotratings.CoreDataPayload;
import com.parrotanalytics.apicore.model.response.APIResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minh Vu
 */
@JsonPropertyOrder({ "warning", "metric", "labeltype", "interval", "datefrom", "dateto", "filters", "pageinfo", "data",
})
@JsonInclude(Include.NON_NULL)
public class MetricRankResponse extends APIResponse
{
    private static final long serialVersionUID = 998695000986475675L;

    @JsonProperty("context_metric")
    private String contextMetric;

    @JsonProperty("dateFrom")
    private String dateFrom;

    @JsonProperty("dateTo")
    private String dateTo;

    @JsonProperty("interval")
    private String interval;

    @JsonProperty("metric_type")
    private String metricType;

    @JsonProperty("order")
    private String order;

    @JsonProperty("sort_by")
    private String sortBy;

    @JsonProperty("period")
    private String period;

    @JsonProperty("benchmark_de")
    private Double benchmark_de;

    private List<CoreDataPayload> data;

    public Double getBenchmark_de()
    {
        return benchmark_de;
    }

    public void setBenchmark_de(Double benchmark_de)
    {
        this.benchmark_de = benchmark_de;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public List<CoreDataPayload> getData()
    {
        return data;
    }

    public void setData(List<CoreDataPayload> data)
    {
        this.data = data;
    }

    public String getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public String getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
    }

    public void addPayload(CoreDataPayload payload)
    {
        if (data == null)
        {
            data = new ArrayList<CoreDataPayload>();
        }
        data.add(payload);
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

}
