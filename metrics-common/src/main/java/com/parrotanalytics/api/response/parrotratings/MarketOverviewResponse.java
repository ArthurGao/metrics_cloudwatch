package com.parrotanalytics.api.response.parrotratings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonPropertyOrder(
{
        "warning", "metric", "labeltype", "interval", "datefrom", "dateto", "aggregation", "grouping", "filters",
        "pageinfo", "data", "genesdata"
})
@JsonInclude(Include.NON_NULL)
public class MarketOverviewResponse extends APIResponse
{
    private static final long serialVersionUID = -785984918596939391L;

    private String metric;

    private String labeltype;

    private String interval;

    private String dateFrom;

    private String dateTo;

    private String aggregation;

    private Map<String, List<String>> grouping;

    private HashMap<String, String> filters;

    private List<Map<String, Object>> genesdata;

    private List<CoreDataPayload> data;

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

    public Map<String, List<String>> getGrouping()
    {
        return grouping;
    }

    public void setGrouping(Map<String, List<String>> grouping)
    {
        this.grouping = grouping;
    }

    public HashMap<String, String> getFilters()
    {
        return filters;
    }

    public void setFilters(HashMap<String, String> filters)
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

    public void adGenesPayload(Map<String, Object> genesPayload)
    {
        if (genesdata == null)
        {
            genesdata = new ArrayList<Map<String, Object>>();
        }
        genesdata.add(genesPayload);
    }

}
