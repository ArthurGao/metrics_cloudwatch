package com.parrotanalytics.api.request.demand;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.TV360Region;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.Genes;
import com.parrotanalytics.apicore.config.APIConstants;
import com.rits.cloning.Cloner;

/**
 * This is the DataQuery object used in /demand, /rank, /contextmetric
 * endpoints. It is recommended to keep its field members as simple as possible.
 * Dont use any object which has circular references. Otherwise, clone()
 * function will behave strangely
 *
 * @author minhvu
 */
public class DataQuery implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = -6413825601395858987L;

    /*
     * badger content
     */
    private String content;

    /**
     * internal field for content
     */
    private List<String> listContentIDs;

    private List<Long> shortIDsList;

    private List<Integer> regionIDsList;

    /**
     * All of titles considered to be Others in market_share call
     */
    private List<Long> othersShortIDsList;

    /*
     * Type of entity
     *
     * e.g: tvseries, talents, topic, portfolios, movies, etc
     */
    private String entity = Entity.TV_SERIES.value();

    /*
     * badger markets
     */
    private String markets;

    /**
     * internal field for markets
     */
    private List<String> marketsList;

    /*
     * badger time period quick key
     *
     * e.g: today, last7days, last30days, thismonth, lastmonth
     */
    private String period;

    /*
     * badger custom date range : dateFrom
     */
    private DateTime dateFrom;

    /*
     * badger custom date range : dateTo
     */
    private DateTime dateTo;

    /*
     * list of dates between dateFrom AND dateTo
     */
    private List<Date> dateRangeList;

    /*
     * quick flag to check whether query contains multiple dates
     */
    private boolean multipleDates;

    /*
     * sets the number of dates requested in API request
     */
    private Integer daysRequested;

    /*
     * comma separated list of CUSTOM portfolios IDs
     */
    @JsonProperty("portfolio")
    private String portfolio;

    @JsonProperty("regions")
    private String regions;

    /*
     *
     */
    private boolean showtitles;

    @JsonProperty("marketaverage")
    private boolean marketaverage;

    @JsonProperty("weightedaverage")
    private boolean weightedaverage;

    @JsonProperty("market_share")
    private String marketShare;

    @JsonProperty("demand_vs_supply")
    private boolean demandVsSupply;

    private boolean showranks;

    @JsonProperty("show_filter_ranks")
    private boolean showFilterRanks;

    /* flag indicates ranks will be crossed between given market */
    @JsonProperty("cross_compare")
    private boolean crossCompare;

    @JsonProperty("top_markets")
    private boolean topMarkets;

    @JsonProperty("group_by_portfolio")
    private boolean groupByPortfolio;

    // custom portfolios id */
    private List<Integer> portfolioIDs;

    // a preset portfolio id is a hypen separated id of a smart portfolio i.e.
    // 124-245
    private List<String> presetPortfolioIDs;

    /*
     * badger filters
     */
    private DataQueryFilter filters;

    private String grouping;

    /**
     * internal fields for group by genes
     */
    private boolean groupByGene;

    private List<String> groupingGenes = new ArrayList<>();

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getMarketStr()
    {
        return markets;
    }

    public void setMarkets(String markets)
    {
        this.markets = markets;
    }

    public List<String> getListContentIDs()
    {
        return listContentIDs;
    }

    public void setListContentIDs(List<String> listContentIDs)
    {
        this.listContentIDs = listContentIDs;
    }

    public List<Long> getShortIDsList()
    {
        return shortIDsList;
    }

    public void setShortIDsList(List<Long> shortIDsList)
    {
        this.shortIDsList = shortIDsList;
    }

    public List<String> getMarketsList()
    {
        return marketsList;
    }

    public void setListMarkets(List<String> marketsList)
    {
        this.marketsList = marketsList;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public DateTime getDateFrom()
    {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom)
    {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo()
    {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo)
    {
        this.dateTo = dateTo;
    }

    public Map<String, String> getSimpleFilters()
    {
        if (getFilters() != null)
        {
            return getFilters().getSimpleFilters();
        }
        return null;
    }

    public void setSimpleFilters(Map<String, String> simpleFilters)
    {
        if (getFilters() == null)
        {
            setFilters(new DataQueryFilter());
        }
        getFilters().setSimpleFilters(simpleFilters);
    }

    public String getGrouping()
    {
        return grouping;
    }

    public void setGrouping(String grouping)
    {
        this.grouping = grouping;

        if (!StringUtils.isEmpty(this.grouping))
        {
            String[] genesArr = this.grouping.split(",");
            if (genesArr != null && genesArr.length >= 1)
            {
                List<String> supportedGenes = Genes.supportedGenes();

                List<String> providedGenes = Arrays.asList(genesArr);

                groupingGenes = providedGenes.stream().filter(gene -> supportedGenes.contains(gene))
                        .collect(Collectors.toList());

                if (!CollectionUtils.isEmpty(groupingGenes))
                {
                    groupByGene = Boolean.TRUE;
                }
                else
                {
                    groupingGenes = null;
                }
            }
        }
    }

    /**
     * whether query has filters
     *
     * @return
     */
    public boolean hasSimpleFilters()
    {
        return getFilters() != null && getFilters().hasSimplyFilters();
    }

    public boolean isGroupByGene()
    {
        return groupByGene;
    }

    /**
     * number of filters : total genes
     *
     * @return
     */
    public int filtersCount()
    {
        Map<String, String> simpleFilters = getSimpleFilters();
        return MapUtils.size(simpleFilters);
    }

    public List<String> getGroupingGenes()
    {
        return groupingGenes;
    }

    public List<Date> getDateRangeList()
    {
        return dateRangeList;
    }

    public void setDateRangeList(List<Date> dateRangeList)
    {
        this.dateRangeList = dateRangeList;
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

    public String getPortfolio()
    {
        return portfolio;
    }

    public void setPortfolio(String portfolio)
    {
        this.portfolio = portfolio;
    }

    public boolean isShowtitles()
    {
        return showtitles;
    }

    public void setShowtitles(boolean showtitles)
    {
        this.showtitles = showtitles;
    }

    public boolean isMarketaverage()
    {
        return marketaverage;
    }

    public boolean isSimpleOrWeightedMarketAverage()
    {
        return isMarketaverage() || isWeightedaverage();
    }

    public void setMarketaverage(boolean marketaverage)
    {
        this.marketaverage = marketaverage;
    }

    public List<Integer> getPortfolioIDs()
    {
        return portfolioIDs;
    }

    public void setPortfolioIDs(List<Integer> portfolioIDs)
    {
        this.portfolioIDs = portfolioIDs;
    }

    public boolean hasSinglePortfolio()
    {
        return StringUtils.isNotEmpty(this.portfolio)
                && CollectionUtils.size(Arrays.asList(this.portfolio.split(APIConstants.DELIM_COMMA))) == 1;
    }

    public boolean hasMultiplePortfolios()
    {

        return StringUtils.isNotEmpty(this.portfolio)
                && CollectionUtils.size(Arrays.asList(this.portfolio.split(APIConstants.DELIM_COMMA))) > 1;
    }

    /**
     * @return true if having custom OR preset portfolios or collections
     */
    public boolean hasPortfolios()
    {
        return CollectionUtils.isNotEmpty(portfolioIDs) || CollectionUtils.isNotEmpty(presetPortfolioIDs);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((dateFrom == null) ? 0 : dateFrom.hashCode());
        result = prime * result + ((dateTo == null) ? 0 : dateTo.hashCode());
        result = prime * result + ((getFilters() == null) ? 0 : getFilters().hashCode());
        result = prime * result + ((markets == null) ? 0 : markets.hashCode());
        result = prime * result + ((period == null) ? 0 : period.hashCode());
        result = prime * result + ((entity == null) ? 0 : entity.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DataQuery other = (DataQuery) obj;
        if (content == null)
        {
            if (other.content != null)
                return false;
        }
        else if (!content.equals(other.content))
            return false;
        if (dateFrom == null)
        {
            if (other.dateFrom != null)
                return false;
        }
        else if (!dateFrom.equals(other.dateFrom))
            return false;
        if (dateTo == null)
        {
            if (other.dateTo != null)
                return false;
        }
        else if (!dateTo.equals(other.dateTo))
            return false;
        if (getFilters() == null)
        {
            if (other.getFilters() != null)
                return false;
        }
        else if (!getFilters().equals(other.getFilters()))
            return false;
        if (markets == null)
        {
            if (other.markets != null)
                return false;
        }
        else if (!markets.equals(other.markets))
            return false;
        if (period == null)
        {
            if (other.period != null)
                return false;
        }
        else if (!period.equals(other.period))
            return false;
        if (entity == null)
        {
            if (other.entity != null)
                return false;
        }
        else if (!entity.equals(other.entity))
            return false;
        return true;
    }

    public boolean isShowranks()
    {
        return showranks;
    }

    public void setShowranks(boolean showranks)
    {
        this.showranks = showranks;
    }

    @Override
    public String toString()
    {
        return "DataQuery [content=" + content + ", listContentIDs=" + listContentIDs + ", entity=" + entity
                + ", markets=" + markets + ", listMarkets=" + marketsList + ", period=" + period + ", dateFrom="
                + dateFrom + ", dateTo=" + dateTo + ", dateRangeList=" + dateRangeList + ", multipleDates="
                + multipleDates + ", daysRequested=" + daysRequested + ", portfolio=" + portfolio + ", showtitles="
                + showtitles + ", marketaverage=" + marketaverage + ", showranks=" + showranks + ", portfolioIDs="
                + portfolioIDs + ", filters=" + getFilters() + ", grouping=" + grouping + ", groupByGene=" + groupByGene
                + ", groupingGenes=" + groupingGenes + "]";
    }

    public boolean isWeightedaverage()
    {
        return weightedaverage;
    }

    public void setWeightedaverage(boolean weightedaverage)
    {
        this.weightedaverage = weightedaverage;
    }

    public boolean isPeriodYear()
    {
        try
        {
            new SimpleDateFormat("yyyy").parse(this.period);
            return true;
        }
        catch (ParseException ex)
        {
            return false;
        }

    }

    public DataQuery clone()
    {
        return new Cloner().deepClone(this);
    }

    public List<String> getFilterBy(String type)
    {
        if (hasSimpleFilters())
        {
            return StringUtils.isNotEmpty(getSimpleFilters().get(type)) ?
                    Arrays.asList(getSimpleFilters().get(type).split(",")).stream().map(String::toLowerCase)
                            .collect(Collectors.toList()) :
                    null;
        }
        return null;
    }

    public boolean constainsFilterKey(String key)
    {
        if (getSimpleFilters().containsKey(key) && (StringUtils.isNotEmpty(getSimpleFilters().get(key).trim())))
        {
            return true;
        }

        return false;
    }

    public boolean isValidLabelQuery()
    {
        return constainsFilterKey(APIConstants.FILTER_LABEL);
    }

    public String getRegions()
    {
        return regions;
    }

    public void setRegions(String regions)
    {
        this.regions = regions;
    }

    public List<Integer> getRegionIDsList()
    {
        return regionIDsList;
    }

    public void setRegionIDsList(List<Integer> regionIDsList)
    {
        this.regionIDsList = regionIDsList;
    }

    public boolean hasMultiMarketsRegions()
    {
        return CollectionUtils.size(getMarketsList()) + CollectionUtils.size(getRegionIDsList()) > 1;
    }

    public boolean hasMultiMarkets()
    {
        return CollectionUtils.size(getMarketsList()) > 1;
    }

    public boolean hasSingleMarketOrRegion()
    {
        boolean singleMarket =
                StringUtils.isNotEmpty(markets) && Arrays.asList(markets.split(APIConstants.DELIM_COMMA)).size() == 1;
        boolean singleRegion =
                StringUtils.isNotEmpty(regions) && Arrays.asList(regions.split(APIConstants.DELIM_COMMA)).size() == 1;
        return singleMarket || singleRegion;
    }

    /**
     * @return list of market iso and region IDs passed in data query
     */
    public List<Object> getMarketRegionList()
    {
        List<Object> list = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(marketsList))
        {
            list.addAll(marketsList);
        }
        if (CollectionUtils.isNotEmpty(regionIDsList))
        {
            list.addAll(regionIDsList);
        }
        return list;
    }

    public void mergeRegionsToMarketsList(Iterable<TV360Region> regions)
    {
        if (!IterableUtils.isEmpty(regions))
        {
            List<String> countryList = new ArrayList<>();
            for (TV360Region r : regions)
            {
                countryList.addAll(r.getCountryList());
            }
            setListMarkets(countryList);
        }
    }

    public String getMarketShare()
    {
        return marketShare;
    }

    public void setMarketShare(String marketShare)
    {
        this.marketShare = marketShare;
    }

    public List<Long> getOthersShortIDsList()
    {
        return othersShortIDsList;
    }

    public void setOthersShortIDsList(List<Long> othersShortIDsList)
    {
        this.othersShortIDsList = othersShortIDsList;
    }

    public boolean isCrossCompare()
    {
        return crossCompare;
    }

    public void setCrossCompare(boolean crossCompare)
    {
        this.crossCompare = crossCompare;
    }

    public List<String> getPresetPortfolioIDs()
    {
        return presetPortfolioIDs;
    }

    public void setPresetPortfolioIDs(List<String> presetPortfolioIDs)
    {
        this.presetPortfolioIDs = presetPortfolioIDs;
    }

    /** get a list of aggregated portfolios custom + preset **/
    public List<Portfolio> getPortfolios()
    {
        return portfolios;
    }

    /** set a list of aggregated portfolios custom + preset **/
    public void setPortfolios(List<Portfolio> portfolios)
    {
        this.portfolios = portfolios;
    }

    /** list of aggregated portfolios custom + preset **/
    private List<Portfolio> portfolios;

    // internal use
    private List<TV360Region> tv360Regions;

    public boolean isMarketShare()
    {
        return StringUtils.isNotEmpty(marketShare);
    }

    public List<String> getPortfolioStringIDs()
    {
        return StringUtils.isNotEmpty(portfolio) ?
                Arrays.asList(portfolio.split(APIConstants.DELIM_COMMA)) :
                new ArrayList<>();
    }

    public boolean isMultiplePortfoliosPerformanceRequest()
    {
        return hasPortfolios() && !isShowtitles();
    }

    public boolean isSingleEntityRequest()
    {
        return CollectionUtils.size(this.shortIDsList) == 1;
    }

    public boolean isGroupedQuery()
    {
        return StringUtils.isNotEmpty(this.portfolio) && !this.showtitles;
    }

    public boolean isAllPortfolios(String portfolioString)
    {
        return APIConstants.ALL_PORTFOLIOS.equalsIgnoreCase(portfolioString);
    }

    public boolean isAllPortfolios()
    {

        return APIConstants.ALL_PORTFOLIOS.equalsIgnoreCase(portfolio);
    }

    public boolean isAllGenrePortfolios()
    {

        return APIConstants.ALL_GENRE_PORTFOLIOS.equalsIgnoreCase(portfolio);
    }

    public boolean isAllPlatformPortfolios()
    {

        return APIConstants.ALL_PLATFORM_PORTFOLIOS.equalsIgnoreCase(portfolio);
    }

    public boolean isAllPlatformPortfolios(String portfolioString)
    {

        return APIConstants.ALL_PLATFORM_PORTFOLIOS.equalsIgnoreCase(portfolioString)
                || APIConstants.ALL_PLATFORMS.equalsIgnoreCase(portfolioString);
    }

    public List<TV360Region> getTv360Regions()
    {
        return tv360Regions;
    }

    public void setTv360Regions(List<TV360Region> tv360Regions)
    {
        this.tv360Regions = tv360Regions;
    }

    public boolean isMarketBreakdown()
    {
        return !this.marketaverage && !this.weightedaverage;
    }

    public boolean isDemandVsSupply()
    {
        return demandVsSupply;
    }

    public void setDemandVsSupply(boolean demandVsSupply)
    {
        this.demandVsSupply = demandVsSupply;
    }

    public boolean isTopMarkets()
    {
        return topMarkets;
    }

    public void setTopMarkets(boolean topMarkets)
    {
        this.topMarkets = topMarkets;
    }

    public String getEntity()
    {
        return entity;
    }

    public void setEntity(String entity)
    {
        this.entity = entity;
    }

    public Entity getEntityEnum()
    {
        return Entity.fromValue(this.entity);
    }

    public boolean isTVSeriesQuery()
    {
        return this.entity.equals(Entity.TV_SERIES.value());
    }

    public boolean isTalentQuery()
    {
        return this.entity.equals(Entity.TALENT.value());
    }

    public boolean isPortfolioQuery()
    {
        return this.entity.equals(Entity.PORTFOLIO.value());
    }

    public boolean isMovieQuery()
    {
        return this.entity.equals(Entity.MOVIE.value());
    }

    public DataQueryFilter getFilters()
    {
        return filters;
    }

    public void setFilters(DataQueryFilter filters)
    {
        this.filters = filters;
    }

    public boolean hasValidFilter()
    {
        return filters != null && filters.isValid();
    }

    public boolean isScatterPlot(){
        return this.groupByPortfolio && showtitles;
    }

    public boolean isGroupByPortfolio() {
        return groupByPortfolio;
    }

    public void setGroupByPortfolio(boolean groupByPortfolio) {
        this.groupByPortfolio = groupByPortfolio;
    }

    public boolean isShowFilterRanks() {
        return showFilterRanks;
    }

    public void setShowFilterRanks(boolean showFilterRanks) {
        this.showFilterRanks = showFilterRanks;
    }
}
