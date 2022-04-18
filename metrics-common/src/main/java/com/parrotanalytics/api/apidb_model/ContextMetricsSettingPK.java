package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class ContextMetricsSettingPK implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 8733919467049443459L;

    public String getMetric_type()
    {
        return metric_type;
    }

    public void setMetric_type(String metric_type)
    {
        this.metric_type = metric_type;
    }

    public Integer getGenreId()
    {
        return genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    @Column(name = "metric_type")
    private String metric_type;

    @Column(name = "genre_id")
    private Integer genreId;
}
