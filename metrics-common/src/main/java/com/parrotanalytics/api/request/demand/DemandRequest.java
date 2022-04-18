package com.parrotanalytics.api.request.demand;

import com.parrotanalytics.api.commons.constants.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.PreComputed;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.model.request.APIRequest;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import org.joda.time.DateTime;

/**
 * @author sanjeev
 * @author Minh Vu
 */
public class DemandRequest extends APIRequest implements Cloneable
{

    /**
     *
     */
    private static final long serialVersionUID = 5583842222515048149L;

    /**
     * {@link APIRequest} calling user
     */
    @JsonIgnore
    protected InternalUser callUser;

    /*
     * show results in given metric type
     *
     * supported values : dex, dex/c
     */
    @JsonProperty("metric_type")
    protected String metricType;

    @JsonProperty("minimum_multiplier")
    protected Double minimumMultiplier;

    @JsonProperty("supporting_metrics")
    private List<String> supportingMetrics;

    public Double getMinimumMultiplier()
    {
        return this.minimumMultiplier;
    }

    public void setMinimumMultiplier(Double minimumMultiplier)
    {
        this.minimumMultiplier = minimumMultiplier;
    }

    public boolean getBenchmark_de()
    {
        return this.benchmark_de;
    }

    public boolean getIncluded_genre()
    {
        return this.included_genre;
    }

    public boolean getIncludedDemandGrowth()
    {
        return this.includedDemandGrowth;
    }

    public boolean getIncludedCumulativeDemand()
    {
        return this.includedCumulativeDemand;
    }

    public boolean getFullMetrics()
    {
        return this.fullMetrics;
    }

    public boolean getMarketPercentile()
    {
        return this.marketPercentile;
    }

    public boolean getChange()
    {
        return this.change;
    }

    public boolean getMultipleDates()
    {
        return this.multipleDates;
    }

    public boolean isIsSingleBadger()
    {
        return this.isSingleBadger;
    }

    public boolean getIsSingleBadger()
    {
        return this.isSingleBadger;
    }

    public void setIsSingleBadger(boolean isSingleBadger)
    {
        this.isSingleBadger = isSingleBadger;
    }

    public boolean getDataLocked()
    {
        return this.dataLocked;
    }

    public boolean getMultipleShowsRequest()
    {
        return this.multipleShowsRequest;
    }

    public boolean isIsSubscriptionShowsRequest()
    {
        return this.isSubscriptionShowsRequest;
    }

    public boolean getIsSubscriptionShowsRequest()
    {
        return this.isSubscriptionShowsRequest;
    }

    public void setIsSubscriptionShowsRequest(boolean isSubscriptionShowsRequest)
    {
        this.isSubscriptionShowsRequest = isSubscriptionShowsRequest;
    }

    public boolean isIsAllShowsRequest()
    {
        return this.isAllShowsRequest;
    }

    public boolean getIsAllShowsRequest()
    {
        return this.isAllShowsRequest;
    }

    public void setIsAllShowsRequest(boolean isAllShowsRequest)
    {
        this.isAllShowsRequest = isAllShowsRequest;
    }

    public boolean isIsSelectedShowsRequest()
    {
        return this.isSelectedShowsRequest;
    }

    public boolean getIsSelectedShowsRequest()
    {
        return this.isSelectedShowsRequest;
    }

    public void setIsSelectedShowsRequest(boolean isSelectedShowsRequest)
    {
        this.isSelectedShowsRequest = isSelectedShowsRequest;
    }

    public boolean getAllTalentsRequest()
    {
        return this.allTalentsRequest;
    }

    public boolean isSelectedTalentsRequest()
    {
        return this.selectedTalentsRequest;
    }

    public boolean getSelectedTalentsRequest()
    {
        return this.selectedTalentsRequest;
    }

    public void setSelectedTalentsRequest(boolean selectedTalentsRequest)
    {
        this.selectedTalentsRequest = selectedTalentsRequest;
    }

    public boolean getMultipleMarketsRegionsRequest()
    {
        return this.multipleMarketsRegionsRequest;
    }

    public boolean isIsSubscriptionMarketsRequest()
    {
        return this.isSubscriptionMarketsRequest;
    }

    public boolean getIsSubscriptionMarketsRequest()
    {
        return this.isSubscriptionMarketsRequest;
    }

    public void setIsSubscriptionMarketsRequest(boolean isSubscriptionMarketsRequest)
    {
        this.isSubscriptionMarketsRequest = isSubscriptionMarketsRequest;
    }

    public boolean isIsAllMarketsRequest()
    {
        return this.isAllMarketsRequest;
    }

    public boolean getIsAllMarketsRequest()
    {
        return this.isAllMarketsRequest;
    }

    public void setIsAllMarketsRequest(boolean isAllMarketsRequest)
    {
        this.isAllMarketsRequest = isAllMarketsRequest;
    }

    public boolean isIsSelectedMarketsRequest()
    {
        return this.isSelectedMarketsRequest;
    }

    public boolean getIsSelectedMarketsRequest()
    {
        return this.isSelectedMarketsRequest;
    }

    public void setIsSelectedMarketsRequest(boolean isSelectedMarketsRequest)
    {
        this.isSelectedMarketsRequest = isSelectedMarketsRequest;
    }

    public boolean getPeriodAllTime()
    {
        return this.periodAllTime;
    }

    public boolean getUseRedshift()
    {
        return this.useRedshift;
    }

    /*
     * display results sorted by this property, if missing sort by 'metric_type'
     *
     * supported values: dex, dex/c, date, change
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

    @JsonProperty("benchmark_de")
    protected boolean benchmark_de;

    @JsonProperty("included_genre")
    private boolean included_genre;

    @JsonProperty("included_demand_growth")
    private boolean includedDemandGrowth;

    @JsonProperty("included_cumulative_demand")
    private boolean includedCumulativeDemand;

    @JsonProperty("full_metrics")
    private boolean fullMetrics;

    @JsonProperty("market_percentile")
    private boolean marketPercentile;

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
     * start day of week interval
     *
     * supported values: daily, weekly, monthly
     */
    @JsonProperty("start_of_interval")
    protected String startOfInterval;

    /*
     * To replace the above field with a start date
     * and calculating the day in the code
     */
    @JsonProperty("interval_start_date")
    protected DateTime intervalStartDate;

    /*
     * interval range specified by the user
     * * supported values: interval = custom
     */
    @JsonProperty("custom_interval_range_in_days")
    protected Integer customIntervalRange;
    /*
     * data query
     */
    protected List<DataQuery> query;

    /*
     * parameter to request demand expressions per N capita
     *
     * 100 (by default), 10000
     */
    protected Integer percapita;

    /*
     * is daily/weekly/monthly change required ?
     */
    protected boolean change;

    /**
     * EXTENDED HELPER PROPERTIES : only created/used by app
     */

    protected PreComputed precomputed;

    /*
     * quick flag to check whether query contains multiple dates
     */
    protected boolean multipleDates;

    /*
     * sets the number of dates requested in API request
     */
    protected Integer daysRequested;

    protected boolean isSingleBadger;

    protected boolean dataLocked;

    protected boolean multipleShowsRequest;

    protected boolean isSubscriptionShowsRequest;

    protected boolean isAllShowsRequest;

    protected boolean isSelectedShowsRequest;

    protected boolean allTalentsRequest;
    
    private boolean allMoviesRequest;
    
    protected boolean selectedTalentsRequest;

    protected boolean multipleMarketsRegionsRequest;

    protected boolean isSubscriptionMarketsRequest;

    protected boolean isAllMarketsRequest;

    protected boolean isSelectedMarketsRequest;

    protected String requestId;

    private boolean periodAllTime;

    protected Double benchmarkDE;

    public Double getBenchmarkDE()
    {
        return this.benchmarkDE;
    }

    public void setBenchmarkDE(Double benchmarkDE)
    {
        this.benchmarkDE = benchmarkDE;
    }

    @JsonIgnore
    private boolean useRedshift;

    public boolean isPeriodAllTime()
    {
        return this.periodAllTime;
    }

    public void setPeriodAllTime(boolean periodAllTime)
    {
        this.periodAllTime = periodAllTime;
    }

    public List<DataQuery> getQuery()
    {
        return query;
    }

    public DataQuery getDataQuery()
    {
        return query.get(0);
    }

    public boolean hasSingleQuery()
    {
        return CollectionUtils.size(query) == 1;
    }

    public boolean hasGroupQuery()
    {
        // secondary is a group of portfolios
        return CollectionUtils.size(query) == 2;
    }

    public void setQuery(List<DataQuery> query)
    {
        this.query = query;
    }

    public InternalUser getCallUser()
    {
        return callUser;
    }

    public void setCallUser(InternalUser callUser)
    {
        this.callUser = callUser;
    }

    public String getMetricType()
    {
        return metricType;
    }

    public void setMetricType(String metricType)
    {
        this.metricType = metricType;
    }

    public Integer getPercapita()
    {
        return percapita;
    }

    public void setPercapita(Integer percapita)
    {
        this.percapita = percapita;
    }

    public boolean isChange()
    {
        return change;
    }

    public void setChange(boolean change)
    {
        this.change = change;
    }

    public boolean isBenchmark_de()
    {
        return benchmark_de;
    }

    public void setBenchmark_de(boolean benchmark_de)
    {
        this.benchmark_de = benchmark_de;
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

    public Interval getIntervalEnum()
    {
        return Interval.fromValue(interval);
    }

    public void setInterval(String interval)
    {
        this.interval = interval;
    }

    public String getStartOfInterval()
    {
        return startOfInterval;
    }

    public void setStartOfInterval(String startOfInterval)
    {
        this.startOfInterval = startOfInterval;
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

    public boolean isMultipleDates()
    {
        return multipleDates;
    }

    public void setMultipleDates(boolean multipleDates)
    {
        this.multipleDates = multipleDates;
    }

    public Integer getDaysRequested()
    {
        return daysRequested;
    }

    public void setDaysRequested(Integer daysRequested)
    {
        this.daysRequested = daysRequested;
    }

    public PreComputed getPrecomputed()
    {
        return precomputed;
    }

    public void setPrecomputed(PreComputed precomputed)
    {
        this.precomputed = precomputed;
    }

    public boolean isSingleBadger()
    {
        return isSingleBadger;
    }

    public void setSingleBadger(boolean isSingleBadger)
    {
        this.isSingleBadger = isSingleBadger;
    }

    public boolean isDataLocked()
    {
        return dataLocked;
    }

    public void setDataLocked(boolean dataLocked)
    {
        this.dataLocked = dataLocked;
    }

    public boolean isMultipleShowsRequest()
    {
        return multipleShowsRequest;
    }

    public void setMultipleShowsRequest(boolean multipleShowsRequest)
    {
        this.multipleShowsRequest = multipleShowsRequest;
    }

    public boolean isAllShowsRequest()
    {
        return isAllShowsRequest;
    }

    public void setAllShowsRequest(boolean isAllShowsRequest)
    {
        this.isAllShowsRequest = isAllShowsRequest;
    }

    public boolean isAllTalentsRequest()
    {
        return allTalentsRequest;
    }

    public void setAllTalentsRequest(boolean allTalentsRequest)
    {
        this.allTalentsRequest = allTalentsRequest;
    }

    public boolean isSubscriptionShowsRequest()
    {
        return isSubscriptionShowsRequest;
    }

    public void setSubscriptionShowsRequest(boolean isSubscriptionShowsRequest)
    {
        this.isSubscriptionShowsRequest = isSubscriptionShowsRequest;
    }

    public boolean isSelectedShowsRequest()
    {
        return isSelectedShowsRequest;
    }

    public void setSelectedShowsRequest(boolean isSelectedShowsRequest)
    {
        this.isSelectedShowsRequest = isSelectedShowsRequest;
    }

    public boolean isMultipleMarketsRegionsRequest()
    {
        return multipleMarketsRegionsRequest;
    }

    public void setMultipleMarketsRegionsRequest(boolean multipleMarketsRequest)
    {
        this.multipleMarketsRegionsRequest = multipleMarketsRequest;
    }

    public boolean isSubscriptionMarketsRequest()
    {
        return isSubscriptionMarketsRequest;
    }

    public void setSubscriptionMarketsRequest(boolean isAllMarketsRequest)
    {
        this.isSubscriptionMarketsRequest = isAllMarketsRequest;
    }

    public boolean isAllMarketsRequest()
    {
        return isAllMarketsRequest;
    }

    public void setAllMarketsRequest(boolean isAllMarketsRequest)
    {
        this.isAllMarketsRequest = isAllMarketsRequest;
    }

    public boolean isWorldwideRequest()
    {
        return this.getDataQuery() != null && StringUtils.isNotEmpty(this.getDataQuery().getMarketStr())
                && this.getDataQuery().getMarketStr().equalsIgnoreCase(APIConstants.WORLDWIDE_CODE);
    }

    public DateTime getIntervalStartDate() { return intervalStartDate; }

    public void setIntervalStartDate(DateTime intervalStartDate) { this.intervalStartDate = intervalStartDate; }

    public Integer getCustomIntervalRange() { return customIntervalRange; }

    public void setCustomIntervalRange(Integer customIntervalRange) { this.customIntervalRange = customIntervalRange; }

    public boolean isSelectedMarketsRequest()
    {
        return isSelectedMarketsRequest;
    }

    public void setSelectedMarketsRequest(boolean isSelectedMarketsRequest)
    {
        this.isSelectedMarketsRequest = isSelectedMarketsRequest;
    }

    public List<String> getListContentIDs()
    {
        return query.get(0).getListContentIDs();
    }

    public void setListContentIDs(List<String> listContentIDs)
    {
        query.get(0).setListContentIDs(listContentIDs);
    }

    public List<String> getListMarkets()
    {

        return query.get(0).getMarketsList();
    }

    public void setListMarkets(List<String> listMarkets)
    {
        query.get(0).setListMarkets(listMarkets);
    }

    public String getMarketsLabel(List<String> markets)
    {
        return StringUtils.join(markets, ',');

    }

    public boolean isCustomStartOfInterval()
    {
        DayOfWeek dayOfWeek = DayOfWeek.fromValue(this.startOfInterval);
        return Interval.WEEKLY.value().equals(this.interval) && dayOfWeek != null && dayOfWeek != DayOfWeek.MON;
    }

    /**
     * Countries columns in weighted_epxressions_computed table is string sorted
     * alphabetically
     *
     * @param markets
     * @return
     */
    public String convertToCountriesColumnValue(List<String> markets)
    {
        // sort the markets in an alphabetical order
        List<String> sorted = new ArrayList<String>(markets);
        Collections.sort(sorted);
        return getMarketsLabel(sorted);

    }

    public List<String> getDateRangeListStr()
    {
        return query.get(0).getDateRangeList().stream().map(d -> CommonsDateUtil.formatDate(d))
                .collect(Collectors.toList());
    }

    public List<Date> getDateRangeList()
    {
        return query.get(0).getDateRangeList();
    }

    public void setDateRangeList(List<Date> dateRangeList)
    {
        query.get(0).setDateRangeList(dateRangeList);
    }

    public boolean isValidLabelQuery()
    {
        return query.get(0).isValidLabelQuery();
    }

    @Override
    public String toString()
    {
        return "DemandRequest [callUser=" + callUser + ", metricType=" + metricType + ", sortBy=" + sortBy + ", order="
                + order + ", benchmark_de=" + benchmark_de + ", aggregation=" + aggregation + ", interval=" + interval
                + ", query=" + query + ", percapita=" + percapita + "]";
    }

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public boolean isIncluded_genre()
    {
        return included_genre;
    }

    public void setIncluded_genre(boolean included_genre)
    {
        this.included_genre = included_genre;
    }

    /**
     * We better to write our own of deep clone so that it is possible to avoid
     * circular references
     */
    @Override
    public DemandRequest clone()
    {
        DemandRequest deepClone = null;
        try
        {
            deepClone = this.getClass().newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (deepClone != null)
        {
            deepClone.aggregation = this.aggregation;
            deepClone.apiAccountId = this.apiAccountId;
            deepClone.apiUserId = this.apiUserId;
            deepClone.benchmark_de = this.benchmark_de;
            deepClone.callUser = this.callUser;
            deepClone.change = this.change;
            deepClone.dataLocked = this.dataLocked;
            deepClone.daysRequested = this.daysRequested;
            deepClone.demoUser = this.demoUser;
            deepClone.format = this.format;
            deepClone.included_genre = this.included_genre;
            deepClone.interval = this.interval;
            deepClone.isAllShowsRequest = this.isAllShowsRequest;
            deepClone.isSelectedMarketsRequest = this.isSelectedMarketsRequest;
            deepClone.isSelectedShowsRequest = this.isSelectedShowsRequest;
            deepClone.allTalentsRequest = this.allTalentsRequest;
            deepClone.selectedTalentsRequest = this.selectedTalentsRequest;
            deepClone.isSingleBadger = this.isSingleBadger;
            deepClone.isSubscriptionMarketsRequest = this.isSubscriptionMarketsRequest;
            deepClone.isSubscriptionShowsRequest = this.isSubscriptionShowsRequest;
            deepClone.isAllMarketsRequest = this.isAllMarketsRequest;
            deepClone.metricType = this.metricType;
            deepClone.multipleDates = this.multipleDates;
            deepClone.multipleMarketsRegionsRequest = this.multipleMarketsRegionsRequest;
            deepClone.multipleShowsRequest = this.multipleShowsRequest;
            deepClone.order = this.order;
            deepClone.page = this.page;
            deepClone.percapita = this.percapita;
            deepClone.periodAllTime = this.periodAllTime;
            deepClone.precomputed = this.precomputed;
            deepClone.query = this.query.stream().map(dq -> dq.clone()).collect(Collectors.toList());
            deepClone.readUser = this.readUser;
            deepClone.requestId = this.requestId;
            deepClone.size = this.size;
            deepClone.sort = SerializationUtils.clone(this.sort);
            deepClone.sortBy = this.sortBy;
            deepClone.superUser = this.superUser;
            deepClone.benchmarkDE = this.benchmarkDE;
            deepClone.minimumMultiplier = this.minimumMultiplier;
            deepClone.startOfInterval = this.startOfInterval;
            deepClone.includedDemandGrowth = this.includedDemandGrowth;
        }
        return deepClone;
    }

    public boolean isIncludedDemandGrowth()
    {
        return includedDemandGrowth;
    }

    public void setIncludedDemandGrowth(boolean includedDemandGrowth)
    {
        this.includedDemandGrowth = includedDemandGrowth;
    }

    public boolean isIncludedCumulativeDemand()
    {
        return includedCumulativeDemand;
    }

    public void setIncludedCumulativeDemand(boolean includedCumulativeDemand)
    {
        this.includedCumulativeDemand = includedCumulativeDemand;
    }

    public boolean isMultiQueries()
    {
        return CollectionUtils.size(this.query) > 1;
    }

    public boolean isFullMetrics()
    {
        return fullMetrics;
    }

    public void setFullMetrics(boolean fullMetrics)
    {
        this.fullMetrics = fullMetrics;
    }

    public boolean isMarketPercentile()
    {
        return marketPercentile;
    }

    public void setMarketPercentile(boolean marketPercentile)
    {
        this.marketPercentile = marketPercentile;
    }

    public boolean isBenchmarkDEAtPeak()
    {
        return CollectionUtils.isNotEmpty(supportingMetrics) && supportingMetrics.contains("benchmark_de_at_peak");
    }

    public boolean isAllMoviesRequest()
    {
        return allMoviesRequest;
    }

    public void setAllMoviesRequest(boolean allMoviesRequest)
    {
        this.allMoviesRequest = allMoviesRequest;
    }
    public boolean isSortByDate(){
        return "date".equalsIgnoreCase(sortBy);
    }
}
