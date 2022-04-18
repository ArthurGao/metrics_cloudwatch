package com.parrotanalytics.api.response.customerapi;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.response.parrotratings.CoreDataPayload;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonPropertyOrder(
{
        "warning", "error", "metric", "interval", "aggregation", "benchmark_de", "data"
})
@JsonInclude(Include.NON_NULL)
public class CustomerDemandResponse extends APIResponse
{
    private static final long serialVersionUID = -785984918596939391L;

    private String metric;

    private String interval;

    private String aggregation;

    @JsonProperty("benchmark_de")
    private Double benchmark_de;

    private List<CoreDataPayload> data;

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public String getAggregation()
    {
        return aggregation;
    }

    public void setAggregation(String aggregation)
    {
        this.aggregation = aggregation;
    }

    public List<CoreDataPayload> getData()
    {
        return data;
    }

    public void setData(List<CoreDataPayload> data)
    {
        this.data = data;
    }

    public void addPayload(CoreDataPayload payload)
    {
        if (data == null)
        {
            data = new ArrayList<CoreDataPayload>();
        }
        data.add(payload);
    }

    public Double getBenchmark_de()
    {
        return benchmark_de;
    }

    public void setBenchmark_de(Double benchmark_de)
    {
        this.benchmark_de = benchmark_de;
    }

}
