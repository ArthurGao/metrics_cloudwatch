package com.parrotanalytics.api.request.demand;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.apicore.model.request.APIRequest;
import com.rits.cloning.Cloner;

public class BreakdownRequest extends APIRequest implements Cloneable
{

    /**
     * 
     */
    private static final long serialVersionUID = -3753172984286330010L;

    /**
     * {@link APIRequest} calling user
     */
    @JsonIgnore
    protected InternalUser callUser;

    /*
     * display results sorted by this property, if missing sort by 'metric_type'
     * 
     * supported values: ??
     */
    @JsonProperty("sort_by")
    protected String sortBy;

    /*
     * sort in given order
     * 
     * supported values: asc, desc
     */
    @JsonProperty("order")
    protected String order;

    /*
     * aggregation method for displaying metric
     * 
     * supported values: avg, sum
     */
    protected String aggregation;

    /*
     * time frequency or interval for displaying aggregated (AVG or SUM) metric
     * 
     * supported values: daily, weekly, monthly
     */
    protected String interval = Interval.DAILY.value();

    /*
     * data query
     */
    protected DataQuery query;

    /**
     * EXTENDED HELPER PROPERTIES : only created/used by app
     */

    protected boolean multipleMarketsRequest;

    protected boolean isSelectedMarketsRequest;

    protected boolean multipleShowsRequest;

    protected boolean isSelectedShowsRequest;

    public InternalUser getCallUser()
    {
        return callUser;
    }

    public void setCallUser(InternalUser callUser)
    {
        this.callUser = callUser;
    }

    public String getSortBy()
    {
        return sortBy;
    }

    public void setSortBy(String sortBy)
    {
        this.sortBy = sortBy;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getAggregation()
    {
        return aggregation;
    }

    public void setAggregation(String aggregation)
    {
        this.aggregation = aggregation;
    }

    public String getInterval()
    {
        return interval;
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public DataQuery getQuery()
    {
        return query;
    }

    public void setQuery(DataQuery query)
    {
        this.query = query;
    }

    public boolean isMultipleMarketsRequest()
    {
        return multipleMarketsRequest;
    }

    public void setMultipleMarketsRequest(boolean multipleMarketsRequest)
    {
        this.multipleMarketsRequest = multipleMarketsRequest;
    }

    public boolean isSelectedMarketsRequest()
    {
        return isSelectedMarketsRequest;
    }

    public void setSelectedMarketsRequest(boolean isSelectedMarketsRequest)
    {
        this.isSelectedMarketsRequest = isSelectedMarketsRequest;
    }

    public boolean isMultipleShowsRequest()
    {
        return multipleShowsRequest;
    }

    public void setMultipleShowsRequest(boolean multipleShowsRequest)
    {
        this.multipleShowsRequest = multipleShowsRequest;
    }

    public boolean isSelectedShowsRequest()
    {
        return isSelectedShowsRequest;
    }

    public void setSelectedShowsRequest(boolean isSelectedShowsRequest)
    {
        this.isSelectedShowsRequest = isSelectedShowsRequest;
    }

    public List<String> getListContentIDs()
    {
        return query.getListContentIDs();
    }

    public void setListContentIDs(List<String> listContentIDs)
    {
        query.setListContentIDs(listContentIDs);
    }

    public List<String> getListMarkets()
    {

        return query.getMarketsList();
    }

    public void setListMarkets(List<String> listMarkets)
    {
        query.setListMarkets(listMarkets);
    }

    public List<Date> getDateRangeList()
    {
        return query.getDateRangeList();
    }

    public void setDateRangeList(List<Date> dateRangeList)
    {
        query.setDateRangeList(dateRangeList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    public BreakdownRequest clone()
    {
        return new Cloner().deepClone(this);
    }

}
