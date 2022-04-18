package com.parrotanalytics.api.response.parrotratings;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.LiveMetricStatus;

/**
 * @author Minh Vu
 */
@JsonPropertyOrder(
{
        "date", "title", "parrot_id", "portfolio_id", "market", "records_count", "season_number", "gene_type"
})
@JsonInclude(Include.NON_NULL)
public class ContextTitleDataPayload extends CoreDataPayload
{
    private static final long serialVersionUID = -226782791989672376L;

    private String gene;

    @JsonProperty("season_number")
    private String seasonNumber;

    @JsonProperty("gene_type")
    private String geneType;

    @JsonProperty("metric_status")
    private LiveMetricStatus metricStatus;

    @JsonProperty("metric_type")
    private String metricType;

    public String getGene()
    {
        return gene;
    }

    public void setGene(String gene)
    {
        this.gene = gene;
    }

    public String getSeasonNumber()
    {
        return seasonNumber;
    }

    public void setSeasonNumber(String seasonNumber)
    {
        this.seasonNumber = seasonNumber;
    }

    public String getGeneType()
    {
        return geneType;
    }

    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }

    public LiveMetricStatus getMetricStatus()
    {
        return metricStatus;
    }

    public void setMetricStatus(LiveMetricStatus metricStatus)
    {
        this.metricStatus = metricStatus;
    }

    public String getMetricType()
    {
        return metricType;
    }

    public void setMetricType(String metricType)
    {
        this.metricType = metricType;
    }
}
