package com.parrotanalytics.api.response.parrotratings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.commons.NoScientificNotationSerializer;
import com.parrotanalytics.api.commons.model.BreakdownDataPoint;
import com.parrotanalytics.api.commons.model.DataPoint;
import com.rits.cloning.Cloner;

/**
 * 
 * @author sanjeev
 *
 */
@JsonPropertyOrder(
{
        "date", "title", "parrot_id", "portfolio_id", "market", "records_count", "benchmark_de", "portfolio_benchmark",
        "datapoints", "datapointsmap"
})
@JsonInclude(Include.NON_NULL)
public class CoreDataPayload implements Cloneable, Serializable
{
    private static final long serialVersionUID = -226782791989672376L;

    private String date;

    private String title;

    @JsonProperty("parrot_id")
    private String parrotId;

    @JsonProperty("portfolio_id")
    private String portfolioId;

    private String market;

    @JsonProperty("records_count")
    private Integer recordsCount;

    @JsonProperty("benchmark_de")
    private Double benchmarkDE;

    @JsonProperty("benchmark_de_at_peak")
    private Double benchmarkDEAtPeak;

    private List<DataPoint> datapoints;

    @JsonSerialize(contentUsing = NoScientificNotationSerializer.class)
    private Map<String, Object> datapointsmap;

    private String colorKey;

    @JsonProperty("title_acount")
    private Integer titleCount;

    @JsonProperty("supply_benchmark")
    private Double supplyBenchmark;

    @JsonProperty("demand_benchmark")
    private Double demandBenchmark;

    @JsonProperty("selection_title_count")
    private Integer selectionTitleCount;

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getParrotId()
    {
        return parrotId;
    }

    public void setParrotId(String parrotId)
    {
        this.parrotId = parrotId;
    }

    public String getMarket()
    {
        return market;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public String getPortfolioId()
    {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId)
    {
        this.portfolioId = portfolioId;
    }

    public Integer getRecordsCount()
    {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount)
    {
        this.recordsCount = recordsCount;
    }

    public List<DataPoint> getDatapoints()
    {
        return datapoints;
    }

    public Map<String, Object> getDatapointsmap()
    {
        return datapointsmap;
    }

    public void addDataPoint(DataPoint datapoint)
    {
        if (datapoints == null)
        {
            datapoints = Collections.synchronizedList(new ArrayList<DataPoint>());
        }
        datapoints.add(datapoint);
    }
    public void setDataPoints(List<DataPoint> datapoints){
        this.datapoints = datapoints;
    }

    public void setDatapointsmap(Map<String, Object> datapointmap)
    {
        this.datapointsmap = datapointmap;
    }

    @SuppressWarnings("unchecked")
    public void addToDataPointMap(DataPoint datapoint)
    {
        if (datapointsmap == null)
        {
            datapointsmap = new HashMap<String, Object>();
        }

        if (datapoint instanceof BreakdownDataPoint)
        {
            if (datapointsmap.get(datapoint.getLabel()) == null)
            {
                datapointsmap.put(datapoint.getLabel(), new HashMap<String, Double>());
            }

            if (datapointsmap.get(datapoint.getLabel()) instanceof HashMap<?, ?>)
            {
                if (((BreakdownDataPoint) datapoint).getSocial() != null)
                {
                    ((HashMap<String, Double>) datapointsmap.get(datapoint.getLabel())).put("social",
                            ((BreakdownDataPoint) datapoint).getSocial());
                }

                if (((BreakdownDataPoint) datapoint).getVideo() != null)
                {
                    ((HashMap<String, Double>) datapointsmap.get(datapoint.getLabel())).put("video",
                            ((BreakdownDataPoint) datapoint).getVideo());
                }

                if (((BreakdownDataPoint) datapoint).getResearch() != null)
                {
                    ((HashMap<String, Double>) datapointsmap.get(datapoint.getLabel())).put("research",
                            ((BreakdownDataPoint) datapoint).getResearch());
                }

                if (((BreakdownDataPoint) datapoint).getPiracy() != null)
                {
                    ((HashMap<String, Double>) datapointsmap.get(datapoint.getLabel())).put("piracy",
                            ((BreakdownDataPoint) datapoint).getPiracy());
                }

            }

        }
        else
        {
            datapointsmap.put(datapoint.getLabel(), datapoint.getValue());
        }

    }

    public String getColorKey()
    {
        return colorKey;
    }

    public void setColorKey(String colorKey)
    {
        this.colorKey = colorKey;
    }

    public CoreDataPayload clone()
    {
        return new Cloner().deepClone(this);
    }

    public Double getBenchmarkDE()
    {
        return benchmarkDE;
    }

    public void setBenchmarkDE(Double benchmarkDE)
    {
        this.benchmarkDE = benchmarkDE;
    }

    public Integer getTitleCount()
    {
        return titleCount;
    }

    public void setTitleCount(Integer titleCount)
    {
        this.titleCount = titleCount;
    }

    public Double getDemandBenchmark()
    {
        return demandBenchmark;
    }

    public void setDemandBenchmark(Double demandBenchmark)
    {
        this.demandBenchmark = demandBenchmark;
    }

    public Integer getSelectionTitleCount()
    {
        return selectionTitleCount;
    }

    public void setSelectionTitleCount(Integer catalogTitleCount)
    {
        this.selectionTitleCount = catalogTitleCount;
    }

    public Double getSupplyBenchmark()
    {
        return supplyBenchmark;
    }

    public void setSupplyBenchmark(Double supplyBenchmark)
    {
        this.supplyBenchmark = supplyBenchmark;
    }

    public Double getBenchmarkDEAtPeak() {
        return benchmarkDEAtPeak;
    }

    public void setBenchmarkDEAtPeak(Double benchmarkDEAtPeak) {
        this.benchmarkDEAtPeak = benchmarkDEAtPeak;
    }
}
