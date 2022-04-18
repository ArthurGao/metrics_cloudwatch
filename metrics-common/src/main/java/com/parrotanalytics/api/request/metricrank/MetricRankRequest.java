package com.parrotanalytics.api.request.metricrank;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;

public class MetricRankRequest extends DemandRequest
{

    private static final long serialVersionUID = 5840711755621595468L;

    @JsonProperty("context_metric")
    private String contextMetric;

    /**
     * include change in percent in response
     */
    @JsonProperty("change_demand")
    private boolean changeDemand;

    /**
     * include change in rank in response
     */
    @JsonProperty("change_rank")
    private boolean changeRank;

    public String getContextMetric()
    {
        return contextMetric;
    }

    public void setContextMetric(String contextMetric)
    {
        this.contextMetric = contextMetric;
    }

    public static MetricRankRequest defaultRequest()
    {
        MetricRankRequest rankRequest = new MetricRankRequest();

        rankRequest.setInterval("overall");
        rankRequest.setMetricType("");
        rankRequest.setSortBy("dexpercapita");
        rankRequest.setContextMetric("dexpercapita");
        rankRequest.setOrder("desc");
        rankRequest.setPage(1);
        rankRequest.setSize(10);

        DataQuery query = new DataQuery();
        query.setContent("allshows");
        query.setMarkets("WW");
        query.setPeriod("latest");
        rankRequest.setQuery(Arrays.asList(query));

        return rankRequest;
    }

    public boolean isChangeRank()
    {
        return changeRank;
    }

    public void setChangeRank(boolean changeRank)
    {
        this.changeRank = changeRank;
    }

    public boolean isSortByRankChange()
    {
        return "rank_change".equals(sortBy);
    }

    public boolean isSortByDemandChange()
    {
        return "demand_change".equals(sortBy);
    }

    public boolean isChangeDemand()
    {
        return changeDemand;
    }

    public void setChangeDemand(boolean changeDemand)
    {
        this.changeDemand = changeDemand;
    }

    @Override
    public DemandRequest clone()
    {
        MetricRankRequest deepClone = (MetricRankRequest) super.clone();
        deepClone.contextMetric = this.contextMetric;
        deepClone.changeDemand = this.changeDemand;
        deepClone.changeRank = this.changeRank;
        return deepClone;
    }

    public boolean isOverallHeatingUp() {
        return Interval.OVERALL == getIntervalEnum() && isSortByDemandChange();
    }

    public boolean isOverallChangeRank() {
        return Interval.OVERALL == getIntervalEnum()
                && (isSortByRankChange() || isChangeRank());
    }
}
