package com.parrotanalytics.api.response.parrotratings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.nonmanaged.GenePerformance;
import com.parrotanalytics.apicore.model.response.APIResponse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonPropertyOrder(
{
        "warning", "metric", "labeltype", "interval", "datefrom", "dateto", "aggregation", "showranks", "grouping",
        "filters", "benchmark_de", "pageinfo", "data", "genesdata", "bucketdata"
})
@JsonInclude(Include.NON_NULL)
public class DemandResponse extends APIResponse
{
    private static final long serialVersionUID = -785984918596939391L;

    private String metric;

    private String labeltype;

    private String interval;

    private String dateFrom;

    private String dateTo;

    private String aggregation;

    private Double benchmark_de;

    private Double market_sum;

    private Map<String, List<String>> grouping;

    private Map<String, String> filters;

    private List<CoreDataPayload> data;

    private List<Map<String, Object>> genesdata;

    private Map<String, List<CoreDataPayload>> bucketdata;

    private Map<String, List<GenePerformance>> topgenedata;

    @JsonProperty("latest_date")
    private String latestDate;

    private boolean showranks;

    private boolean weightedaverage;

    public String getMetric()
    {
        return metric;
    }

    public void setMetric(String metric)
    {
        this.metric = metric;
    }

    public String getLabeltype()
    {
        return labeltype;
    }

    public void setLabeltype(String labeltype)
    {
        this.labeltype = labeltype;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public String getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public String getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
    }

    public String getAggregation()
    {
        return aggregation;
    }

    public void setAggregation(String aggregation)
    {
        this.aggregation = aggregation;
    }

    public Double getBenchmark_de()
    {
        return benchmark_de;
    }

    public void setBenchmark_de(Double benchmark_de)
    {
        this.benchmark_de = benchmark_de;
    }

    public Map<String, List<String>> getGrouping()
    {
        return grouping;
    }

    public void setGrouping(Map<String, List<String>> grouping)
    {
        this.grouping = grouping;
    }

    public Map<String, String> getFilters()
    {
        return filters;
    }

    public void setFilters(Map<String, String> filters)
    {
        this.filters = filters;
    }

    public List<Map<String, Object>> getGenesdata()
    {
        return genesdata;
    }

    public void setGenesdata(List<Map<String, Object>> genesdata)
    {
        this.genesdata = genesdata;
    }

    public Map<String, List<CoreDataPayload>> getBucketTopNData()
    {
        return bucketdata;
    }

    public void setBucketTopNData(Map<String, List<CoreDataPayload>> bucketdata)
    {
        this.bucketdata = bucketdata;
    }

    public Map<String, List<GenePerformance>> getTopgenedata()
    {
        return topgenedata;
    }

    public void setTopgenedata(Map<String, List<GenePerformance>> topgenedata)
    {
        this.topgenedata = topgenedata;
    }

    public List<CoreDataPayload> getData()
    {
        return data;
    }

    public void setData(List<CoreDataPayload> data)
    {
        this.data = data;
    }

    public void registerGrouping(String gene, String value)
    {
        if (grouping == null)
        {
            grouping = new LinkedHashMap<String, List<String>>();
        }

        if (grouping.get(gene) == null)
        {
            grouping.put(gene, new ArrayList<String>());
        }

        /*
         * registers the gene unique keys
         */
        if (!grouping.get(gene).contains(value))
            grouping.get(gene).add(value);
    }

    public void addPayload(CoreDataPayload payload)
    {
        if (data == null)
        {
            data = new ArrayList<CoreDataPayload>();
        }
        data.add(payload);
    }

    public void addGenesPayload(Map<String, Object> genesPayload)
    {
        if (genesdata == null)
        {
            genesdata = new ArrayList<Map<String, Object>>();
        }
        genesdata.add(genesPayload);
    }

    public void addBucketTopNPayload(String type, CoreDataPayload payload)
    {
        if (bucketdata == null)
        {
            bucketdata = new LinkedHashMap<String, List<CoreDataPayload>>();
        }

        if (bucketdata.get(type) == null)
        {
            bucketdata.put(type, new ArrayList<CoreDataPayload>());
        }

        bucketdata.get(type).add(payload);
    }

    public void addGenePerfPayload(String type, GenePerformance payload)
    {
        if (topgenedata == null)
        {
            topgenedata = new LinkedHashMap<String, List<GenePerformance>>();
        }

        if (topgenedata.get(type) == null)
        {
            topgenedata.put(type, new ArrayList<GenePerformance>());
        }

        topgenedata.get(type).add(payload);
    }

    public boolean isShowranks()
    {
        return showranks;
    }

    public void setShowranks(boolean showranks)
    {
        this.showranks = showranks;
    }

    public boolean isWeightedaverage()
    {
        return weightedaverage;
    }

    public void setWeightedaverage(boolean weightedaverage)
    {
        this.weightedaverage = weightedaverage;
    }

    public Double getMarket_sum()
    {
        return market_sum;
    }

    public void setMarket_sum(Double market_sum)
    {
        this.market_sum = market_sum;
    }

    public DemandResponse withLatestDate(String latestDate){
        this.latestDate = latestDate;
        return this;
    }
}
