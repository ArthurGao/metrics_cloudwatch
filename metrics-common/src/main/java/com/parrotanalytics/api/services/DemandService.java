package com.parrotanalytics.api.services;

import com.amazonaws.services.dynamodbv2.model.ConditionalOperator;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.parrotanalytics.api.apidb_model.Account;
import com.parrotanalytics.api.apidb_model.BenchmarkDemand;
import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.RangeKeyLookup;
import com.parrotanalytics.api.apidb_model.TV360Region;
import com.parrotanalytics.api.apidb_model.comparators.GroupedExpressionsComparator;
import com.parrotanalytics.api.apidb_model.nonmanaged.CountryShortIdTuple;
import com.parrotanalytics.api.apidb_model.nonmanaged.DateRangePartition;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressionsEx;
import com.parrotanalytics.api.apidb_model.nonmanaged.MovieESFilter;
import com.parrotanalytics.api.apidb_model.nonmanaged.ShowESFilter;
import com.parrotanalytics.api.apidb_model.nonmanaged.TalentESFilter;
import com.parrotanalytics.api.commons.constants.DayOfWeek;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.PreComputed;
import com.parrotanalytics.api.commons.constants.ReleaseDateFilterType;
import com.parrotanalytics.api.commons.constants.TimePeriod;
import com.parrotanalytics.api.config.APIConfig;
import com.parrotanalytics.api.data.repo.api.AccountRepository;
import com.parrotanalytics.api.data.repo.api.ContextMetricRepository;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.data.repo.api.LabelRepository;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.data.repo.api.ProductSpecsRepository;
import com.parrotanalytics.api.data.repo.api.RegionRepository;
import com.parrotanalytics.api.data.repo.api.SubscriptionsRepository;
import com.parrotanalytics.api.data.repo.api.TitlesRepository;
import com.parrotanalytics.api.data.repo.api.cache.MetadataCache;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;
import com.parrotanalytics.api.request.demand.SortInfo;
import com.parrotanalytics.api.request.metricrank.MetricRankRequest;
import com.parrotanalytics.api.request.util.FiltersBuilder;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.commons.config.ConfigFactory;
import com.parrotanalytics.commons.config.service.ConfigurationService;
import com.parrotanalytics.commons.constants.App;
import com.parrotanalytics.commons.utils.QuickTimeMeasurement;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import com.parrotanalytics.commons.utils.date.DateUtils;
import com.parrotanalytics.enrichment.elasticsearch.ElasticSearchIndex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

/**
 * Service class for Demand Data Fetching
 *
 * @author sanjeev
 * @author Minh Vu
 */
@Service
public class DemandService {

  protected static Logger logger = LoggerFactory.getLogger(DemandService.class);

  private static ConfigurationService apiConfig = ConfigFactory.getConfig(App.TV360_API);

  @Autowired
  protected TitlesRepository titlesRepo;

  @Autowired
  protected MarketsRepository marketsRepo;

  @Autowired
  protected DemandRepository demandDataRepo;

  @Autowired
  protected AccountRepository accountRepo;

  @Autowired
  protected ProductSpecsRepository productSpecsRepo;

  @Autowired
  protected ContextMetricRepository contextMetricRepo;

  @Autowired
  protected SubscriptionsRepository subscriptionsRepo;

  @Autowired
  protected LabelRepository labelRepo;

  @Autowired
  protected MetadataCache metadataCache;

  @Autowired
  protected RegionRepository regionRepo;

  @Autowired
  protected TVSeasonService tvSeasonService;

  @Autowired
  protected MetadataService metadataService;

  @Autowired
  protected CatalogESService catalogESService;

  @Autowired
  protected UserService userService;

  public static List<String> validComputeKeys;

  public static Map<Integer, DayOfWeek> DAY_ISO_TO_DAY_NAME = new HashMap<>();

  static {
    DAY_ISO_TO_DAY_NAME.put(1, DayOfWeek.MON);
    DAY_ISO_TO_DAY_NAME.put(2, DayOfWeek.TUE);
    DAY_ISO_TO_DAY_NAME.put(3, DayOfWeek.WED);
    DAY_ISO_TO_DAY_NAME.put(4, DayOfWeek.THU);
    DAY_ISO_TO_DAY_NAME.put(5, DayOfWeek.FRI);
    DAY_ISO_TO_DAY_NAME.put(6, DayOfWeek.SAT);
    DAY_ISO_TO_DAY_NAME.put(7, DayOfWeek.SUN);
  }

  @PostConstruct
  public void init() {
    /*
     * Common Data Initialization
     */
    logger.info("[DemandController] Initializing Valid Compute Keys...");

    validComputeKeys = demandDataRepo.findValidRangeKeys();

  }

  public List<GroupedExpressions> combineGroupExpressionsByInterval(
      List<GroupedExpressionsEx> result) {
    if (CollectionUtils.isEmpty(result)) {
      return new ArrayList<>();
    }

    Date date = result.stream().map(ex -> ex.getDate()).max(Comparator.naturalOrder()).get();

    // group expressions by market,short_id tuple
    Map<CountryShortIdTuple, List<GroupedExpressionsEx>> expressionsByMarketShortIdGroup = result.stream()
        .collect(Collectors.groupingBy(
            drEx -> new CountryShortIdTuple(drEx.getCountry(), drEx.getShort_id())));

    // combined of group expressions grouped by title, sort by api request
    return expressionsByMarketShortIdGroup.entrySet().stream()
        .map(entry -> createCombinedGroupedExpressions(date, entry.getKey().country,
            entry.getKey().shortId,
            entry.getValue())).collect(Collectors.toList());

  }

  public GroupedExpressions createCombinedGroupedExpressions(Date date, String country,
      Long shortId,
      List<GroupedExpressionsEx> value) {
    double dexSum = 0.0;
    double dexpercapitaSum = 0.0;
    int sumDays = 0;
    int overallRank = 0;
    int rankByPeak = 0;
    int filterRank = 0;
    int bestRank = 0;
    for (GroupedExpressionsEx drEx : value) {

      int numDays = drEx.getNum_days();
      Double dex = drEx.getDex();
      Double dexpercapita = drEx.getDexpercapita();
      dexSum += dex * numDays;
      dexpercapitaSum += dexpercapita * numDays;
      sumDays += numDays;
      overallRank = drEx.getOverall_rank();
      filterRank = drEx.getFilter_rank();
      rankByPeak = drEx.getRank_by_peak();
      bestRank = drEx.getBest_rank();
    }

    // peak is max of overall peak
    double peak_dex = value.stream().mapToDouble(drEx -> drEx.getPeak_dex()).max().orElse(0.0);
    double peak_dexpercapita = value.stream().mapToDouble(drEx -> drEx.getPeak_dexpercapita()).max()
        .orElse(0.0);
    // create new group expressions
    return new GroupedExpressions(date, shortId, country, dexpercapitaSum / sumDays,
        peak_dexpercapita,
        dexSum / sumDays, peak_dex, overallRank, filterRank, rankByPeak, bestRank, sumDays);
  }

  public List<String> getFilteredGenres(DataQuery dataQuery, Long shortID) {

    List<String> allGenres = new ArrayList<>();

    if (dataQuery.hasSimpleFilters()) {
      String genreStr = dataQuery.getSimpleFilters().get(APIConstants.GENRE_TAGS);
      if (StringUtils.isNotEmpty(genreStr)) {
        allGenres.addAll(
            Stream.of(genreStr.split(APIConstants.DELIM_COMMA)).collect(Collectors.toList()));
      }
    } else {
      allGenres = contextMetricRepo.getGenreTagsForTitle(shortID);
    }
    return allGenres;
  }

  /**
   * parse content string from request and convert to list of content ids
   *
   * @param dataQuery
   * @param apiRequest
   * @param callUser
   * @throws APIException
   */
  public void parseContent(DataQuery dataQuery, DemandRequest apiRequest, InternalUser callUser) {

    if (dataQuery.getContent() != null) {
      /* Type A : SUBSCRIPTION SHOWS */
      if (dataQuery.getContent().equalsIgnoreCase(APIConstants.SUBSCRIPTION_SHOWS)) {
        apiRequest.setSubscriptionShowsRequest(Boolean.TRUE);

        if (apiRequest.getPage() == null) {
          apiRequest.setPage(1);
        }

        if (apiRequest.getSize() == null) {
          apiRequest.setSize(10);
        }
      }
      /* Type B : ALL SHOWS */
      else if (dataQuery.getContent().contains(APIConstants.ALL_SHOWS)) {
        apiRequest.setAllShowsRequest(Boolean.TRUE);
        apiRequest.setSelectedShowsRequest(Boolean.FALSE);

      } else if (dataQuery.getContent().contains(APIConstants.ALL_TALENTS)) {
        apiRequest.setAllTalentsRequest(Boolean.TRUE);
        apiRequest.setSelectedShowsRequest(Boolean.FALSE);

      } else if (dataQuery.getContent().contains(APIConstants.ALL_MOVIES)) {
        apiRequest.setAllMoviesRequest(Boolean.TRUE);
        apiRequest.setSelectedShowsRequest(Boolean.FALSE);

      }
      /* Type C : SELECTED SHOWS from Client */
      else {
        String[] contentGUIDs = dataQuery.getContent().split(APIConstants.DELIM_COMMA);

        if (contentGUIDs != null && contentGUIDs.length >= 1) {
          apiRequest.setSelectedShowsRequest(Boolean.TRUE);

          List<String> shortIDsList = Arrays.asList(contentGUIDs);
          Collections.sort(shortIDsList);
          apiRequest.setListContentIDs(shortIDsList);
        }

      }
    }
  }

  /**
   * parse market string from request and convert to list of markets
   *
   * @param dataQuery
   * @param apiRequest
   * @param callUser
   * @throws APIException
   */
  public void parseMarket(DataQuery dataQuery, DemandRequest apiRequest, InternalUser callUser)
      throws APIException {
    if (dataQuery.getMarketStr() == null) {
      return;
    }

    /* Type C: ALL SHOWS */
    if (dataQuery.getMarketStr().equalsIgnoreCase(APIConstants.SUBSCRIPTION_MARKETS)) {
      apiRequest.setSubscriptionMarketsRequest(Boolean.TRUE);
      apiRequest.setListMarkets(
          subscriptionsRepo.getMarketsISOCodesByAccount(apiRequest.getApiAccountId(),
              dataQuery.getEntityEnum()));

      apiRequest.setPage(apiRequest.getPage() == null ? 1 : apiRequest.getPage());
      apiRequest.setSize(apiRequest.getSize() == null ? 10 : apiRequest.getSize());
    } else if (dataQuery.getMarketStr().equalsIgnoreCase(APIConstants.ALL_MARKETS)) {
      apiRequest.setAllMarketsRequest(Boolean.TRUE);
      apiRequest.setListMarkets(
          subscriptionsRepo.getMarketsISOCodesByAccount(APIConstants.CUSTOMER_READY_ACCOUNT,
              dataQuery.getEntityEnum()));

      apiRequest.setPage(apiRequest.getPage() == null ? 1 : apiRequest.getPage());
      apiRequest.setSize(apiRequest.getSize() == null ? 10 : apiRequest.getSize());
    } else if (dataQuery.getMarketStr().startsWith("-")) {
      List<String> parsedMarkets = subscriptionsRepo.getMarketsISOCodesByAccount(
          APIConstants.CUSTOMER_READY_ACCOUNT, dataQuery.getEntityEnum());
      apiRequest.setSelectedMarketsRequest(Boolean.TRUE);
      List<String> excludedMarkets = DemandHelper.parseMarkets(
          dataQuery.getMarketStr().substring(1));
      parsedMarkets.removeAll(excludedMarkets);
      apiRequest.setListMarkets(parsedMarkets);
    } else {
      /* Type C: SELECTED Markets */
      apiRequest.setSelectedMarketsRequest(Boolean.TRUE);
      List<String> parsedMarkets = DemandHelper.parseMarkets(dataQuery.getMarketStr());
      apiRequest.setListMarkets(parsedMarkets);
    }
  }

  public void parseRegions(DataQuery dataQuery, DemandRequest apiRequest, InternalUser callUser) {
    if (StringUtils.isNotEmpty(dataQuery.getRegions())) {
      apiRequest.setSelectedMarketsRequest(Boolean.TRUE);
      List<Integer> regionIDsList = Arrays.asList(dataQuery.getRegions().split(",")).stream()
          .map(m -> Integer.parseInt(m.trim())).collect(Collectors.toList());
      dataQuery.setRegionIDsList(regionIDsList);

      List<TV360Region> tv360Regions = regionRepo.findByListIds(regionIDsList);
      dataQuery.setTv360Regions(tv360Regions);
    }
  }

  public void parseTime(DemandRequest apiRequest, DataQuery dataQuery, InternalUser callUser)
      throws APIException {
    // A: Check for Keys based Date Range
    if (dataQuery.getPeriod() != null) {
      TimePeriod timePeriod = TimePeriod.fromValue(dataQuery.getPeriod());

      if (timePeriod != null) {
        dataQuery.setDateFrom(timePeriod.dateFrom());
        dataQuery.setDateTo(timePeriod.dateTo());

        if (validComputeKeys.contains(timePeriod.value())) {
          apiRequest.setPrecomputed(PreComputed.RANGE);
        }

        dateAdjustmentByLatest(dataQuery, timePeriod);
      } else {
        PreComputed preComputedPeriod = checkForPreComputedPeriod(dataQuery.getPeriod());

        if (preComputedPeriod != null) {
          apiRequest.setPrecomputed(preComputedPeriod);
        }
      }
    }
    // B: Custom Date Range
    else {
      // TODO : review if we need to set that data locked flag for custom
      // date range
      // apiRequest.setDataLocked(Boolean.TRUE);
    }

    apiRequest.setDateRangeList(
        DateUtils.toDateRangeList(dataQuery.getDateFrom(), dataQuery.getDateTo()));

  }

  protected void dateAdjustmentByLatest(DataQuery dataQuery, TimePeriod timePeriod) {
    dateAdjustmentByLatest(dataQuery, timePeriod, demandDataRepo.latestDemandDate());
  }

  protected void dateAdjustmentByLatest(DataQuery dataQuery, TimePeriod timePeriod,
      Date latestDate) {
    int daysDelta = 0;

    if (timePeriod.dateTo().isAfter(new DateTime(latestDate))) {
      daysDelta = daysDiff(new DateTime(latestDate), timePeriod.dateTo()) - 1;
      dataQuery.setDateTo(timePeriod.dateTo().minusDays(daysDelta));
    } else {
      dataQuery.setDateTo(timePeriod.dateTo());
    }

    if (daysDelta > 0) {
      dataQuery.setDateFrom(timePeriod.dateFrom().minusDays(daysDelta));
    } else {
      dataQuery.setDateFrom(timePeriod.dateFrom());
    }
  }

  protected Integer daysDiff(DateTime start, DateTime end) {
    return CommonsDateUtil.daysInBetween(start, end);
  }

  protected PreComputed checkForPreComputedPeriod(String period) {
    if (validComputeKeys.contains(period)) {
      if (period.startsWith("w")) {
        return PreComputed.WEEKLY;
      } else if (StringUtils.startsWithAny(period,
          new String[]{"jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov",
              "dec"
          })) {
        return PreComputed.MONTHLY;
      } else if (StringUtils.startsWithAny(period, new String[]{"q1", "q2", "q3", "q4"
      })) {
        return PreComputed.QUARTERLY;
      } else if (DateUtils.isValidDate(period + "-01-01")) {
        return PreComputed.YEARLY;
      }
    }
    return null;
  }

  /**
   * execute live query on pre-computed expressions table based on query parameters
   *
   * @param apiRequest
   * @param pageRequest
   * @return
   */
  public Page<GroupedExpressions> executeDemandQueryByRangeKey(DemandRequest apiRequest,
      PageRequest pageRequest) {
    List<GroupedExpressions> r = null;

    DataQuery dataQuery = apiRequest.getQuery().get(0);

    String rangeKey = dataQuery.getPeriod();
    Date dateTo =
        dataQuery.getDateTo() != null ? dataQuery.getDateTo().toDate()
            : demandDataRepo.latestDemandDate();

    if (dataQuery.isMarketShare()) {
      r = demandDataRepo.intervalDemandMarketShareAsOverall(apiRequest, pageRequest,
          apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    } else if (dataQuery.isMarketaverage()) {

      r = demandDataRepo.precomputedDemandByShortIdAsTimeseries(apiRequest.getPrecomputed(), dateTo,
          rangeKey, dataQuery.getMarketsList(), dataQuery.getShortIDsList(), pageRequest,
          dataQuery.getEntityEnum(),
          apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    } else if (dataQuery.isWeightedaverage()) {
      r = new ArrayList<>();
    } else {
      r = demandDataRepo.precomputedDemandByShortIdCountryAsTimeseries(apiRequest.getPrecomputed(),
          dateTo,
          rangeKey, dataQuery.getMarketsList(), dataQuery.getShortIDsList(), pageRequest,
          dataQuery.getEntityEnum(), apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    }
    if (dataQuery.isShowFilterRanks()) {
      r = sortFilterRank(r, apiRequest.getSort(), apiRequest.getSortBy(), apiRequest, pageRequest);
    }
    return new PageImpl<>(r);
  }

  public List<GroupedExpressions> timeseriesDemandByShortIdDate(DemandRequest apiRequest,
      PageRequest pageRequest) {
    List<GroupedExpressions> result = null;

    DataQuery dataQuery = apiRequest.getDataQuery();
    List<Date> dateRanges = dataQuery.getDateRangeList();
    List<Long> shortIDsList = dataQuery.getShortIDsList();
    List<String> marketsList = dataQuery.getMarketsList();
    Interval interval = Interval.fromValue(apiRequest.getInterval());

    if (apiRequest.isCustomStartOfInterval()) {
      result = demandDataRepo.intervalDemandByShortIdAsTimeseriesWithStartOfWeek(
          DayOfWeek.fromValue(apiRequest.getStartOfInterval()), dateRanges, marketsList,
          shortIDsList,
          pageRequest, dataQuery.getEntityEnum(), apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    } else if (Interval.CUSTOM == interval) {
      DayOfWeek dayOfWeek = getDayOfWeek(Optional.ofNullable(apiRequest.getIntervalStartDate()));
      Integer customIntervalRange = apiRequest.getCustomIntervalRange();

      result = demandDataRepo.intervalDemandByShortIdAsTimeseriesWithCustomInterval(interval,
          dayOfWeek, Optional.ofNullable(customIntervalRange),
          dateRanges, marketsList,
          shortIDsList,
          pageRequest, dataQuery.getEntityEnum(), apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    } else {
      result = demandDataRepo.intervalDemandByShortIdAsTimeseries(interval, dateRanges, marketsList,
          shortIDsList,
          pageRequest, dataQuery.getEntityEnum(), apiRequest.getBenchmarkDE(),
          apiRequest.getMinimumMultiplier());
    }
    return result;
  }

  private DayOfWeek getDayOfWeek(Optional<DateTime> intervalStartDate) {
    try {
      if (intervalStartDate.isPresent()) {
        return DAY_ISO_TO_DAY_NAME.get(intervalStartDate.get().getDayOfWeek());
      }
    } catch (Exception ex) {
      logger.warn("Invalid start date specified {}", intervalStartDate);
    }

    return DayOfWeek.MON;

  }

  public List<GroupedExpressions> showsDemandByOVERALLIntervalAVERAGE(List<Long> contentShortIDs,
      List<String> markets, PageRequest pageRequest, List<Date> dateRangeList, Interval interval) {
    return showsDemandByOVERALLIntervalAVERAGE(contentShortIDs, markets, pageRequest, dateRangeList,
        interval,
        Entity.TV_SERIES);
  }

  public List<GroupedExpressions> showsDemandByOVERALLIntervalAVERAGE(List<Long> contentShortIDs,
      List<String> markets, PageRequest pageRequest, List<Date> dateRangeList, Interval interval,
      Entity entity) {
    ListeningExecutorService executor = MoreExecutors.listeningDecorator(
        Executors.newCachedThreadPool());

    List<GroupedExpressions> result = new ArrayList<>();
    try {
      List<ListenableFuture<List<GroupedExpressions>>> futures = new ArrayList<>();

      for (String market : markets) {
        ListenableFuture<List<GroupedExpressions>> future = executor.submit(
            new Callable<List<GroupedExpressions>>() {

              @Override
              public List<GroupedExpressions> call() throws Exception {

                return demandDataRepo.intervalDemandByShortIdCountryAsOverall(interval,
                    dateRangeList,
                    Arrays.asList(market), contentShortIDs, pageRequest, entity, null, null);
              }

            });
        futures.add(future);
      }
      ListenableFuture<List<List<GroupedExpressions>>> combinedFutures = Futures.allAsList(futures);
      combinedFutures.get().stream().forEach(r -> {
        result.addAll(r);
      });
    } catch (Exception e) {
      logger.error("failed to fetch shows demand by overall interval AVERAGE", e);
    }
    return result;

  }

  /**
   * execute live query on expressions tables based on query parameters
   *
   * @param apiRequest
   * @param pageRequest
   * @return
   */
  public Page<GroupedExpressions> executeDemandQueryByDateRange(DemandRequest apiRequest,
      PageRequest pageRequest) {
    List<GroupedExpressions> result = null;

    Interval interval = Interval.fromValue(apiRequest.getInterval());

    if (interval == Interval.OVERALL) {
      // overall
      result = showsOverallDemandByDateRange(apiRequest, pageRequest);
    } else {
      // time series
      result = showsTimeseriesDemandByDateRange(apiRequest, pageRequest);
    }

    return new PageImpl<>(result);
  }

  protected List<GroupedExpressions> showsTimeseriesDemandByDateRange(DemandRequest apiRequest,
      PageRequest pageRequest) {
    DataQuery dataQuery = apiRequest.getDataQuery();
    List<GroupedExpressions> result = null;

    if (dataQuery.isMarketaverage()) {
      result = timeseriesDemandByShortIdDate(apiRequest, pageRequest);
    } else if (dataQuery.isWeightedaverage()) {
      result = demandDataRepo.liveWeightedDemandByShortIdAsTimeseries(apiRequest, pageRequest);
    } else {
      // timeseries breakdown by short id, country
      result = timeseriesDemandByShortIdCountryDate(apiRequest, pageRequest);
    }
    // custom start of interval are derived from daily, hence overall_rank
    // is always 0
    // and marketShareTimeseries only works if overall_rank exist
    if (CollectionUtils.isNotEmpty(result) && dataQuery.isMarketShare()
        && !apiRequest.isCustomStartOfInterval()) {
      result.addAll(demandDataRepo.marketShareTimeseries(apiRequest, pageRequest));
    }
    return result;
  }

  public List<GroupedExpressions> showsOverallDemandByDateRange(DemandRequest apiRequest,
      PageRequest pageRequest) {

    List<Date> dateRange = apiRequest.getDataQuery().getDateRangeList();

    List<DateRangePartition> dateRangePartitions = partitionDateRangesByInterval(dateRange.get(0),
        dateRange.get(dateRange.size() - 1));

    ListeningExecutorService executor = MoreExecutors.listeningDecorator(
        Executors.newCachedThreadPool());

    List<GroupedExpressionsEx> result = new ArrayList<>();
    try {
      List<ListenableFuture<List<GroupedExpressionsEx>>> futures = new ArrayList<>();

      // we would like to have full catalog query before sort and filter
      // by the end of this method
      PageRequest catalogPage = PageRequest.of(0, Integer.MAX_VALUE, pageRequest.getSort());
      DataQuery dataQuery = apiRequest.getDataQuery();
      List<Long> shortIDsList = dataQuery.getShortIDsList();
      List<Long> fullCatShortIDsList = getFullEntityCatalogShortIds(dataQuery.getEntityEnum());

      for (DateRangePartition dateRangePartition : dateRangePartitions) {
        List<Date> dateRangeList = dateRangePartition.toDateRangeList();
        DemandRequest clonedRequest = apiRequest.clone();
        clonedRequest.setInterval(dateRangePartition.getInterval());
        clonedRequest.setDateRangeList(dateRangeList);
        clonedRequest.getDataQuery().setDateFrom(dateRangePartition.getStart());
        clonedRequest.getDataQuery().setDateTo(dateRangePartition.getEnd());
        ListenableFuture<List<GroupedExpressionsEx>> future = executor.submit(
            new Callable<List<GroupedExpressionsEx>>() {

              @Override
              public List<GroupedExpressionsEx> call() throws Exception {
                QuickTimeMeasurement qtm = new QuickTimeMeasurement(DemandService.class);
                Interval partitionInterval = dateRangePartition.getIntervalEnum();
                List<GroupedExpressions> r = null;

                DataQuery dataQuery = clonedRequest.getDataQuery();
                List<String> marketsList = dataQuery.getMarketsList();
                if (dataQuery.isMarketaverage()) {
                  r = demandDataRepo.intervalDemandByShortIdAsOverall(partitionInterval,
                      dateRangeList, marketsList, fullCatShortIDsList, catalogPage,
                      apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());

                } else if (dataQuery.isWeightedaverage()
                    && apiRequest.isMultipleMarketsRegionsRequest()) {

                  // already cloned api request at begining of
                  // each iteration
                  r = demandDataRepo.liveWeightedDemandByShortIdAsOverall(clonedRequest,
                      catalogPage,
                      apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());

                } else if (dataQuery.isMarketShare()) {
                  r = demandDataRepo.intervalDemandMarketShareAsOverall(clonedRequest, catalogPage,
                      apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
                } else {
                  // this is the compare show
                  if (!apiRequest.isMultipleMarketsRegionsRequest()) {
                    r = demandDataRepo.intervalDemandByShortIdCountryAsOverall(
                        clonedRequest.getIntervalEnum(), dateRangeList, marketsList,
                        fullCatShortIDsList, catalogPage, dataQuery.getEntityEnum(),
                        apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
                  } else {
                    r = showsDemandByOVERALLIntervalAVERAGE(fullCatShortIDsList, marketsList,
                        catalogPage,
                        dateRangeList, partitionInterval, dataQuery.getEntityEnum());
                  }

                }
                logger.debug("Call showsOverallDemandByDateRange {} , Finished in {} ms",
                    dateRangePartition, qtm.timeTakenMillis());
                return r.stream().map(ex -> new GroupedExpressionsEx(ex, dateRangeList))
                    .collect(Collectors.toList());
              }
            });
        futures.add(future);
      }

      ListenableFuture<List<List<GroupedExpressionsEx>>> combinedFutures = Futures.allAsList(
          futures);
      combinedFutures.get().stream().forEach(r -> {
        result.addAll(r);
      });

    } catch (Exception e) {
      e.printStackTrace();
      logger.error("failed to fetch overall demand by date range for call {}", apiRequest);
    }

    List<GroupedExpressions> expressionsList = combineGroupExpressionsByInterval(result);

    Sort sort = pageRequest.getSort();
    String sortBy = DemandHelper.prepareSortBy(apiRequest);

    /*
     * Ignore sorting as this will be handled after in memory percent change
     * calculation
     */
    if (!apiRequest.getSortBy().contains("demand_change")) {
      List<Long> shortIDsList = apiRequest.getDataQuery().getShortIDsList();
      if (apiRequest.getDataQuery().isMarketShare()) {
        shortIDsList.add((long) -1);
      }
      /* we sort overall rank and filter rank then paginated the result
      from full catalog list heree
       */
      expressionsList = sortOverallRankNPeakRankNPagination(expressionsList, sort, sortBy,
          apiRequest, shortIDsList,
          pageRequest);

    }

    return expressionsList;

  }

  protected List<GroupedExpressions> timeseriesDemandByShortIdCountryDate(DemandRequest apiRequest,
      PageRequest pageRequest) {

    ListeningExecutorService executor = MoreExecutors.listeningDecorator(
        Executors.newCachedThreadPool());
    List<GroupedExpressions> result = new ArrayList<>();
    DataQuery dataQuery = apiRequest.getDataQuery();
    List<Date> dateRanges = dataQuery.getDateRangeList();
    List<Long> shortIDsList = dataQuery.getShortIDsList();
    List<String> marketsList = dataQuery.getMarketsList();
    Interval interval = Interval.fromValue(apiRequest.getInterval());
    DayOfWeek startOfInterval = DayOfWeek.fromValue(apiRequest.getStartOfInterval());

    try {
      if (dataQuery.isSingleEntityRequest()) {
        if (apiRequest.isCustomStartOfInterval()) {
          result = demandDataRepo.intervalDemandByShortIdCountryAsTimeseriesWithStartOfWeek(
              startOfInterval,
              dateRanges, marketsList, shortIDsList, pageRequest, dataQuery.getEntityEnum(),
              apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
        } else if (Interval.CUSTOM == interval) {
          DayOfWeek dayOfWeek = getDayOfWeek(
              Optional.ofNullable(apiRequest.getIntervalStartDate()));
          Integer customIntervalRange = apiRequest.getCustomIntervalRange();

          result = demandDataRepo.intervalDemandByShortIdAsTimeseriesWithCustomInterval(interval,
              dayOfWeek, Optional.ofNullable(customIntervalRange),
              dateRanges, marketsList,
              shortIDsList,
              pageRequest, dataQuery.getEntityEnum(), apiRequest.getBenchmarkDE(),
              apiRequest.getMinimumMultiplier());
        } else {
          result = demandDataRepo.intervalDemandByShortIdCountryAsTimeseries(interval, dateRanges,
              marketsList, shortIDsList, pageRequest, dataQuery.getEntityEnum(),
              apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
        }
      } else {
        List<ListenableFuture<List<GroupedExpressions>>> futures = new ArrayList<>();

        for (String market : marketsList) {
          ListenableFuture<List<GroupedExpressions>> future = executor.submit(
              new Callable<List<GroupedExpressions>>() {
                @Override
                public List<GroupedExpressions> call() throws Exception {
                  Entity entity = Entity.fromValue(dataQuery.getEntity());
                  if (apiRequest.isCustomStartOfInterval()) {
                    return demandDataRepo.intervalDemandByShortIdCountryAsTimeseriesWithStartOfWeek(
                        startOfInterval, dateRanges, Arrays.asList(market), shortIDsList,
                        pageRequest, entity, apiRequest.getBenchmarkDE(),
                        apiRequest.getMinimumMultiplier());
                  } else {
                    return demandDataRepo.intervalDemandByShortIdCountryAsTimeseries(interval,
                        dateRanges, Arrays.asList(market), shortIDsList, pageRequest, entity,
                        apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
                  }
                }

              });

          futures.add(future);
        }
        ListenableFuture<List<List<GroupedExpressions>>> combinedFutures = Futures.allAsList(
            futures);
        for (List<GroupedExpressions> gr : combinedFutures.get()) {
          result.addAll(gr);
        }

      }

    } catch (Exception e) {
      logger.error("Failed to fetch shows demand by interval average:", e);
    }

    return result;
  }

  public List<Long> injectMarketShareShortIDs(DataQuery dataQuery, InternalUser callUser)
      throws APIException {
    List<Long> otherShortIDs = null;

    if (APIConstants.SUBSCRIPTION_SHOWS.equalsIgnoreCase(dataQuery.getMarketShare())
        || APIConstants.ALL_SHOWS.equalsIgnoreCase(dataQuery.getMarketShare())) {
      otherShortIDs = metadataCache.accountTitleShortIds(callUser.getAccount());
    } else {
      List<String> marketShareIDs = StringUtils.isNotEmpty(dataQuery.getMarketShare()) ?
          Arrays.asList(dataQuery.getMarketShare().split(APIConstants.DELIM_COMMA)) :
          null;

      otherShortIDs = extractIDs(marketShareIDs);
    }
    if (dataQuery.hasSimpleFilters()) {
      otherShortIDs = filterTitles(dataQuery.getEntityEnum(), dataQuery.getFilters(),
          otherShortIDs);
    }

    if (CollectionUtils.isNotEmpty(otherShortIDs)) {
      otherShortIDs = otherShortIDs.stream()
          .filter(shortId -> !dataQuery.getShortIDsList().contains(shortId))
          .collect(Collectors.toList());
    }

    return otherShortIDs;

  }

  public Page<GroupedExpressions> fetchDemandData(DemandRequest apiRequest, PageRequest pageRequest)
      throws APIException {

    Page<GroupedExpressions> pageResult = null;

    DataQuery dataQuery = apiRequest.getDataQuery();

    if (StringUtils.isNotEmpty(dataQuery.getMarketShare())) {
      List<Long> othersShortIDs = injectMarketShareShortIDs(dataQuery, apiRequest.getCallUser());

      dataQuery.setOthersShortIDsList(othersShortIDs);
    }

    if (DemandHelper.isWorldwideMarket(dataQuery.getMarketStr())) {
      dataQuery.setListMarkets(Arrays.asList(APIConstants.WORLDWIDE_CODE));
    }
    if (CollectionUtils.size(dataQuery.getRegionIDsList()) > 0) {
      Iterable<TV360Region> regions = regionRepo.findAllById(dataQuery.getRegionIDsList());

      dataQuery.mergeRegionsToMarketsList(regions);

    }
    if (CollectionUtils.isEmpty(apiRequest.getListMarkets())) {
      List<String> subscriptionMarkets = marketsRepo.getAccountMarkets(apiRequest.getApiAccountId(),
              dataQuery.getEntityEnum())
          .stream()
          .map(m -> m.getIso()).collect(Collectors.toList());
      dataQuery.setListMarkets(subscriptionMarkets);
    }

    Interval interval = Interval.fromValue(apiRequest.getInterval());

    if (apiRequest.getPrecomputed() != null && interval == Interval.OVERALL) {
      pageResult = executeDemandQueryByRangeKey(apiRequest, pageRequest);
    }

    if (pageResult == null || !pageResult.hasContent()) {
      pageResult = executeDemandQueryByDateRange(apiRequest, pageRequest);
    }

    return pageResult;
  }

  public List<GroupedExpressions> findOverallHeatingUp(MetricRankRequest apiRequest,
      String metricType,
      Integer idAccount, PageRequest pageRequest) throws APIException {
    QuickTimeMeasurement qtm = new QuickTimeMeasurement(DemandService.class);

    DataQuery dataQuery = apiRequest.getQuery().get(0);
    List<Long> shortIDs = dataQuery.getShortIDsList();
    String markets = dataQuery.getMarketStr();

    DateTime dateFrom = dataQuery.getDateFrom();
    DateTime dateTo = dataQuery.getDateTo();
    if (apiRequest.getMinimumMultiplier() == null) {
      apiRequest.setMinimumMultiplier(0.0);
    }

    if (dateFrom.equals(dateTo) || TimePeriod.LATESTDAY.value().equals(dataQuery.getPeriod())) {
      // in case 1 day range or latest , dateFrom should be -1 day so we
      // can compare the demand change
      dateFrom = dateFrom.minusDays(1);
    }

    if (markets.contains(APIConstants.GLOBAL) || markets.equalsIgnoreCase(
        APIConstants.WORLDWIDE_CODE)
        || markets.equalsIgnoreCase(APIConstants.ALL_MARKETS)) {
      apiRequest.getDataQuery().setListMarkets(Arrays.asList(APIConstants.WORLDWIDE_CODE));
    }
    PageRequest demandPagable = PageRequest.of(0, Integer.MAX_VALUE,
        Sort.by(Order.desc(SortInfo.NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS)));

    try {
      DemandRequest fromDateRequest = apiRequest.clone();
      fromDateRequest.setMinimumMultiplier(null);
      fromDateRequest.setPrecomputed(null);
      fromDateRequest.getDataQuery().setPeriod(null);
      fromDateRequest.getDataQuery().setDateFrom(dateFrom);
      fromDateRequest.getDataQuery().setDateTo(dateFrom);
      fromDateRequest.setDateRangeList(DateUtils.toDateRangeList(dateFrom, dateFrom));
      fromDateRequest.setBenchmark_de(Boolean.FALSE);

      Entity entity = Entity.fromValue(dataQuery.getEntity());
      List<GroupedExpressions> fromDateDemandData = demandDataRepo.intervalDemandByShortIdCountryAsOverall(
          Interval.DAILY, fromDateRequest.getDateRangeList(), fromDateRequest.getListMarkets(),
          fromDateRequest.getDataQuery().getShortIDsList(), demandPagable, entity,
          fromDateRequest.getBenchmarkDE(), fromDateRequest.getMinimumMultiplier());

      logger.info("Method:findOverallDemandRank::fromDateDemandData, finished in {} ms",
          qtm.timeTakenMillis());

      DemandRequest toDateRequest = apiRequest.clone();
      toDateRequest.setMinimumMultiplier(apiRequest.getMinimumMultiplier());
      toDateRequest.setPrecomputed(null);
      toDateRequest.getDataQuery().setPeriod(null);
      toDateRequest.getDataQuery().setDateFrom(dateTo);
      toDateRequest.getDataQuery().setDateTo(dateTo);
      toDateRequest.setDateRangeList(DateUtils.toDateRangeList(dateTo, dateTo));
      toDateRequest.setBenchmark_de(Boolean.FALSE);
      Double benchmarkDE = demandDataRepo.benchmarkDemand(dataQuery.getMarketsList().get(0),
          dateTo.toDate(),
          dateTo.toDate(), entity);
      toDateRequest.setBenchmarkDE(benchmarkDE);

      List<GroupedExpressions> toDateDemandData = demandDataRepo.intervalDemandByShortIdCountryAsOverall(
          Interval.DAILY, toDateRequest.getDateRangeList(), toDateRequest.getListMarkets(),
          toDateRequest.getDataQuery().getShortIDsList(), demandPagable, entity,
          toDateRequest.getBenchmarkDE(), toDateRequest.getMinimumMultiplier());

      // the selected date range: either predefined or custom
      DemandRequest overallRequest = apiRequest.clone();
      overallRequest.getDataQuery().setShowFilterRanks(true);
      overallRequest.setMinimumMultiplier(null);
      List<GroupedExpressions> overallDemandData = fetchDemandData(overallRequest,
          demandPagable).stream()
          .collect(Collectors.toList());
      if (apiRequest.getMinimumMultiplier() != null) {
        shortIDs = toDateDemandData.stream().map(GroupedExpressions::getShort_id)
            .collect(Collectors.toList());
      }
      List<GroupedExpressions> thisPeriodDemandData = injectChangePercent(apiRequest, shortIDs,
          overallDemandData,
          fromDateDemandData, toDateDemandData, pageRequest);
      logger.info("Method:findOverallDemandRank::thisPeriodExpr, finished in {} ms",
          qtm.timeTakenMillis());
      return thisPeriodDemandData;
    } catch (APIException ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("Method:findOverallDemandRank, finished in {} ms", qtm.timeTakenMillis());
    return new ArrayList<>();
  }

  public List<GroupedExpressions> injectChangePercent(MetricRankRequest apiRequest,
      List<Long> shortIDs,
      List<GroupedExpressions> overallDemandData, List<GroupedExpressions> fromDateDemandData,
      List<GroupedExpressions> toDateDemandData, PageRequest pageRequest) {
    List<GroupedExpressions> result = new ArrayList<>();
    DataQuery dataQuery = apiRequest.getDataQuery();
    String market = dataQuery.getMarketStr();
    Map<Long, List<GroupedExpressions>> overallDDGroupByShortId = overallDemandData.stream()
        .collect(Collectors.groupingBy(GroupedExpressions::getShort_id));
    Map<Long, List<GroupedExpressions>> toDateDDGroupBy = toDateDemandData.stream()
        .collect(Collectors.groupingBy(GroupedExpressions::getShort_id));
    Map<Long, List<GroupedExpressions>> fromDateDDGroupBy = fromDateDemandData.stream()
        .collect(Collectors.groupingBy(GroupedExpressions::getShort_id));

    Iterator<Long> shortIDItr = shortIDs.iterator();

    while (shortIDItr.hasNext()) {
      Long shortID = shortIDItr.next();

      if (!overallDDGroupByShortId.containsKey(shortID)) {
        // should not happen
        continue;
      }
      GroupedExpressions overallGE = overallDDGroupByShortId.get(shortID).get(0);

      GroupedExpressions dateFromGE = fromDateDDGroupBy.containsKey(shortID) ?
          fromDateDDGroupBy.get(shortID).get(0) :
          new GroupedExpressions(dataQuery.getDateTo().toDate(), shortID, market, 0.0, 0.0, 0.0,
              0.0, 0, 0, 0,
              0, 1);

      GroupedExpressions dateToGE = toDateDDGroupBy.containsKey(shortID) ?
          toDateDDGroupBy.get(shortID).get(0) :
          new GroupedExpressions(dataQuery.getDateTo().toDate(), shortID, market, 0.0, 0.0, 0.0,
              0.0, 0, 0, 0,
              0, 1);

      Double changePercent = -1000.0;

      Integer changeRank = 0;

      changePercent = dateFromGE.getDexpercapita() != 0 ?
          (dateToGE.getDexpercapita() - dateFromGE.getDexpercapita()) / dateFromGE.getDexpercapita()
              * 100 :
          100.0;
      changeRank = dateFromGE.getOverall_rank() - dateToGE.getOverall_rank();

      overallGE.setRank_change(changeRank);
      overallGE.setDemand_change(changePercent);
      result.add(overallGE);
    }
    Sort sort = pageRequest.getSort();

    String sortBy = sort.iterator().next().getProperty();
    result = sortOverallRankNPeakRankNPagination(result, sort, sortBy, apiRequest, shortIDs,
        pageRequest);
    return result;
  }

  public List<DateRangePartition> partitionDateRangesByInterval(Date start, Date end) {
    String longestInterval = Interval.DAILY.value();

    if (DateUtils.isYearlyDateRange(start, end)) {
      longestInterval = Interval.YEARLY.value();
    } else if (DateUtils.isMonthlyDateRange(start, end)) {
      longestInterval = Interval.MONTHLY.value();

    } else if (DateUtils.isWeeklyDateRange(start, end)) {
      longestInterval = Interval.WEEKLY.value();
    }

    return partitionDateRangesByInterval(start, end, longestInterval);
  }

  protected List<DateRangePartition> partitionDateRangesByInterval(Date start, Date end,
      String longestInterval) {
    // this is the lowest interval, can not partition further
    if (Interval.DAILY.value().equals(longestInterval)) {
      return Arrays.asList(
          new DateRangePartition(new DateTime(start, DateTimeZone.UTC),
              new DateTime(end, DateTimeZone.UTC),
              longestInterval));
    }

    List<DateRangePartition> dateRangePartitions = new ArrayList<>();

    String intervalType = Interval.convertIntervalType(longestInterval);
    List<RangeKeyLookup> rangeKeyLookups = demandDataRepo.findRangeKeyLookupsByDates(start, end,
        intervalType);

    // in case we can not find interval start and end for given interval
    // type
    if (CollectionUtils.isEmpty(rangeKeyLookups)) {
      return partitionDateRangesByInterval(start, end, this.getLowerInterval(longestInterval));
    }
    Date minStartDate = rangeKeyLookups.get(0).getStart();
    Date maxEndDate = rangeKeyLookups.get(rangeKeyLookups.size() - 1).getEnd();

    DateTime intervalStart = new DateTime(minStartDate, DateTimeZone.UTC);
    DateTime intervalEnd = new DateTime(maxEndDate, DateTimeZone.UTC);

    boolean canPartitionHeadFurther = new DateTime(start, DateTimeZone.UTC).isBefore(intervalStart);
    if (canPartitionHeadFurther) {
      dateRangePartitions.addAll(
          partitionDateRangesByInterval(start, intervalStart.minusDays(1).toDate(),
              this.getLowerInterval(longestInterval)));
    }
    dateRangePartitions.add(new DateRangePartition(intervalStart, intervalEnd, longestInterval));
    boolean canPartitionTailFurther = new DateTime(intervalEnd, DateTimeZone.UTC).isBefore(
        new DateTime(end, DateTimeZone.UTC));
    if (canPartitionTailFurther) {
      dateRangePartitions.addAll(
          partitionDateRangesByInterval(
              new DateTime(intervalEnd, DateTimeZone.UTC).plusDays(1).toDate(), end,
              this.getLowerInterval(longestInterval)));
    }
    // remove the date range which has 0 (zero) day
    dateRangePartitions = dateRangePartitions.stream()
        .filter(dateRangePartition -> !dateRangePartition.toDateRangeList().isEmpty())
        .collect(Collectors.toList());

    return dateRangePartitions;
  }

  protected String getLowerInterval(String longestInterval) {
    if (Interval.YEARLY.value().equals(longestInterval)) {
      return Interval.MONTHLY.value();
    } else if (Interval.MONTHLY.value().equals(longestInterval)) {
      return Interval.WEEKLY.value();
    }
    return Interval.DAILY.value();
  }

  public List<GroupedExpressions> findOverallChangeRank(MetricRankRequest apiRequest,
      String metricType,
      Integer idAccount, PageRequest pageRequest) throws APIException {
    QuickTimeMeasurement qtm = new QuickTimeMeasurement(DemandService.class);

    DataQuery dataQuery = apiRequest.getQuery().get(0);

    /* dates list */
    List<Date> selectedPeriodDateList = dataQuery.getDateRangeList();
    List<Long> shortIDs = dataQuery.getShortIDsList();

    Map<Long, List<GroupedExpressions>> prevRankExpr = new HashMap<>();
    Map<Long, List<GroupedExpressions>> latestRankExpr = new HashMap<>();

    List<GroupedExpressions> selectedPeriodExpr = new ArrayList<>();

    /*
     * Worldwide Demand Flow
     */
    if (DemandHelper.isWorldwideMarket(dataQuery.getMarketStr())) {
      apiRequest.getDataQuery().setListMarkets(Arrays.asList(APIConstants.WORLDWIDE_CODE));
    }
    PageRequest demandPagable = PageRequest.of(0, Integer.MAX_VALUE, apiRequest.getSort());

    try {

      DateTime originalDateTo = apiRequest.getDataQuery().getDateTo();
      DateTime originalDateFrom = apiRequest.getDataQuery().getDateFrom();

      // if period = latest -> compare to latest - 6. Otherwise, always
      // the last day of this period
      boolean isLatest = CollectionUtils.size(selectedPeriodDateList) == 1;
      DateTime prevDateTo = isLatest ? originalDateFrom.minusDays(6) : originalDateFrom;
      DateTime prevDateFrom = prevDateTo.minusDays(0);

      /**
       * Step 1 previous day rank request (1 day period overall rank)
       */
      DemandRequest rankRequest = apiRequest.clone();
      rankRequest.getDataQuery().setDateFrom(prevDateFrom);
      rankRequest.getDataQuery().setDateTo(prevDateTo);
      rankRequest.getDataQuery().setPeriod(null);
      rankRequest.setPrecomputed(null);

      rankRequest.setDateRangeList(Arrays.asList(prevDateTo.toDate()));
      rankRequest.setBenchmark_de(Boolean.FALSE);
      List<GroupedExpressions> prevRankData = fetchDemandData(rankRequest, demandPagable).stream()
          .collect(Collectors.toList());
      prevRankExpr = prevRankData.stream().collect(Collectors.groupingBy(e -> e.getShort_id()));

      /**
       * Step 2 latest day rank request
       */
      rankRequest = apiRequest.clone();
      rankRequest.getDataQuery().setDateFrom(originalDateTo);
      rankRequest.getDataQuery().setDateTo(originalDateTo);
      rankRequest.getDataQuery().setPeriod(null);
      //inject filter rank
      rankRequest.setPrecomputed(null);

      rankRequest.setDateRangeList(Arrays.asList(originalDateTo.toDate()));

      rankRequest.setBenchmark_de(Boolean.FALSE);
      List<GroupedExpressions> latestRankData = fetchDemandData(rankRequest, demandPagable).stream()
          .collect(Collectors.toList());
      latestRankExpr = latestRankData.stream().collect(Collectors.groupingBy(e -> e.getShort_id()));

      /**
       * Step 3: Fetch Selected Period Demand
       */
      apiRequest.getDataQuery().setShowranks(true);
      apiRequest.getDataQuery().setShowFilterRanks(true);
      selectedPeriodExpr = fetchDemandData(apiRequest, demandPagable).stream()
          .collect(Collectors.toList());

    } catch (APIException ex) {
      logger.error(ex.getMessage(), ex);
    }

    String market = dataQuery.getMarketStr();

    for (GroupedExpressions ge : selectedPeriodExpr) {
      Long shortID = ge.getShort_id();

      GroupedExpressions thisGE, prevGE = null;
      if (prevRankExpr.containsKey(shortID)) {
        prevGE = prevRankExpr.get(shortID).get(0);
      } else {
        prevGE = new GroupedExpressions(
            selectedPeriodDateList.get(selectedPeriodDateList.size() - 1), shortID,
            market, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 1);
      }

      if (latestRankExpr.containsKey(shortID)) {
        thisGE = latestRankExpr.get(shortID).get(0);
      } else {
        thisGE = new GroupedExpressions(
            selectedPeriodDateList.get(selectedPeriodDateList.size() - 1), shortID,
            market, 0.0, 0.0, 0.0, 0.0, 0, 0, 0, 0, 1);
      }
      Integer changeRank = prevGE.getOverall_rank() - thisGE.getOverall_rank();
      ge.setRank_change(changeRank);
    }
    logger.info("Method:findOverallChangeRank, finished in {} ms", qtm.timeTakenMillis());
    return selectedPeriodExpr;
  }

  public List<Long> filterTitles(Entity entity, DataQueryFilter filters,
                                 List<Long> contentShortIDs) {
    if (filters == null) {
      return contentShortIDs;
    }
    switch (entity) {
      case TV_SERIES:
      case SHOW:
      default:
        return catalogESService.searchShortIdsByFilter(ElasticSearchIndex.SERIES_METADATA,
            new ShowESFilter(filters), contentShortIDs);
      case TALENT:
        return catalogESService.searchShortIdsByFilter(ElasticSearchIndex.TALENT_METADATA,
            new TalentESFilter(filters), contentShortIDs);
      case MOVIE:
        return catalogESService.searchShortIdsByFilter(ElasticSearchIndex.MOVIE_METADATA,
            new MovieESFilter(filters), contentShortIDs);
    }
  }

  protected List<Long> filterTitles(Map<String, String> filters, List<Long> contentShortIDs,
                                    ConditionalOperator operator) {
    return operator == ConditionalOperator.OR ?
        filterShowsOperatorOr(filters, contentShortIDs) :
        filterShowsOperatorAnd(filters, contentShortIDs);
  }

  public List<Long> filterShowsOperatorAnd(Map<String, String> filters,
      List<Long> contentShortIDs) {
    Map<String, String> cloneFilters = new HashMap<String, String>(filters);

    if (cloneFilters.containsKey(APIConstants.FILTER_LABEL)) {
      contentShortIDs = labelRepo.filterShowsByLabels(
          DemandHelper.requestLabels(cloneFilters.get(APIConstants.FILTER_LABEL)), contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_LABEL);
    }
    if (CollectionUtils.isEmpty(contentShortIDs)) {
      return contentShortIDs;
    }
    if (cloneFilters.containsKey(APIConstants.FILTER_SEASON_NUMBER)) {
      contentShortIDs = tvSeasonService.filterShowsBySeasonNumber(
          cloneFilters.get(APIConstants.FILTER_SEASON_NUMBER), contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_SEASON_NUMBER);
    }
    if (CollectionUtils.isEmpty(contentShortIDs)) {
      return contentShortIDs;
    }
    if (cloneFilters.containsKey(APIConstants.FILTER_ORIGINAL_LANGUAGE)) {
      contentShortIDs = metadataService.filterShowsByOriginalLanguage(
          cloneFilters.get(APIConstants.FILTER_ORIGINAL_LANGUAGE), contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_ORIGINAL_LANGUAGE);
    }
    if (CollectionUtils.isEmpty(contentShortIDs)) {
      return contentShortIDs;
    }
    if (cloneFilters.containsKey(APIConstants.FILTER_TV_SERIES_TYPE)) {
      contentShortIDs = metadataService.filterShowsByTvSeriesType(
          cloneFilters.get(APIConstants.FILTER_TV_SERIES_TYPE), contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_TV_SERIES_TYPE);
    }
    if (CollectionUtils.isEmpty(contentShortIDs)) {
      return contentShortIDs;
    }

    if (FiltersBuilder.hasValidReleaseDateFilter(cloneFilters)) {
      contentShortIDs = filterByReleaseDate(cloneFilters, contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_RELEASE_DATE);
    }

    if (MapUtils.isNotEmpty(cloneFilters)) {
      contentShortIDs = titlesRepo.filterShowsByGenes(contentShortIDs, cloneFilters,
          ConditionalOperator.AND);
    }
    return contentShortIDs;
  }

  public List<Long> filterShowsOperatorOr(Map<String, String> filters, List<Long> contentShortIDs) {
    Map<String, String> cloneFilters = new HashMap<String, String>(filters);
    int numOfShortIDs = contentShortIDs.size();
    if (cloneFilters.containsKey(APIConstants.FILTER_LABEL)) {
      contentShortIDs = labelRepo.filterShowsByLabels(
          DemandHelper.requestLabels(cloneFilters.get(APIConstants.FILTER_LABEL)), contentShortIDs);
      cloneFilters.remove(APIConstants.FILTER_LABEL);
    }

    if (cloneFilters.containsKey(APIConstants.FILTER_SEASON_NUMBER)) {
      contentShortIDs = contentShortIDs.size() < numOfShortIDs ?
          new ArrayList<>(
              CollectionUtils.union(contentShortIDs, tvSeasonService.filterShowsBySeasonNumber(
                  cloneFilters.get(APIConstants.FILTER_SEASON_NUMBER), contentShortIDs))) :
          contentShortIDs;
      cloneFilters.remove(APIConstants.FILTER_SEASON_NUMBER);
    }

    if (cloneFilters.containsKey(APIConstants.FILTER_ORIGINAL_LANGUAGE)) {
      contentShortIDs = contentShortIDs.size() < numOfShortIDs ?
          new ArrayList<>(CollectionUtils.union(contentShortIDs,
              metadataService.filterShowsByOriginalLanguage(
                  cloneFilters.get(APIConstants.FILTER_ORIGINAL_LANGUAGE), contentShortIDs))) :
          contentShortIDs;
      cloneFilters.remove(APIConstants.FILTER_ORIGINAL_LANGUAGE);
    }

    if (cloneFilters.containsKey(APIConstants.FILTER_TV_SERIES_TYPE)) {
      contentShortIDs = contentShortIDs.size() < numOfShortIDs ?
          new ArrayList<>(
              CollectionUtils.union(contentShortIDs, metadataService.filterShowsByTvSeriesType(
                  cloneFilters.get(APIConstants.FILTER_TV_SERIES_TYPE), contentShortIDs))) :
          contentShortIDs;
      cloneFilters.remove(APIConstants.FILTER_TV_SERIES_TYPE);
    }

    if (FiltersBuilder.hasValidReleaseDateFilter(cloneFilters)) {
      contentShortIDs = contentShortIDs.size() < numOfShortIDs ?
          new ArrayList<>(CollectionUtils.union(contentShortIDs,
              filterByReleaseDate(cloneFilters, contentShortIDs))) :
          contentShortIDs;
      cloneFilters.remove(APIConstants.FILTER_RELEASE_DATE);
    }

    if (MapUtils.isNotEmpty(cloneFilters)) {
      contentShortIDs = contentShortIDs.size() < numOfShortIDs ?
          new ArrayList<>(CollectionUtils.union(contentShortIDs,
              titlesRepo.filterShowsByGenes(contentShortIDs, cloneFilters, ConditionalOperator.OR)))
          :
              contentShortIDs;
    }
    return contentShortIDs;
  }

  public List<Long> filterByReleaseDate(Map<String, String> filters, List<Long> contentShortIDs) {

    DateTime releaseDateStart = FiltersBuilder.getFilterReleaseDateStart(filters);
    DateTime releaseDateEnd = FiltersBuilder.getFilterReleaseDateEnd(filters);
    ReleaseDateFilterType releaseDateFilterType = FiltersBuilder.getReleaseDateFilterType(filters);
    if (!Objects.isNull(releaseDateStart) && !Objects.isNull(releaseDateEnd) && !Objects.isNull(
        releaseDateFilterType)) {
      contentShortIDs = titlesRepo.filteredShowsByReleaseDate(releaseDateStart, releaseDateEnd,
          releaseDateFilterType, contentShortIDs);
    }
    return contentShortIDs;
  }

  public double calculateMarketSum(DemandRequest apiRequest, PageRequest pageRequest)
      throws APIException {
    double marketSum = 0.0;
    if (apiRequest.getDateRangeList().size() <= 30) {
      // this native query takes > 30 seconds to return for longer range
      marketSum = demandDataRepo.marketSumDemand(apiRequest.getDateRangeList().get(0),
          apiRequest.getDateRangeList().get(apiRequest.getDateRangeList().size() - 1),
          apiRequest.getDataQuery().getMarketsList().get(0));
    } else {
      DemandRequest fullCatRequest = apiRequest.clone();
      List<Long> customerReadyShortIDList = metadataCache.accountTitleShortIds(
          accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT));
      fullCatRequest.getDataQuery().setShortIDsList(customerReadyShortIDList);

      List<GroupedExpressions> avgResult = fetchDemandData(apiRequest, pageRequest).getContent();

      for (GroupedExpressions ge : avgResult) {
        marketSum += ge.getDexpercapita();
      }
    }
    return marketSum;
  }

  /**
   * Extract list of IDs from query
   *
   * @param listContentIDs
   * @return
   */
  public List<Long> extractIDs(List<String> listContentIDs) {
    List<Long> shortIDs = null;

    if (CollectionUtils.isNotEmpty(listContentIDs)) {
      shortIDs = titlesRepo.shortIDs(listContentIDs);
    }

    return shortIDs;
  }

  public Map<String, Double> getBenchmarkDEByDate(DemandRequest apiRequest, DataQuery dataQuery) {
    Map<String, Double> benchmarkDEByDate = new HashMap<>();
    if (apiRequest.isBenchmark_de() && !Interval.OVERALL.value().equals(apiRequest.getInterval())) {
      String market = apiRequest.isWorldwideRequest() ?
          APIConstants.WORLDWIDE_CODE :
          APIConstants.UNITED_STATE_CODE;

      benchmarkDEByDate = demandDataRepo.dailyBenchmarkDemandByCountry(market,
          dataQuery.getDateFrom().toDate(),
          dataQuery.getDateTo().toDate(), dataQuery.getEntityEnum()).stream().collect(
          Collectors.toMap(d -> CommonsDateUtil.formatDate(d.getDate()),
              BenchmarkDemand::getBenchmark_de));
    }
    return benchmarkDEByDate;
  }

  public List<Long> injectContentShortIDs(final DemandRequest apiRequest, DataQuery dataQuery)
      throws APIException {
    // we decided that Portfolios (custom or preset) will not be affected by
    // filters
    List<Long> contentShortIDs = extractIDs(dataQuery.getListContentIDs());

    // a query in 1+ queries array can NOT have both content and portfolio
    // field COEXIST
    if (apiRequest.isAllShowsRequest()) {
      contentShortIDs = metadataCache.accountTitleShortIds(
          accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT));
      contentShortIDs = excludeDisableTopShows(contentShortIDs);
    } else if (apiRequest.isSubscriptionShowsRequest()) {
      contentShortIDs = metadataCache.accountTitleShortIds(
          accountRepo.findByIdAccount(apiRequest.getApiAccountId()));
    } else if (apiRequest.isAllTalentsRequest()) {
      contentShortIDs = metadataCache.accountTalentShortIds(
          accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT));
    } else if (apiRequest.isAllMoviesRequest()) {
      contentShortIDs = metadataCache.accountMovieShortIds(
          accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT));
    } else if (dataQuery.hasPortfolios()) {
      // we merge existing portfolios with other within portfolios
      contentShortIDs = contentShortIDs != null ? contentShortIDs : new ArrayList<>();
      for (Portfolio portfolio : dataQuery.getPortfolios()) {
        contentShortIDs.addAll(portfolio.getShortIDList());
      }
      contentShortIDs = contentShortIDs.stream().distinct().collect(Collectors.toList());
    }

    if (dataQuery.hasValidFilter() && !dataQuery.hasPortfolios()) {
      // filters should not affect on a query having portfolio field
      contentShortIDs = filterTitles(dataQuery.getEntityEnum(), dataQuery.getFilters(),
          contentShortIDs);
    }
    return contentShortIDs;
  }

  public List<Long> getFullEntityCatalogShortIds(Entity entity) throws APIException {
    Account account = accountRepo.findByIdAccount(APIConstants.CUSTOMER_READY_ACCOUNT);
    switch (entity) {
      case TV_SERIES: //legacy entity
      case SHOW:
      default:
        return metadataCache.accountTitleShortIds(account);
      case TALENT:
        return metadataCache.accountTalentShortIds(account);
      case MOVIE:
        return metadataCache.accountMovieShortIds(account);
    }
  }

  public List<Long> excludeDisableTopShows(List<Long> contentShortIDs) {
    /*
     * Note: we can exclude shows from appearing in TOP SHOWS if any show
     * doesn't satisfy some data health checks
     */
    String disabledTopShows = apiConfig.readProperty(APIConfig.DISABLED_TOPSHOWS_DATAAPI);
    if (StringUtils.isNotEmpty(disabledTopShows)) {
      List<String> disabledTopShowsList = Arrays.asList(disabledTopShows.split(","));

      if (!CollectionUtils.isEmpty(disabledTopShowsList)) {
        contentShortIDs.removeAll(titlesRepo.shortIDs(disabledTopShowsList));
      }
    }
    return contentShortIDs;
  }

  /**
   * Sort the given list of {@link GroupedExpressions} with desired {@link Sort} and also ranks the
   * items after sorting
   *
   * @param expressionsList
   * @param sort
   * @param sortBy
   * @param contentShortIDs
   * @param pageRequest
   * @return
   */
  public List<GroupedExpressions> sortOverallRankNPeakRankNPagination(
      List<GroupedExpressions> expressionsList, Sort sort,
      String sortBy,
      DemandRequest demandRequest, List<Long> contentShortIDs, PageRequest pageRequest) {
    if (pageRequest != null) {
      DataQuery query = demandRequest.getDataQuery();

      Order order = sort.iterator().next();

      Direction dir = order.getDirection();
      /* show rank is the rank WITHIN whole catalog */
      if (query.isShowranks()) {
        int rank = 0;
        Collections.sort(expressionsList,
            new GroupedExpressionsComparator(APIConstants.DEXPERCAPITA, Direction.DESC));
        for (GroupedExpressions ge : expressionsList) {
          ge.setOverall_rank(++rank);
        }
        rank = 0;
        Collections.sort(expressionsList,
            new GroupedExpressionsComparator(APIConstants.PEAK_DEXPERCAPITA, Direction.DESC));
        for (GroupedExpressions ge : expressionsList) {
          ge.setRank_by_peak(++rank);
        }
      }

      // sort by the sortBy first
      Collections.sort(expressionsList, new GroupedExpressionsComparator(sortBy, dir));

      /* filter by subset of shortid*/
      if (CollectionUtils.isNotEmpty(contentShortIDs)) {
        expressionsList = expressionsList.stream()
            .filter(ex -> contentShortIDs.contains(ex.getShort_id()))
            .collect(Collectors.toList());
      }
      //filter rank is the rank aftere the filtered is applied
      if (query.isShowFilterRanks()) {
        int rank = 0;
        //we have to create new expression list so the order specified order_by is respected
        List<GroupedExpressions> newExpressionsList = new ArrayList<>(expressionsList);
        Collections.sort(newExpressionsList,
            new GroupedExpressionsComparator(APIConstants.DEXPERCAPITA, Direction.DESC));
        for (GroupedExpressions ge : newExpressionsList) {
          ge.setFilter_rank(++rank);
        }
      }

      int indFrom = pageRequest.getPageNumber() * pageRequest.getPageSize();
      int indTo = (pageRequest.getPageNumber() + 1) * pageRequest.getPageSize();

      expressionsList = expressionsList.subList(indFrom,
          indTo < expressionsList.size() ? indTo : expressionsList.size());
    }

    return expressionsList;
  }

  public Map<Portfolio, Page<GroupedExpressions>> fetchGroupByPortfolioDemand(
      DemandRequest apiRequest, PageRequest pageRequest) throws APIException {
    Map<Portfolio, Page<GroupedExpressions>> result = new HashMap<>();
    List<Portfolio> portfolios = apiRequest.getDataQuery().getPortfolios();

    List<CompletableFuture<Pair<Portfolio, Page<GroupedExpressions>>>> futuresList = new ArrayList<>();

    for (Portfolio portfolio : portfolios) {
      CompletableFuture<Pair<Portfolio, Page<GroupedExpressions>>> future = CompletableFuture.supplyAsync(
          () -> {
            DemandRequest clonedRequest = apiRequest.clone();
            try {
              clonedRequest.getDataQuery().setPortfolios(new ArrayList<>());
              clonedRequest.getDataQuery().setShortIDsList(portfolio.getShortIDList());
              if(CollectionUtils.size(portfolio.getShortIDList()) == 0){
                return new Pair<>(portfolio, new PageImpl<>(new ArrayList<>()));
              }
              Page<GroupedExpressions> portResult = fetchDemandData(clonedRequest,
                  pageRequest);
              return new Pair<>(portfolio, portResult);
            } catch (APIException e) {
              logger.error("failed to fetch demand data for portfolio {}:{}.Exception is {}",
                  portfolio.getStringID(),
                  portfolio.getName(), e);
            }
            return new Pair<>(portfolio, new PageImpl<>(new ArrayList<>()));
          }, Executors.newCachedThreadPool());
      futuresList.add(future);
    }
    try {
      CompletableFuture<Void> allFutures = CompletableFuture
          .allOf(futuresList.toArray(new CompletableFuture[futuresList.size()]));
      CompletableFuture<List<Pair<Portfolio, Page<GroupedExpressions>>>> allCompletableFuture = allFutures
          .thenApply(
              f -> futuresList.stream().map(completableFuture -> completableFuture.join())
                  .collect(Collectors.toList()));
      result = allCompletableFuture.get().stream().filter(Objects::nonNull)
          .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    } catch (ExecutionException | InterruptedException ex) {
      logger.error("failed to invoke parallel calls in fetchScatterPlotPerformance.Exception is {}",
          ex);
      throw new APIException(
          "failed to invoke parallel calls in fetchScatterPlotPerformance.Exception is {}",
          ex);
    }
    return result;
  }

  public List<GroupedExpressions> sortFilterRank(List<GroupedExpressions> expressionsList,
      Sort sort,
      String sortBy,
      DemandRequest demandRequest, PageRequest pageRequest) {
    if (pageRequest != null) {
      DataQuery query = demandRequest.getDataQuery();
      Order order = sort.iterator().next();
      Direction dir = order.getDirection();
      int rank = 0;
      Collections.sort(expressionsList,
          new GroupedExpressionsComparator(APIConstants.DEXPERCAPITA, Direction.DESC));
      for (GroupedExpressions ge : expressionsList) {
        ge.setFilter_rank(++rank);
      }

    }
    return expressionsList;
  }
}
