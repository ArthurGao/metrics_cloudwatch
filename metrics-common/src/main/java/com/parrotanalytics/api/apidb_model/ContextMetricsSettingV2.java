package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.parrotanalytics.api.commons.constants.TableConstants;

/**
 * The persistent class for the context_metrics_settings database table.
 */
@Entity
@Table(name = TableConstants.CONTEXT_METRICS_SETTINGS_V2, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = ContextMetricsSettingPKV2.class)
public class ContextMetricsSettingV2 implements Serializable
{

    private static final long serialVersionUID = 9126927562272414309L;

    @Id
    @Column(name = "metric_type")
    private String metric_type;

    @Id
    @Column(name = "period")
    private String period;

    @Column(name = "label_buckets")
    private String label_buckets;

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

    public String getLabel_buckets()
    {
        return label_buckets;
    }

    public void setLabel_buckets(String label_buckets)
    {
        this.label_buckets = label_buckets;
    }
}