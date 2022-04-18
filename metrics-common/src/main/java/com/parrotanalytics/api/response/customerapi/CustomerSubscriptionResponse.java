package com.parrotanalytics.api.response.customerapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

@JsonPropertyOrder(
{
        "records_count", "titles", "markets"
})
@JsonInclude(Include.NON_NULL)
public class CustomerSubscriptionResponse extends APIResponse
{
    private static final long serialVersionUID = 7045205799009444274L;

    @JsonProperty("records_count")
    private Integer recordsCount;

    private List<CustomerTitle> titles;

    private List<CustomerMarket> markets;

    @JsonProperty("show_markets")
    private List<CustomerMarket> showMarkets;

    @JsonProperty("talent_markets")
    private List<CustomerMarket> talentMarkets;

    @JsonProperty("movie_markets")
    private List<CustomerMarket> movieMarkets;

    public Integer getRecordsCount()
    {
        return recordsCount;
    }

    public void setRecordsCount(Integer recordsCount)
    {
        this.recordsCount = recordsCount;
    }

    public List<CustomerTitle> getTitles()
    {
        return titles;
    }

    public void setTitles(List<CustomerTitle> titles)
    {
        this.titles = titles;
    }

    public List<CustomerMarket> getMarkets()
    {
        return markets;
    }

    public void setMarkets(List<CustomerMarket> markets)
    {
        this.markets = markets;
    }

    public List<CustomerMarket> getShowMarkets()
    {
        return showMarkets;
    }

    public void setShowMarkets(List<CustomerMarket> showMarkets)
    {
        this.showMarkets = showMarkets;
    }

    public List<CustomerMarket> getTalentMarkets()
    {
        return talentMarkets;
    }

    public void setTalentMarkets(List<CustomerMarket> talentMarkets)
    {
        this.talentMarkets = talentMarkets;
    }

    public List<CustomerMarket> getMovieMarkets()
    {
        return movieMarkets;
    }

    public void setMovieMarkets(List<CustomerMarket> movieMarkets)
    {
        this.movieMarkets = movieMarkets;
    }

}
