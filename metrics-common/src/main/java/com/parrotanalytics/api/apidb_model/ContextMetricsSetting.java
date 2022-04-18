package com.parrotanalytics.api.apidb_model;

import javax.persistence.*;

import com.parrotanalytics.api.commons.constants.TableConstants;

import java.io.Serializable;

/**
 * The persistent class for the context_metrics_settings database table.
 */
@Entity
@Table(name = TableConstants.CONTEXT_METRICS_SETTINGS, schema = TableConstants.METRICS_SCHEMA)
@IdClass(value = ContextMetricsSettingPK.class)
public class ContextMetricsSetting implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 9126927562272414309L;

    @Id
    @Column(name = "metric_type")
    private String metric_type;

    @Column(name = "display_name")
    private String display_name;

    @Column(name = "min_value")
    private int min_value;

    @Column(name = "max_value")
    private int max_value;

    @Column(name = "period")
    private String period;

    @Column(name = "label_buckets")
    private String label_buckets;

    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    public String getMetric_type()
    {
        return metric_type;
    }

    public void setMetric_type(String metric_type)
    {
        this.metric_type = metric_type;
    }

    public String getDisplay_name()
    {
        return display_name;
    }

    public void setDisplay_name(String display_name)
    {
        this.display_name = display_name;
    }

    public int getMin_value()
    {
        return min_value;
    }

    public void setMin_value(int min_value)
    {
        this.min_value = min_value;
    }

    public int getMax_value()
    {
        return max_value;
    }

    public void setMax_value(int max_value)
    {
        this.max_value = max_value;
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

    public Integer getGenreId()
    {
        return genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }
}