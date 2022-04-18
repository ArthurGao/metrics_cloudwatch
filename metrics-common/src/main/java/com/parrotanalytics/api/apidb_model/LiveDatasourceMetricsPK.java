package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import java.io.Serializable;

public class LiveDatasourceMetricsPK implements Serializable
{

    @Column(name = "short_id")
    private Long short_id;

    @Column(name = "range_key")
    private String range_key;

    @Column(name = "source")
    private String source;

    @Column(name = "metric")
    private String metric;

    public String getRange_key()
    {
        return range_key;
    }

    public void setRange_key(String range_key)
    {
        this.range_key = range_key;
    }

    public Long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(Long short_id)
    {
        this.short_id = short_id;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

}
