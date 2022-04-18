package com.parrotanalytics.api.services;

import com.parrotanalytics.api.commons.constants.DayOfWeek;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.parrotanalytics.api.commons.constants.Entity;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.PortfolioItem;
import com.parrotanalytics.api.apidb_model.nonmanaged.DateRangePartition;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressionsEx;
import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioType;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.data.repo.api.PortfolioItemsRepository;
import com.parrotanalytics.api.data.repo.api.PortfoliosRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;

@Service
public class PortfolioService
{

    protected static final Logger logger = LogManager.getLogger(PortfolioService.class);

    @Autowired
    protected PortfoliosRepository portfolioRepo;

    @Autowired
    protected PortfolioItemsRepository portfolioItemRepo;

    @Autowired
    protected DemandRepository demandDataRepo;

    @Autowired
    protected DemandService demandService;

    @Autowired
    protected MetadataCache metadataCache;

    @Autowired
    protected AccountRepository accountRepo;

    public static String getOperatorRegex(String operator)
    {
        return operator.equalsIgnoreCase(APIConstants.DELIM_OR_OPERATOR) ?
                "\\" + APIConstants.DELIM_OR_OPERATOR :
                operator;
    }

    public static final String DELIM_OPERATORS_REGEX = String.format("[%s%s]",
            getOperatorRegex(APIConstants.DELIM_AND_OPERATOR), getOperatorRegex(APIConstants.DELIM_OR_OPERATOR));

    @SuppressWarnings("unchecked")
    public List<Portfolio> fetchSelectedPortfolios(DataQuery dataQuery)
    {
        // try to find custom portfolios and preset portfolios that are mixed
        // together
        List<String> portfolioStrList = Arrays.asList(dataQuery.getPortfolio().split(APIConstants.DELIM_COMMA));

        if (CollectionUtils.isEmpty(portfolioStrList))
            return new ArrayList<>();

        portfolioStrList = deduplicatePortfolios(portfolioStrList);

        List<Integer> portfolioIds = new ArrayList<>();
        List<String> presetPortfolioIDs = new ArrayList<>();

        if (portfolioStrList.contains(APIConstants.ALL_PLATFORM_PORTFOLIOS))
        {
            // in case portfolio string contains allplatformportfolios string
            portfolioStrList.remove(APIConstants.ALL_PLATFORM_PORTFOLIOS);
            List<Integer> allPlatformPortfolioIds = portfolioRepo.findPortfoliosByAccountNType(
                            APIConstants.CUSTOMER_READY_ACCOUNT, PortfolioType.platform).stream().map(p -> p.getIdPortfolio())
                    .collect(Collectors.toList());
            presetPortfolioIDs.addAll(allPlatformPortfolioIds.stream().map(portfolioId -> portfolioId.toString())
                    .collect(Collectors.toList()));
            portfolioIds.addAll(allPlatformPortfolioIds);
        }
        if (portfolioStrList.contains(APIConstants.ALL_GENRE_PORTFOLIOS))
        {
            // in case portfolio string contains allgenreplatforms string
            portfolioStrList.remove(APIConstants.ALL_GENRE_PORTFOLIOS);
            List<Integer> allGenrerPortfolioIds = portfolioRepo.findPortfoliosByAccountNType(
                            APIConstants.CUSTOMER_READY_ACCOUNT, PortfolioType.genre_tags).stream().map(p -> p.getIdPortfolio())
                    .collect(Collectors.toList());
            presetPortfolioIDs.addAll(allGenrerPortfolioIds.stream().map(portfolioId -> portfolioId.toString())
                    .collect(Collectors.toList()));
            portfolioIds.addAll(allGenrerPortfolioIds);
        }

        for (String portStr : portfolioStrList)
        {
            portfolioIds.addAll(Arrays.asList(portStr.split(DELIM_OPERATORS_REGEX)).stream().map(p -> {
                try
                {
                    return Integer.valueOf(p);
                }
                catch (Exception ex)
                {
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList()));
        }
        portfolioIds = portfolioIds.stream().distinct().collect(Collectors.toList());
        List<Portfolio> availPortfolios = portfolioRepo.findMultiplePortfoliosById(portfolioIds);
        Map<String, Portfolio> portfolioLookup = availPortfolios.stream()
                .collect(Collectors.toMap(p -> p.getIdPortfolio().toString(), p -> p));

        // custom portfolio ids
        List<Integer> customPortfolioIds = availPortfolios.stream().filter(p -> p.getType() == PortfolioType.custom)
                .map(Portfolio::getIdPortfolio).collect(Collectors.toList());

        dataQuery.setPortfolioIDs(customPortfolioIds);

        // remaining will be preset ones
        presetPortfolioIDs.addAll(CollectionUtils.removeAll(portfolioStrList,
                customPortfolioIds.stream().map(i -> i.toString()).collect(Collectors.toList())));

        dataQuery.setPresetPortfolioIDs(presetPortfolioIDs);

        List<Portfolio> allPortfolios = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(dataQuery.getPortfolioIDs()))
        {

            List<Portfolio> customPortfolios = availPortfolios.stream().filter(p -> p.getType() == PortfolioType.custom)
                    .collect(Collectors.toList());

            fetchCustomPortfolios(dataQuery, customPortfolios);
            allPortfolios.addAll(customPortfolios);
        }
        if (CollectionUtils.isNotEmpty(dataQuery.getPresetPortfolioIDs()))
        {

            List<Long> catalogShortIDs = new ArrayList<>();
            try
            {
                catalogShortIDs = metadataCache.accountTitleShortIds(
                        accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT));
            }
            catch (APIException e)
            {
                logger.error("Failed to load customer ready account " + APIConstants.CUSTOMER_READY_ACCOUNT + " titles",
                        e);
            }

            List<Portfolio> presetPortfolios = fetchPresetPortfolios(dataQuery, dataQuery.getPresetPortfolioIDs(), portfolioLookup,
                    catalogShortIDs);

            allPortfolios.addAll(presetPortfolios);
        }
        if (dataQuery.hasValidFilter())
        {
            allPortfolios = performFilters(dataQuery, allPortfolios);
        }
        return allPortfolios;
    }

    public List<Portfolio> prefetchShortIDList(List<Portfolio> availPortfolios, List<Long> catalogShortIDs)
    {

        return availPortfolios;
    }

    public List<Portfolio> performFilters(DataQuery dataQuery, List<Portfolio> results)
    {
        for (Portfolio portfolio : results)
        {
            List<Long> filteredShortIDs = demandService.filterTitles(dataQuery.getEntityEnum(), dataQuery.getFilters(),
                    portfolio.getShortIDList());
            filteredShortIDs = filteredShortIDs != null ? filteredShortIDs : new ArrayList<>();
            portfolio.setShortIDList(filteredShortIDs);
        }
        return results;
    }

    private List<String> deduplicatePortfolios(List<String> portfolioStrList)
    {
        Set<String> curatedList = new HashSet<>();
        // only deduplicate if preset portfolio only contains either OR or AND
        // but not
        // both
        for (String portfolioId : portfolioStrList)
        {
            if (portfolioId.contains(APIConstants.DELIM_OR_OPERATOR) && portfolioId.contains(
                    APIConstants.DELIM_AND_OPERATOR))
            {
                curatedList.add(portfolioId);
            }
            else
            {
                String operator = portfolioId.contains(APIConstants.DELIM_OR_OPERATOR) ?
                        APIConstants.DELIM_OR_OPERATOR :
                        APIConstants.DELIM_AND_OPERATOR;
                curatedList.add(StringUtils.join(
                        new HashSet<>(Arrays.asList(portfolioId.split(getOperatorRegex(operator)))).toArray(),
                        operator));
            }

        }

        return new ArrayList<>(portfolioStrList);
    }

    public List<Pair<Portfolio, Page<GroupedExpressions>>> sortScatterPlotGroups(
            Map<Portfolio, Page<GroupedExpressions>> groupByPortfolioDemand, PageRequest pageRequest) {

        return groupByPortfolioDemand.entrySet().stream().filter(entry -> {
            Page<GroupedExpressions> pageResult = entry.getValue();
            return pageResult != null;
        }).map(entry -> {
            Page<GroupedExpressions> pageResult = entry.getValue();
            List<GroupedExpressions> result = pageResult.getContent();
            DoubleSummaryStatistics stats = result.stream().map(ge -> ge.getDexpercapita())
                    .mapToDouble(d -> d).summaryStatistics();
            return new Pair<>(entry, stats);
        }).collect(Collectors.toList()).stream().sorted((p1, p2) -> {
            DoubleSummaryStatistics stats1 = p1.getValue();
            DoubleSummaryStatistics stats2 = p2.getValue();
            int compare = Double.valueOf(stats1.getAverage()).compareTo(stats2.getAverage());
            Sort.Order order = pageRequest.getSort().stream().iterator().next();
            Sort.Direction dir = order.getDirection();
            return dir == Sort.Direction.ASC ?  compare: -compare;
        }).map(p -> p.getKey()).map(p-> new Pair<>(p.getKey(), p.getValue())).collect(Collectors.toList());
    }

    public void fetchCustomPortfolios(DataQuery dataQuery, List<Portfolio> customPortfolios)
    {
        List<PortfolioItem> portfolioItems = portfolioItemRepo.findByPortfolios(dataQuery.getPortfolioIDs());
        Map<Integer, List<PortfolioItem>> groupingByPortfolio = portfolioItems.stream()
                .collect(Collectors.groupingBy(PortfolioItem::getIdPortfolio));

        if (CollectionUtils.isNotEmpty(portfolioItems))
        {
            for (Portfolio portfolio : customPortfolios)
            {
                boolean hasItems = groupingByPortfolio.containsKey(portfolio.getIdPortfolio());
                portfolio.setShortIDList(hasItems ?
                        groupingByPortfolio.get(portfolio.getIdPortfolio()).stream().map(PortfolioItem::getShortId)
                                .collect(Collectors.toList()) :
                        new ArrayList<>());
                portfolio.setStringID(portfolio.getIdPortfolio().toString());
                portfolio.setLabel(portfolio.getName());

            }
        }
    }

    public List<Portfolio> fetchPresetPortfolios(DataQuery dataQuery, List<String> presetPortfolioIDList,
            Map<String, Portfolio> portfolioLookup, List<Long> catalogShortIDs)
    {
        List<Portfolio> presetPortfolios = new ArrayList<>();

        List<CompletableFuture<Portfolio>> futuresList = new ArrayList<>();
        try
        {
            // we initialize the comprising items (shortIDList) in leaf portfolios
            for (Portfolio p : portfolioLookup.values())
            {
                CompletableFuture<Portfolio> future = CompletableFuture.supplyAsync(
                    new PortfolioItemsProvider(dataQuery, catalogShortIDs, p, demandService));
                futuresList.add(future);
            }
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    futuresList.toArray(new CompletableFuture[futuresList.size()]));
            CompletableFuture<List<Portfolio>> allCompletableFuture = allFutures.thenApply(
                    future -> futuresList.stream().map(completableFuture -> completableFuture.join())
                            .collect(Collectors.toList()));
            // execute the parallel completable
            allCompletableFuture.get();

            futuresList.clear();

            for (String presetPortfolioID : presetPortfolioIDList)
            {
                CompletableFuture<Portfolio> future = CompletableFuture.supplyAsync(() -> {
                    return fetchPresetPortfolio(dataQuery, portfolioLookup, catalogShortIDs, presetPortfolioID);
                });
                futuresList.add(future);
            }
            allFutures = CompletableFuture.allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
            allCompletableFuture = allFutures.thenApply(
                    future -> futuresList.stream().map(completableFuture -> completableFuture.join())
                            .collect(Collectors.toList()));
            // execute the parallel completable
            presetPortfolios = allCompletableFuture.get();
        }
        catch (InterruptedException | ExecutionException e)
        {
            logger.error("Failed to initialize the shortIDList field in preset portfolios", e);
        }

        return presetPortfolios;
    }



    public Portfolio fetchPresetPortfolio(DataQuery dataQuery, Map<String, Portfolio> portfolioLookup, List<Long> catalogShortIDs,
                                          String presetPortfolioID)
    {
        Portfolio resultPortfolio = new Portfolio();
        List<Long> contentShortIDs = new ArrayList<>();
        // always process AND logic first then OR one
        // for example Action-Netflix|Netflix Originals = array of [Action,
        // Netflix |
        // Netflix Originals] of AND logic. The precendence can be improved
        // later using
        // brackets i.e. ( )
        if (presetPortfolioID.contains(APIConstants.DELIM_AND_OPERATOR))
        {
            contentShortIDs = filterByANDOperatorForPreset(dataQuery, portfolioLookup, catalogShortIDs, presetPortfolioID);
        }
        else
        {
            contentShortIDs = filterByOROperatorForPreset(dataQuery, portfolioLookup, catalogShortIDs, presetPortfolioID);
        }
        resultPortfolio.setShortIDList(contentShortIDs);
        resultPortfolio.setStringID(presetPortfolioID);
        String name = deducePortfolioName(portfolioLookup, presetPortfolioID);
        resultPortfolio.setName(name);
        resultPortfolio.setLabel(resultPortfolio.getName());
        return resultPortfolio;
    }

    @SuppressWarnings("unchecked")
    private List<Long> filterByOROperatorForPreset(DataQuery dataQuery, Map<String, Portfolio> portfolioLookup, List<Long> catalogShortIDs,
                                                   String presetPortfolioID)
    {
        List<String> elementPresetPortfolioIDList = Arrays.asList(
                presetPortfolioID.split(getOperatorRegex(APIConstants.DELIM_OR_OPERATOR)));
        Set<Long> contentShortIDSet = new HashSet<>();
        boolean first = true;
        for (String elementPortID : elementPresetPortfolioIDList)
        {
            Portfolio portfolio = portfolioLookup.get(elementPortID);
            if (portfolio == null || CollectionUtils.isEmpty(portfolio.getShortIDList()))
                continue;
            if (first)
            {
                contentShortIDSet.addAll(portfolio.getShortIDList());
                first = false;
            }
            else
            {
                contentShortIDSet = new HashSet<>(CollectionUtils.union(contentShortIDSet, portfolio.getShortIDList()));
            }
        }
        return new ArrayList<>(contentShortIDSet);

    }

    @SuppressWarnings("unchecked")
    private List<Long> filterByANDOperatorForPreset(DataQuery dataQuery, Map<String, Portfolio> portfolioLookup, List<Long> catalogShortIDs,
                                                    String presetPortfolioID)
    {
        List<Long> contentShortIDs;
        List<String> breakdownPortfolioIDs = Arrays.asList(
                presetPortfolioID.split(getOperatorRegex(APIConstants.DELIM_AND_OPERATOR)));
        List<Portfolio> andPresetPortfolios = fetchPresetPortfolios(dataQuery, breakdownPortfolioIDs, portfolioLookup,
                catalogShortIDs);
        Set<Long> contentShortIDSet = new HashSet<>();
        boolean first = true;
        for (Portfolio portfolio : andPresetPortfolios)
        {
            if (portfolio == null || CollectionUtils.isEmpty(portfolio.getShortIDList()))
            {
                // one of portfolio preset get 0 shortIDs, we know that the AND
                // with this portfolio will result
                // empty final portfolio
                contentShortIDSet = new HashSet<>();
                break;
            }
            if (first)
            {
                contentShortIDSet.addAll(portfolio.getShortIDList());
                first = false;
            }
            else
            {
                contentShortIDSet = new HashSet<>(
                        CollectionUtils.intersection(contentShortIDSet, portfolio.getShortIDList()));
            }
        }
        contentShortIDs = new ArrayList<>(contentShortIDSet);
        return contentShortIDs;
    }

    private String deducePortfolioName(Map<String, Portfolio> portfolioLookup, String portID)
    {
        if (!portID.contains(APIConstants.DELIM_AND_OPERATOR))
        {
            List<String> elementPortIds = Arrays.asList(portID.split(getOperatorRegex(APIConstants.DELIM_OR_OPERATOR)));
            List<Portfolio> elementPorts = elementPortIds.stream().map(id -> portfolioLookup.get(id))
                    .collect(Collectors.toList());

            elementPorts.sort(Comparator.comparingInt(o -> o.getType().order()));

            if (elementPorts.size() == 2 && elementPorts.get(0).getType() == PortfolioType.platform
                    && elementPorts.get(1).getType() == PortfolioType.network && elementPorts.get(0).getFilterValue()
                    .equals(elementPorts.get(1).getFilterValue()))
            {
                return String.format("%s", elementPorts.get(0).getName());
            }
            else
            {
                List<String> elementPortNames = elementPorts.stream().map(elementPort -> {

                    if (elementPort.getType() == PortfolioType.platform)
                    {
                        return elementPort.getName() + " Licenced";
                    }
                    else if (elementPort.getType() == PortfolioType.network)
                    {
                        return elementPort.getName() + " Originals";
                    }
                    return elementPort.getName();

                }).collect(Collectors.toList());
                return StringUtils.join(elementPortNames, " ");
            }
        }

        List<String> elementPortIds = Arrays.asList(portID.split(getOperatorRegex(APIConstants.DELIM_AND_OPERATOR)));

        List<String> elementPortNames = elementPortIds.stream()
                .map(elementPortId -> deducePortfolioName(portfolioLookup, elementPortId)).collect(Collectors.toList());
        return StringUtils.join(elementPortNames, " ");

    }

    public List<Portfolio> fetchCustomPortfolios(List<Integer> portfolioIds)
    {
        List<Portfolio> customPortfolios = portfolioRepo.findMultiplePortfoliosById(portfolioIds);

        List<PortfolioItem> portfolioItems = portfolioItemRepo.findByPortfolios(portfolioIds);
        Map<Integer, List<PortfolioItem>> groupingByPortfolio = portfolioItems.stream()
                .collect(Collectors.groupingBy(PortfolioItem::getIdPortfolio));

        if (CollectionUtils.isNotEmpty(portfolioItems))
        {
            for (Portfolio portfolio : customPortfolios)
            {
                boolean hasItems = groupingByPortfolio.containsKey(portfolio.getIdPortfolio());
                portfolio.setShortIDList(hasItems ?
                        groupingByPortfolio.get(portfolio.getIdPortfolio()).stream().map(PortfolioItem::getShortId)
                                .collect(Collectors.toList()) :
                        new ArrayList<>());

            }
        }
        return customPortfolios;
    }

    public Portfolio createCustomPortfolio(int portfolioId, String portName)
    {
        Portfolio portfolio = new Portfolio();
        portfolio.setIdPortfolio(portfolioId);
        portfolio.setName(portName);
        return portfolio;
    }

    public List<GroupedExpressions> processPortfolio(DemandRequest apiRequest, Portfolio portfolio,
            PageRequest pageRequest)
    {
        if (CollectionUtils.isEmpty(portfolio.getShortIDList()))
        {
            return new ArrayList<>();
        }
        List<Long> shortIDs = portfolio.getShortIDList();
        /*
         * Sort the IDs so that order of CIDs always remain same in SQL query if
         * portfolio items not changed (for caching)
         */
        Collections.sort(shortIDs);

        String countryGroup = null;
        List<String> markets = null;
        //this is the entity type of a selected portfolio
        Entity entity = Objects.isNull(portfolio.getEntity())
            || portfolio.getEntity() == Entity.SHOW ? Entity.TV_SERIES : portfolio.getEntity();
        if (CollectionUtils.isNotEmpty(apiRequest.getListMarkets()))
        {
            countryGroup = StringUtils.join(apiRequest.getListMarkets(), APIConstants.DELIM_COMMA);
            markets = apiRequest.getListMarkets();
        }
        else
        {
            countryGroup = apiRequest.getDataQuery().getTv360Regions().get(0).getName();
            markets = apiRequest.getDataQuery().getTv360Regions().get(0).getCountryList();
        }

        List<GroupedExpressions> rawStats = null;

        List<GroupedExpressions> results = null;

        if (CollectionUtils.isNotEmpty(shortIDs))
        {
            List<Date> dateRangeList = apiRequest.getDateRangeList();
            if (Interval.OVERALL == apiRequest.getIntervalEnum())
            {
                results = overallPortfolioDemand(apiRequest, markets, shortIDs, dateRangeList, countryGroup,
                        pageRequest, entity);
            }
            else if (apiRequest.isCustomStartOfInterval())
            {
                results = demandDataRepo.intervalDemandAsTimeSeriesWithStartOfWeek(
                        DayOfWeek.fromValue(apiRequest.getStartOfInterval()), dateRangeList, markets, shortIDs,
                        pageRequest, entity, apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
            }
            else
            {
                results = demandDataRepo.intervalDemandAsTimeSeries(apiRequest.getIntervalEnum(), dateRangeList,
                        markets, shortIDs, pageRequest,entity, apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
            }
        }

        rawStats = new ArrayList<>();

        for (GroupedExpressions gE : results)
        {
            GroupedExpressions clonedExpression = gE.clone();
            clonedExpression.setPortfolio(portfolio);
            if (StringUtils.isEmpty(gE.getCountry()))
                clonedExpression.setCountry(countryGroup);
            if (apiRequest.getDataQuery().isMarketShare())
            {
                // market share will take the sum of demand of all short ids
                // instead of average
                gE.setDexpercapita(gE.getDexpercapita() * shortIDs.size());
                gE.setPeak_dexpercapita(gE.getPeak_dexpercapita() * shortIDs.size());
                gE.setDex(gE.getDex() * shortIDs.size());
                gE.setPeak_dex(gE.getPeak_dex() * shortIDs.size());
            }
            rawStats.add(clonedExpression);
        }

        return rawStats;

    }

    public List<GroupedExpressions> overallPortfolioDemand(DemandRequest apiRequest, List<String> markets,
            List<Long> shortIDs, List<Date> dateRange, String countryGroup, PageRequest pageRequest, Entity entity)
    {

        DataQuery dataQuery = apiRequest.getDataQuery();

        if (apiRequest.getPrecomputed() != null)
        {
            List<GroupedExpressions> r = null;

            if (dataQuery.isMarketBreakdown())
            {
                // worldview heatmap
                r = demandDataRepo.precomputedDemandByCountryAsOverall(apiRequest.getPrecomputed(),
                        dataQuery.getDateTo().toDate(), dataQuery.getPeriod(), markets, shortIDs, pageRequest, entity,
                        apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
            }
            else
            {
                // markets average overall or weighted average
                r = demandDataRepo.precomputedDemandAsOverall(apiRequest.getPrecomputed(),
                        dataQuery.getDateTo().toDate(), dataQuery.getPeriod(), markets, shortIDs, pageRequest, entity,
                        apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());

            }
            if (CollectionUtils.isNotEmpty(r))
                return r;
        }
        List<GroupedExpressionsEx> result = new ArrayList<>();
        List<DateRangePartition> dateRangePartitions = demandService.partitionDateRangesByInterval(
                dataQuery.getDateFrom().toDate(), dataQuery.getDateTo().toDate());

        try
        {
            List<CompletableFuture<List<GroupedExpressionsEx>>> futuresList = new ArrayList<>();

            for (DateRangePartition partitionDateRange : dateRangePartitions)
            {

                CompletableFuture<List<GroupedExpressionsEx>> future = CompletableFuture.supplyAsync(() -> {
                    List<Date> dateRangeList = partitionDateRange.toDateRangeList();
                    Interval interval = partitionDateRange.getIntervalEnum();
                    List<GroupedExpressions> r = null;

                    if (dataQuery.isMarketBreakdown())
                    {
                        // demand grouped by countries or country
                        // group
                        r = demandDataRepo.intervalDemandByCountryAsOverall(interval, dateRangeList, markets, shortIDs,
                                pageRequest, entity,  apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
                    }
                    else
                    {
                        r = demandDataRepo.intervalDemandAsOverall(interval, dateRangeList, markets, shortIDs,
                                pageRequest,entity, apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
                    }
                    return r != null ? r.stream().map(ex -> {
                        GroupedExpressionsEx ge = new GroupedExpressionsEx(ex, dateRangeList);
                        if (!dataQuery.isMarketBreakdown())
                            ge.setCountry(countryGroup);
                        return ge;
                    }).collect(Collectors.toList()) : new ArrayList<>();
                }, Executors.newCachedThreadPool());
                futuresList.add(future);
            }
            // execute the parallel completable
            CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                    futuresList.toArray(new CompletableFuture[futuresList.size()]));
            CompletableFuture<List<List<GroupedExpressionsEx>>> allCompletableFuture = allFutures.thenApply(
                    future -> futuresList.stream().map(completableFuture -> completableFuture.join())
                            .collect(Collectors.toList()));
            allCompletableFuture.get().stream().forEach(result::addAll);
        }
        catch (Exception ex)
        {
            logger.error("fail to call overallPortfolioDemand", ex);
        }
        return demandService.combineGroupExpressionsByInterval(result);
    }

    public GroupedExpressions getOtherDemand(List<GroupedExpressions> otherResults, Portfolio otherPortfolios)
    {
        double dexpercapita = 0;
        double peakDexpercapita = 0;
        double dex = 0;
        double peakDex = 0;
        String country = null;
        Date date = null;
        Long shortId = null;
        for (GroupedExpressions gr : otherResults)
        {
            dexpercapita += gr.getDexpercapita();
            peakDexpercapita += gr.getPeak_dexpercapita();
            dex += gr.getDex();
            peakDex += gr.getPeak_dex();
            country = gr.getCountry();
            date = gr.getDate();
            shortId = (long) 0;
        }

        GroupedExpressions otherPortfolioGE = new GroupedExpressions(date, shortId, country, dexpercapita,
                peakDexpercapita, dex, peakDex, 0, 0, 0, 0, 1);
        otherPortfolioGE.setPortfolio(otherPortfolios);
        return otherPortfolioGE;
    }
    public List<Portfolio> fetchAllCustomPortfolios(DataQuery dataQuery, InternalUser callUser)
    {
        List<Portfolio> accountPortfolios = portfolioRepo.findAccountPortfolios(callUser.getAccount().getIdAccount());
        if (CollectionUtils.isNotEmpty(accountPortfolios))
        {
            List<Integer> portfolioIDs = accountPortfolios.stream().map(p -> {
                p.setStringID(p.getIdPortfolio().toString());
                return p.getIdPortfolio();
            }).collect(Collectors.toList());

            dataQuery.setPortfolioIDs(portfolioIDs);
            fetchCustomPortfolios(dataQuery, accountPortfolios);
        }
        return accountPortfolios;
    }

    public List<Portfolio> fetchAllGenrePortfolios(DataQuery dataQuery, InternalUser callUser)
    {
        // these portfolios list not initialised the associated data items yet
        // */
        List<Portfolio> portfolios = portfolioRepo.findPortfoliosByAccountNType(APIConstants.CUSTOMER_READY_ACCOUNT,
                PortfolioType.genre_tags);

        DataQuery query = new DataQuery();
        query.setSimpleFilters(dataQuery.hasSimpleFilters() ?
                new HashMap<String, String>(dataQuery.getSimpleFilters()) :
                new HashMap<String, String>());
        query.setPortfolio(portfolios.stream().map(p -> p.getIdPortfolio().toString())
                .collect(Collectors.joining(APIConstants.DELIM_COMMA)));
        portfolios = fetchSelectedPortfolios(query);
        dataQuery.setPresetPortfolioIDs(query.getPresetPortfolioIDs());
        return portfolios;
    }

    public List<Portfolio> fetchAllPlatformPortfolios(DataQuery dataQuery, InternalUser callUser)
    {
        List<Portfolio> platformPortfolios = portfolioRepo.findPortfoliosByAccountNType(
                APIConstants.CUSTOMER_READY_ACCOUNT, PortfolioType.platform);
        List<Portfolio> networkPortfolios = portfolioRepo.findPortfoliosByAccountNType(
                APIConstants.CUSTOMER_READY_ACCOUNT, PortfolioType.network);
        Map<String, Portfolio> networkPortfoliosByPlatformName = networkPortfolios.stream()
                .collect(Collectors.toMap(p -> p.getFilterValue(), p -> p));
        DataQuery query = new DataQuery();
        query.setSimpleFilters(dataQuery.hasSimpleFilters() ? new HashMap<>(dataQuery.getSimpleFilters()) : new HashMap<>());
        query.setPortfolio(platformPortfolios.stream().map(platformPort -> {
            Portfolio networkPort = networkPortfoliosByPlatformName.get(platformPort.getFilterValue());
            return networkPort != null ?
                    String.format("%s" + APIConstants.DELIM_OR_OPERATOR + "%s", networkPort.getIdPortfolio().toString(),
                            platformPort.getIdPortfolio().toString()) :
                    platformPort.getIdPortfolio().toString();
        }).collect(Collectors.joining(APIConstants.DELIM_COMMA)));
        platformPortfolios = fetchSelectedPortfolios(query);
        dataQuery.setPresetPortfolioIDs(query.getPresetPortfolioIDs());
        return platformPortfolios;
    }

}
