package com.parrotanalytics.api.commons.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.Map;

@JsonPropertyOrder(
{
        "label", "parrot_id", "onboarded_on", "value", "peak", "peak_date", "overall_rank", "rank_by_peak", "change",
        "longevity", "times_average", "travelability", "population"
})
public class ContextMetricDataPoint extends DataPoint
{

    private static final long serialVersionUID = 7783081958274583822L;

    @JsonProperty("de_piracy")
    private Map<String, Double> de_piracy;

    @JsonProperty("de_research")
    private Map<String, Double> de_research;

    @JsonProperty("de_social")
    private Map<String, Double> de_social;

    @JsonProperty("de_video")
    private Map<String, Double> de_video;

    @JsonProperty("longevity")
    private Double longevity;

    @JsonProperty("population")
    private Integer population;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("peak_date")
    private Date peak_date;

    @JsonProperty("rank")
    private Integer rank;

    @JsonProperty("best_rank")
    private Integer best_rank;

    @JsonProperty("times_average")
    private Double times_average;

    @JsonProperty("travelability")
    private Double travelability;

    @JsonProperty("engagement_rate")
    private Double engagement_rate;

    @JsonProperty("fandom_rate")
    private Double fandom_rate;

    @JsonProperty("monetization")
    private Double monetization;

    @JsonProperty("franchisability")
    private Double franchisability;

    @JsonProperty("type")
    private String type;

    @JsonProperty("affinityindex")
    private Double affinityindex;

    @JsonProperty("market")
    private String market;

    @JsonProperty("momentum")
    private Double momentum;

    @JsonProperty("source")
    private String source;

    @JsonProperty("metric")
    private String metric;

    @JsonProperty("change_percent")
    private Double change_percent;

    public Double getChange_percent()
    {
        return change_percent;
    }

    public void setChange_percent(Double change_percent)
    {
        this.change_percent = change_percent;
    }

    public Double getLongevity()
    {
        return longevity;
    }

    public void setLongevity(Double longevity)
    {
        this.longevity = longevity;
    }

    public Integer getPopulation()
    {
        return population;
    }

    public void setPopulation(Integer population)
    {
        this.population = population;
    }

    public Integer getRank()
    {
        return rank;
    }

    public void setRank(Integer rank)
    {
        this.rank = rank;
    }

    public Double getTimes_average()
    {
        return times_average;
    }

    public void setTimes_average(Double times_average)
    {
        this.times_average = times_average;
    }

    public Double getTravelability()
    {
        return travelability;
    }

    public void setTravelability(Double travelability)
    {
        this.travelability = travelability;
    }

    public Double getEngagement_rate()
    {
        return engagement_rate;
    }

    public void setEngagement_rate(Double engagement_rate)
    {
        this.engagement_rate = engagement_rate;
    }

    public Double getFandom_rate()
    {
        return fandom_rate;
    }

    public void setFandom_rate(Double fandom_rate)
    {
        this.fandom_rate = fandom_rate;
    }

    public Double getMonetization()
    {
        return monetization;
    }

    public void setMonetization(Double monetization)
    {
        this.monetization = monetization;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Double getFranchisability()
    {
        return franchisability;
    }

    public void setFranchisability(Double franchisability)
    {
        this.franchisability = franchisability;
    }

    public Double getAffinityindex()
    {
        return affinityindex;
    }

    public void setAffinityindex(Double affinityindex)
    {
        this.affinityindex = affinityindex;
    }

    public String getMarket()
    {
        return market;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public Map<String, Double> getDe_piracy()
    {
        return de_piracy;
    }

    public void setDe_piracy(Map<String, Double> de_piracy)
    {
        this.de_piracy = de_piracy;
    }

    public Map<String, Double> getDe_research()
    {
        return de_research;
    }

    public void setDe_research(Map<String, Double> de_research)
    {
        this.de_research = de_research;
    }

    public Map<String, Double> getDe_social()
    {
        return de_social;
    }

    public void setDe_social(Map<String, Double> de_social)
    {
        this.de_social = de_social;
    }

    public Map<String, Double> getDe_video()
    {
        return de_video;
    }

    public void setDe_video(Map<String, Double> de_video)
    {
        this.de_video = de_video;
    }

    public Double getMomentum()
    {
        return momentum;
    }

    public void setMomentum(Double momentum)
    {
        this.momentum = momentum;
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

    public Date getPeak_date()
    {
        return peak_date;
    }

    public void setPeak_date(Date peak_date)
    {
        this.peak_date = peak_date;
    }

    public Integer getBest_rank()
    {
        return best_rank;
    }

    public void setBest_rank(Integer best_rank)
    {
        this.best_rank = best_rank;
    }
}
