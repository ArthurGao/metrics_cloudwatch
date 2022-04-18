package com.parrotanalytics.api.request.titlecontext;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.request.demand.DemandRequest;

public class TitleContextMetricRequest extends DemandRequest
{

    private static final long serialVersionUID = -4899295374851215565L;

    /* returns full affinity shows outside of subscription */
    @JsonProperty("affinity_extended")
    private boolean affinityExtended;

    @JsonProperty("context_metric")
    private String contextMetric;

    private List<String> genres;

    /**
     * Internal use
     */
    private ContextMetricType titleContextMetricType;

    @Override
    public DemandRequest clone()
    {
        TitleContextMetricRequest deepClone = (TitleContextMetricRequest) super.clone();
        deepClone.contextMetric = this.contextMetric;
        deepClone.affinityExtended = this.affinityExtended;
        deepClone.genres = this.genres;
        return deepClone;
    }

    public String getContextMetric()
    {
        return contextMetric;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public ContextMetricType getTitleContextMetricType()
    {
        return titleContextMetricType;
    }

    public boolean isAffinityExtended()
    {
        return affinityExtended;
    }

    public void setAffinityExtended(boolean affinityExtended)
    {
        this.affinityExtended = affinityExtended;
    }

    public void setContextMetric(String contextMetric)
    {
        this.contextMetric = contextMetric;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public void setTitleContextMetricType(ContextMetricType titleContextMetricType)
    {
        this.titleContextMetricType = titleContextMetricType;
    }
}
