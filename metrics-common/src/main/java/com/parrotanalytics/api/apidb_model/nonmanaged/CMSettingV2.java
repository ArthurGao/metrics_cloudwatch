package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ContextMetricsSettingV2;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.exceptions.APIException;

public class CMSettingV2 implements Serializable
{

    private static final long serialVersionUID = 9126927562272416309L;

    public CMSettingV2(ContextMetricsSettingV2 input)
    {
        this.metric_type = input.getMetric_type();
        this.period = input.getPeriod();
        try
        {
            this.label_buckets = DemandHelper.parseValue(input);
        }
        catch (APIException e)
        {
        }
    }

    @JsonProperty("metric_type")
    private String metric_type;

    @JsonProperty("period")
    private String period;

    @JsonProperty("label_buckets")
    private Object label_buckets;

    public String getMetric_type()
    {
        return metric_type;
    }

    public void setMetric_type(String metric_type)
    {
        this.metric_type = metric_type;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public Object getLabel_buckets()
    {
        return label_buckets;
    }

    public void setLabel_buckets(Object label_buckets)
    {
        this.label_buckets = label_buckets;
    }
}