package com.parrotanalytics.api.commons.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.parrotanalytics.api.commons.NoScientificNotationSerializer;
import java.util.Date;
import lombok.Data;

/**
 * @author Sanjeev Sharma
 * @author Minh Vu
 */
@JsonPropertyOrder(
    {
        "label", "parrot_id", "onboarded_on", "value", "peak", "overall_rank", "rank_by_peak",
        "change", "percentage"
    })
@JsonInclude(Include.NON_NULL)
@Data
public class DataPoint implements IDataPoint {

  private static final long serialVersionUID = 1600745368465079109L;

  @JsonProperty("parrot_id")
  private String parrotId;

  @JsonProperty("iso_code")
  private String isoCode;

  @JsonProperty("portfolio_id")
  private String portfolioId;

  @JsonProperty("label")
  protected String label;

  @JsonProperty("onboarded_on")
  protected Date onboardedOn;

  @JsonProperty("value")
  @JsonSerialize(using = NoScientificNotationSerializer.class)
  protected Double value;

  @JsonProperty("peak")
  @JsonSerialize(using = NoScientificNotationSerializer.class)
  protected Double peak;

  @JsonProperty("overall_rank")
  protected Integer overallRank;

  @JsonProperty("filter_rank")
  protected Integer filterRank;

  @JsonProperty("rank_by_peak")
  private Integer rankByPeak;

  @JsonProperty("best_rank")
  private Integer bestRank;

  @JsonProperty("market_rank")
  protected Integer marketRank;

  @JsonProperty("change")
  protected Double change;

  @JsonProperty("grow_percent")
  private Double growPercent;

  @JsonProperty("market_percentile")
  private Double marketPercentile;

  @JsonProperty("cumulative_value")
  private Double cumulativeValue;

  @JsonProperty("percentile")
  private Double percentile;

  @JsonProperty("percentage")
  private Double percentage;

  @JsonProperty("genre")
  protected String genre;

  @JsonProperty("subgenre")
  protected String subgenre;

  @JsonProperty("genre_tags")
  private String genreTags;

  @JsonProperty("source")
  private String source;

  @JsonProperty("benchmark_de")
  private Double benchmarkDE;

  @JsonProperty("benchmark_de_at_peak")
  private Double benchmarkDEAtPeak;

  @JsonProperty("title_count")
  private Integer titleCount;

  @JsonProperty("dexpercapita")
  @JsonSerialize(using = NoScientificNotationSerializer.class)
  private Double dexpercapita;

  @JsonProperty("dex")
  @JsonSerialize(using = NoScientificNotationSerializer.class)
  private Double dex;

  @JsonProperty("genre_avg")
  protected Double genreAvg;

  public void setOverallRank(Integer overallRank) {
    if (overallRank > 0) {
      this.overallRank = overallRank;
    }
  }

  public void setFilterRank(Integer filterRank) {
    if (filterRank > 0) {
      this.filterRank = filterRank;
    }
  }

  public void setRankByPeak(Integer rankByPeak) {
    if (rankByPeak > 0) {
      this.rankByPeak = rankByPeak;
    }
  }

  public void clearRanks() {
    this.bestRank = null;
    this.overallRank = null;
    this.rankByPeak = null;
    this.filterRank = null;
  }
}
