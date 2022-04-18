package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class ContextMetricsSettingPKV2 implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8733919467049443459L;

    @Column(name = "metric_type")
    private String metric_type;

    @Column(name = "period")
    private String period;

    public String getMetric_type()
    {
        return metric_type;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setMetric_type(String metric_type)
    {
        this.metric_type = metric_type;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }
}
