package com.parrotanalytics.api.request.portfolios;

import com.parrotanalytics.api.commons.ParrotEntityConverter;
import com.parrotanalytics.api.commons.constants.Entity;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.PortfolioQueryType;
import com.parrotanalytics.apicore.model.request.FilteredAPIRequest;
import javax.persistence.Convert;

/**
 * REST API {@link Endpoint} portfolio request.
 * 
 * @author Sanjeev Sharma
 *
 */
public class PortfoliosRequest extends FilteredAPIRequest
{
    /*
     * portfolio query type
     */
    private PortfolioQueryType queryType;

    /*
     * whether to perform portfolios items average across date range
     */
    protected boolean average;

    /*
     * 
     */
    @JsonProperty("content")
    private List<String> content;

    /*
     * markets given in api request
     */
    private Set<String> markets;

    /*
     * does api request contains only single market with one or more portfolios
     */
    boolean isSingleMarket;

    /*
     * does api request contains only multiple markets with only one portfolio
     */
    boolean isMultipleMarkets;

    @JsonIgnore()
    protected boolean latest;

    /* id portfolio can be a preset one like 123-456 */
    @JsonProperty("id_portfolio")
    private String idPortfolio;

    @JsonProperty("filters")
    private Map<String, String> filters;

    @JsonProperty("entity")
    @Convert(converter = ParrotEntityConverter.class)
    private Entity entity;

    public boolean isLatest()
    {
        return latest;
    }

    public void setLatest(boolean latest)
    {
        this.latest = latest;
    }

    public boolean isShared()
    {
        return shared;
    }

    public void setShared(boolean shared)
    {
        this.shared = shared;
    }

    @JsonIgnore()
    protected boolean shared;

    /*
     * getters and setters
     */

    /**
     * @return the queryType
     */
    public PortfolioQueryType getQueryType()
    {
        return queryType;
    }

    /**
     * @param queryType
     *            the queryType to set
     */
    public void setQueryType(PortfolioQueryType queryType)
    {
        this.queryType = queryType;
    }

    /**
     * @return the portfolioId
     */
    public Integer getPortfolioId()
    {
        if (getQueryItems() != null && getQueryItems().itemCount() < 1)
            return null;

        return Integer.parseInt(getQueryItems().getIds().get(0));
    }

    /**
     * @return the average
     */
    public boolean isAverage()
    {
        return average;
    }

    /**
     * @param average
     *            the average to set
     */
    public void setAverage(boolean average)
    {
        this.average = average;
    }

    public List<String> getContent()
    {
        return content;
    }

    public void setContent(List<String> content)
    {
        this.content = content;
    }

    /**
     * 
     * @return
     */
    public List<Integer> getPortfolioIDs()
    {
        if (queryItems != null)
            return queryItems.getIdsAsInt();

        return null;
    }

    /**
     * @return the isSingleMarket
     */
    public boolean isSingleMarket()
    {
        return isSingleMarket;
    }

    /**
     * @param isSingleMarket
     *            the isSingleMarket to set
     */
    public void setSingleMarket(boolean isSingleMarket)
    {
        this.isSingleMarket = isSingleMarket;
    }

    /**
     * @return the isMultipleMarkets
     */
    public boolean isMultipleMarkets()
    {
        return isMultipleMarkets;
    }

    /**
     * @param isMultipleMarkets
     *            the isMultipleMarkets to set
     */
    public void setMultipleMarkets(boolean isMultipleMarkets)
    {
        this.isMultipleMarkets = isMultipleMarkets;
    }

    public String getIdPortfolio()
    {
        return idPortfolio;
    }

    public void setIdPortfolio(String idPortfolio)
    {
        this.idPortfolio = idPortfolio;
    }

    public Map<String, String> getFilters()
    {
        return filters;
    }

    public void setFilters(Map<String, String> filters)
    {
        this.filters = filters;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
