package com.parrotanalytics.api.response.portfolios;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.IPortfolio;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * The Class PortfoliosResponse holds the properties returned in a call to the portfolios endpoint.
 * 
 * @author Chris
 *
 */
@JsonPropertyOrder(
{
        "warning", "market", "analysis", "grouping", "datefrom", "dateto", "pageinfo", "portfolios"
})
public class PortfoliosResponse extends APIResponse
{
    private static final long serialVersionUID = -6161941186415610912L;

    @JsonProperty("market")
    private String market;

    @JsonProperty("datefrom")
    private String dateFrom;

    @JsonProperty("dateto")
    private String dateTo;

    @JsonProperty("analysis")
    private String analysis;

    @JsonProperty("portfolios")
    private List<? extends IPortfolio> portfolios;

    public PortfoliosResponse()
    {
        // empty args constructor
    }

    /**
     * Instantiates a new portfolios response.
     *
     * @param list
     *            the portfolios
     */
    public PortfoliosResponse(List<? extends IPortfolio> list)
    {
        this.setPortfolios(list);
    }

    public PortfoliosResponse(String market, String dateFrom, String dateTo, String analysis,
            List<? extends IPortfolio> portfolios)
    {
        this.market = market;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.analysis = analysis;
        this.portfolios = portfolios;

    }

    /**
     * @return the market
     */
    public String getMarket()
    {
        return market;
    }

    /**
     * @param market
     *            the market to set
     */
    public void setMarket(String market)
    {
        this.market = market;
    }

    /**
     * @return the dateFrom
     */
    public String getDateFrom()
    {
        return dateFrom;
    }

    /**
     * @param dateFrom
     *            the dateFrom to set
     */
    public void setDateFrom(String dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public String getDateTo()
    {
        return dateTo;
    }

    /**
     * @param dateTo
     *            the dateTo to set
     */
    public void setDateTo(String dateTo)
    {
        this.dateTo = dateTo;
    }

    /**
     * @return the analysis
     */
    public String getAnalysis()
    {
        return analysis;
    }

    /**
     * @param analysis
     *            the analysis to set
     */
    public void setAnalysis(String analysis)
    {
        this.analysis = analysis;
    }

    /**
     * Gets the portfolios.
     *
     * @return the portfolios
     */
    public List<? extends IPortfolio> getPortfolios()
    {
        return portfolios;
    }

    /**
     * Sets the portfolios.
     *
     * @param portfolios2
     *            the new portfolios
     */
    public void setPortfolios(List<? extends IPortfolio> portfolios)
    {
        this.portfolios = portfolios;
    }
}
