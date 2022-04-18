package com.parrotanalytics.api.response.markets;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "pageinfo", "markets"
})
@JsonInclude(Include.NON_NULL)
public class MarketsResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 3054334711813312498L;

    @JsonProperty("markets")
    private List<AccountMarketResponse> markets;

    /**
     * @return the markets
     */
    public List<AccountMarketResponse> getMarkets()
    {
        return markets;
    }

    /**
     * @param markets
     *            the markets to set
     */
    public void setMarkets(List<AccountMarketResponse> markets)
    {
        this.markets = markets;
    }

    /**
     * 
     * @param market
     */
    public void addMarket(AccountMarketResponse market)
    {
        if (market != null)
        {
            if (this.markets == null)
            {
                this.markets = new ArrayList<AccountMarketResponse>(5);
            }
            this.markets.add(market);
        }

    }

}
