package com.parrotanalytics.api.util;

import com.google.gson.Gson;
import com.parrotanalytics.api.apidb_model.ContextMetricsSetting;
import com.parrotanalytics.api.apidb_model.ContextMetricsSettingV2;
import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.api.apidb_model.ExpressionsMonthly;
import com.parrotanalytics.api.apidb_model.ExpressionsQuarterly;
import com.parrotanalytics.api.apidb_model.ExpressionsWeekly;
import com.parrotanalytics.api.apidb_model.ExpressionsYearly;
import com.parrotanalytics.api.apidb_model.ISubscription;
import com.parrotanalytics.api.apidb_model.MovieExpressionsMonthly;
import com.parrotanalytics.api.apidb_model.MovieExpressionsQuarterly;
import com.parrotanalytics.api.apidb_model.MovieExpressionsWeekly;
import com.parrotanalytics.api.apidb_model.MovieExpressionsYearly;
import com.parrotanalytics.api.apidb_model.MovieSubscription;
import com.parrotanalytics.api.apidb_model.Portfolio;
import com.parrotanalytics.api.apidb_model.Subscription;
import com.parrotanalytics.api.apidb_model.TalentExpressionsDaily;
import com.parrotanalytics.api.apidb_model.TalentExpressionsMonthly;
import com.parrotanalytics.api.apidb_model.TalentExpressionsQuarterly;
import com.parrotanalytics.api.apidb_model.TalentExpressionsWeekly;
import com.parrotanalytics.api.apidb_model.TalentExpressionsYearly;
import com.parrotanalytics.api.apidb_model.TalentSubscription;
import com.parrotanalytics.api.apidb_model.nonmanaged.DateRangeKey;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedGenesExpressions;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.PreComputed;
import com.parrotanalytics.api.commons.constants.SubscriptionType;
import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.api.commons.constants.TimePeriod;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;
import com.parrotanalytics.api.request.metricrank.MetricRankRequest;
import com.parrotanalytics.api.request.rawsignal.RawSignalRequest;
import com.parrotanalytics.api.request.titlecontext.ContextMetricType;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;
import com.parrotanalytics.commons.constants.ParrotConstants;
import com.parrotanalytics.commons.utils.CommonsUtil;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "singleton")
public class DemandHelper {

  public static final String DELIM_CONFIG = ":";
  @Autowired
  protected static DemandRepository demandDataRepo;

  protected static Map<Interval, String> interval2SeriesTable = new HashMap<>();
  protected static Map<Interval, String> interval2TalentTable = new HashMap<>();
  protected static Map<Interval, String> interval2MovieTable = new HashMap<>();

  protected static Map<PreComputed, String> precomputed2SeriesTable = new HashMap<>();
  protected static Map<PreComputed, String> precomputed2TalentTable = new HashMap<>();
  protected static Map<PreComputed, String> precomputed2MovieTable = new HashMap<>();

  protected static Map<Interval, String> interval2SeriesTableEntity = new HashMap<>();
  protected static Map<Interval, String> interval2TalentTableEntity = new HashMap<>();
  protected static Map<Interval, String> interval2MovieTableEntity = new HashMap<>();

  protected static Map<PreComputed, String> precomputed2SeriesTableEntity = new HashMap<>();
  protected static Map<PreComputed, String> precomputed2TalentTableEntity = new HashMap<>();
  protected static Map<PreComputed, String> precomputed2MovieTableEntity = new HashMap<>();

  protected static Map<Entity, String> entity2CatalogGenesTable = new HashMap<>();

  protected static Map<Entity, String> entity2MetadataTable = new HashMap<>();

  static {
    // interval -> table
    interval2SeriesTable.put(Interval.YEARLY, TableConstants.EXPRESSIONS_COMPUTED_YEARLY);
    interval2SeriesTable.put(Interval.QUARTERLY, TableConstants.EXPRESSIONS_COMPUTED_QUARTERLY);
    interval2SeriesTable.put(Interval.MONTHLY, TableConstants.EXPRESSIONS_COMPUTED_MONTHLY);
    interval2SeriesTable.put(Interval.WEEKLY, TableConstants.EXPRESSIONS_COMPUTED_WEEKLY);
    interval2SeriesTable.put(Interval.DAILY, TableConstants.EXPRESSIONS_DAILY);

    interval2TalentTable.put(Interval.YEARLY, TableConstants.TALENT_EXPRESSIONS_COMPUTED_YEARLY);
    interval2TalentTable.put(Interval.QUARTERLY,
        TableConstants.TALENT_EXPRESSIONS_COMPUTED_QUARTERLY);
    interval2TalentTable.put(Interval.MONTHLY, TableConstants.TALENT_EXPRESSIONS_COMPUTED_MONTHLY);
    interval2TalentTable.put(Interval.WEEKLY, TableConstants.TALENT_EXPRESSIONS_COMPUTED_WEEKLY);
    interval2TalentTable.put(Interval.DAILY, TableConstants.TALENT_EXPRESSIONS_DAILY);

    interval2MovieTable.put(Interval.YEARLY, TableConstants.MOVIE_EXPRESSIONS_COMPUTED_YEARLY);
    interval2MovieTable.put(Interval.QUARTERLY,
        TableConstants.MOVIE_EXPRESSIONS_COMPUTED_QUARTERLY);
    interval2MovieTable.put(Interval.MONTHLY, TableConstants.MOVIE_EXPRESSIONS_COMPUTED_MONTHLY);
    interval2MovieTable.put(Interval.WEEKLY, TableConstants.MOVIE_EXPRESSIONS_COMPUTED_WEEKLY);
    interval2MovieTable.put(Interval.DAILY, TableConstants.MOVIE_EXPRESSIONS_DAILY);

    // interval -> tableentity

    interval2SeriesTableEntity.put(Interval.YEARLY, ExpressionsYearly.class.getSimpleName());
    interval2SeriesTableEntity.put(Interval.QUARTERLY, ExpressionsQuarterly.class.getSimpleName());
    interval2SeriesTableEntity.put(Interval.MONTHLY, ExpressionsMonthly.class.getSimpleName());
    interval2SeriesTableEntity.put(Interval.WEEKLY, ExpressionsWeekly.class.getSimpleName());
    interval2SeriesTableEntity.put(Interval.DAILY, ExpressionsDaily.class.getSimpleName());

    interval2TalentTableEntity.put(Interval.YEARLY, TalentExpressionsYearly.class.getSimpleName());
    interval2TalentTableEntity.put(Interval.QUARTERLY,
        TalentExpressionsQuarterly.class.getSimpleName());
    interval2TalentTableEntity.put(Interval.MONTHLY,
        TalentExpressionsMonthly.class.getSimpleName());
    interval2TalentTableEntity.put(Interval.WEEKLY, TalentExpressionsWeekly.class.getSimpleName());
    interval2TalentTableEntity.put(Interval.DAILY, TalentExpressionsDaily.class.getSimpleName());

    interval2MovieTableEntity.put(Interval.YEARLY, MovieExpressionsYearly.class.getSimpleName());
    interval2MovieTableEntity.put(Interval.QUARTERLY,
        MovieExpressionsQuarterly.class.getSimpleName());
    interval2MovieTableEntity.put(Interval.MONTHLY, MovieExpressionsMonthly.class.getSimpleName());
    interval2MovieTableEntity.put(Interval.WEEKLY, MovieExpressionsWeekly.class.getSimpleName());
    interval2MovieTableEntity.put(Interval.DAILY, MovieExpressionsYearly.class.getSimpleName());

    // precomputed -> table
    precomputed2SeriesTable.put(PreComputed.YEARLY, TableConstants.EXPRESSIONS_COMPUTED_YEARLY);
    precomputed2SeriesTable.put(PreComputed.QUARTERLY,
        TableConstants.EXPRESSIONS_COMPUTED_QUARTERLY);
    precomputed2SeriesTable.put(PreComputed.MONTHLY, TableConstants.EXPRESSIONS_COMPUTED_MONTHLY);
    precomputed2SeriesTable.put(PreComputed.WEEKLY, TableConstants.EXPRESSIONS_COMPUTED_WEEKLY);
    precomputed2SeriesTable.put(PreComputed.RANGE, TableConstants.EXPRESSIONS_COMPUTED);

    precomputed2TalentTable.put(PreComputed.YEARLY,
        TableConstants.TALENT_EXPRESSIONS_COMPUTED_YEARLY);
    precomputed2TalentTable.put(PreComputed.QUARTERLY,
        TableConstants.TALENT_EXPRESSIONS_COMPUTED_QUARTERLY);
    precomputed2TalentTable.put(PreComputed.MONTHLY,
        TableConstants.TALENT_EXPRESSIONS_COMPUTED_MONTHLY);
    precomputed2TalentTable.put(PreComputed.WEEKLY,
        TableConstants.TALENT_EXPRESSIONS_COMPUTED_WEEKLY);
    precomputed2TalentTable.put(PreComputed.RANGE, TableConstants.TALENT_EXPRESSIONS_COMPUTED);

    precomputed2MovieTable.put(PreComputed.YEARLY,
        TableConstants.MOVIE_EXPRESSIONS_COMPUTED_YEARLY);
    precomputed2MovieTable.put(PreComputed.QUARTERLY,
        TableConstants.MOVIE_EXPRESSIONS_COMPUTED_QUARTERLY);
    precomputed2MovieTable.put(PreComputed.MONTHLY,
        TableConstants.MOVIE_EXPRESSIONS_COMPUTED_MONTHLY);
    precomputed2MovieTable.put(PreComputed.WEEKLY,
        TableConstants.MOVIE_EXPRESSIONS_COMPUTED_WEEKLY);
    precomputed2MovieTable.put(PreComputed.RANGE, TableConstants.MOVIE_EXPRESSIONS_COMPUTED);

    // precomputed -> tableentity
    precomputed2SeriesTableEntity.put(PreComputed.YEARLY, ExpressionsYearly.class.getSimpleName());
    precomputed2SeriesTableEntity.put(PreComputed.QUARTERLY,
        ExpressionsQuarterly.class.getSimpleName());
    precomputed2SeriesTableEntity.put(PreComputed.MONTHLY,
        ExpressionsMonthly.class.getSimpleName());
    precomputed2SeriesTableEntity.put(PreComputed.WEEKLY, ExpressionsWeekly.class.getSimpleName());
    precomputed2SeriesTableEntity.put(PreComputed.RANGE, ExpressionsDaily.class.getSimpleName());

    precomputed2TalentTableEntity.put(PreComputed.YEARLY,
        TalentExpressionsYearly.class.getSimpleName());
    precomputed2TalentTableEntity.put(PreComputed.QUARTERLY,
        TalentExpressionsQuarterly.class.getSimpleName());
    precomputed2TalentTableEntity.put(PreComputed.MONTHLY,
        TalentExpressionsMonthly.class.getSimpleName());
    precomputed2TalentTableEntity.put(PreComputed.WEEKLY,
        TalentExpressionsWeekly.class.getSimpleName());
    precomputed2TalentTableEntity.put(PreComputed.RANGE,
        TalentExpressionsDaily.class.getSimpleName());

    precomputed2MovieTableEntity.put(PreComputed.YEARLY,
        MovieExpressionsYearly.class.getSimpleName());
    precomputed2MovieTableEntity.put(PreComputed.QUARTERLY,
        MovieExpressionsQuarterly.class.getSimpleName());
    precomputed2MovieTableEntity.put(PreComputed.MONTHLY,
        MovieExpressionsMonthly.class.getSimpleName());
    precomputed2MovieTableEntity.put(PreComputed.WEEKLY,
        MovieExpressionsWeekly.class.getSimpleName());
    precomputed2MovieTableEntity.put(PreComputed.RANGE,
        MovieExpressionsYearly.class.getSimpleName());

    // entity to catalog genes table
    entity2CatalogGenesTable.put(Entity.SHOW, TableConstants.TV_SERIES_CATALOG_GENES);
    entity2CatalogGenesTable.put(Entity.TV_SERIES, TableConstants.TV_SERIES_CATALOG_GENES);
    entity2CatalogGenesTable.put(Entity.MOVIE, TableConstants.MOVIE_CATALOG_GENES);
    entity2CatalogGenesTable.put(Entity.TALENT, TableConstants.TALENT_CATALOG_GENES);

    // entity to catalog genes table
    entity2MetadataTable.put(Entity.SHOW, TableConstants.TV_SERIES_METADATA);
    entity2MetadataTable.put(Entity.TV_SERIES, TableConstants.TV_SERIES_METADATA);
    entity2MetadataTable.put(Entity.MOVIE, TableConstants.MOVIE_METADATA);
    entity2MetadataTable.put(Entity.TALENT, TableConstants.TALENT_METADATA);
  }

  public static String getExpressionsTableByInterval(Interval interval) {
    return getExpressionsTableWithSchemaByInterval(interval, Entity.TV_SERIES);
  }

  public static String getExpressionsTableByPrecomputed(PreComputed precomputed, Entity entity) {
    String tableName;

    if (Entity.TALENT == entity) {
      tableName = precomputed2TalentTable.get(precomputed);
    } else if (Entity.MOVIE == entity) {
      tableName = precomputed2MovieTable.get(precomputed);
    } else {
      tableName = precomputed2SeriesTable.get(precomputed);
    }
    return TableConstants.METRICS_SCHEMA + "." + tableName;
  }

  public static String getExpressionsTableWithSchemaByInterval(Interval interval, Entity entity) {
    String tableName = null;

    if (Entity.TALENT == entity) {
      tableName = interval2TalentTable.get(interval);
    } else if (Entity.MOVIE == entity) {
      tableName = interval2MovieTable.get(interval);
    } else {
      tableName = interval2SeriesTable.get(interval);
    }
    return TableConstants.METRICS_SCHEMA + "." + tableName;
  }

  public static String getTableEntityByInterval(Interval interval, Entity entity) {
    if (Entity.TALENT == entity) {
      return interval2TalentTableEntity.get(interval);

    } else if (Entity.MOVIE == entity) {

      return interval2MovieTableEntity.get(interval);
    } else {
      return interval2SeriesTableEntity.get(interval);
    }
  }

  public static String getTableEntityByPrecomputed(PreComputed precomputed, Entity entity) {
    if (Entity.TALENT == entity) {

      return precomputed2TalentTableEntity.get(precomputed);
    } else if (Entity.MOVIE == entity) {

      return precomputed2MovieTableEntity.get(precomputed);

    } else {
      return precomputed2SeriesTableEntity.get(precomputed);

    }
  }

  public static String getCatalogGenesTableByEntity(Entity entity) {
    return entity2CatalogGenesTable.get(entity);
  }

  public static String getMetadataTableByEntity(Entity entity) {
    return entity2MetadataTable.get(entity);
  }

  public static DateRangeKey findDateRangeByKey(Date date, String rangeKey) {
    Optional<DateRangeKey> found = getDailyDateRanges(date).stream()
        .filter(r -> r.getRangeKey().equals(rangeKey))
        .findFirst();

    return found.isPresent() ? found.get() : null;
  }

  public static List<DateRangeKey> getDailyDateRanges(Date date) {
    List<DateRangeKey> dailyDateRanges = new ArrayList<>();

    dailyDateRanges.add(
        new DateRangeKey(new DateTime(date), new DateTime(date), TimePeriod.LATESTDAY.value()));
    dailyDateRanges.add(
        new DateRangeKey(new DateTime(CommonsDateUtil.lastNDays(date, 6)), new DateTime(date),
            TimePeriod.LAST7DAYS.value()));
    dailyDateRanges.add(
        new DateRangeKey(new DateTime(CommonsDateUtil.lastNDays(date, 29)), new DateTime(date),
            TimePeriod.LAST30DAYS.value()));
    dailyDateRanges.add(
        new DateRangeKey(new DateTime(CommonsDateUtil.firstDayOfMonth(date)), new DateTime(date),
            TimePeriod.THISMONTH.value()));
    dailyDateRanges
        .add(new DateRangeKey(
            new DateTime(CommonsDateUtil.firstDayOfMonth(CommonsDateUtil.lastMonth(date))),
            new DateTime(CommonsDateUtil.lastDayOfMonth(CommonsDateUtil.lastMonth(date))),
            TimePeriod.LASTMONTH.value()));
    dailyDateRanges.add(
        new DateRangeKey(new DateTime(CommonsDateUtil.lastNDays(date, 59)), new DateTime(date),
            TimePeriod.LAST60DAYS.value()));
    dailyDateRanges.add(
        new DateRangeKey(new DateTime(CommonsDateUtil.lastNDays(date, 89)), new DateTime(date),
            TimePeriod.LAST90DAYS.value()));

    return dailyDateRanges;
  }

  protected static String getLowerInterval(String longestInterval) {
    if (Interval.YEARLY.value().equals(longestInterval)) {
      return Interval.MONTHLY.value();
    } else if (Interval.MONTHLY.value().equals(longestInterval)) {
      return Interval.WEEKLY.value();
    }
    return Interval.DAILY.value();
  }

  public static int pageSize(DemandRequest apiRequest) {
    if (apiRequest.isSelectedShowsRequest()) {
      if (Interval.OVERALL.value().equals(apiRequest.getInterval())) {
        return apiRequest.getListContentIDs().size();
      } else if (apiRequest.getListMarkets() != null && apiRequest.getListMarkets().size() > 1) {
        return apiRequest.getListContentIDs().size() * apiRequest.getListMarkets().size()
            * apiRequest.getDaysRequested();
      } else {
        return apiRequest.getListContentIDs().size() * apiRequest.getDaysRequested();
      }
    } else if (apiRequest.isSubscriptionShowsRequest() || apiRequest.isAllShowsRequest()) {
      return apiRequest.getSize();
    }

    return 3000;
  }

  public static int pageNumber(DemandRequest apiRequest) {
    return apiRequest.getPage() != null ? apiRequest.getPage() : 1;
  }

  public static String aggregateFunction(RawSignalRequest apiRequest) {
    return "sum";
  }

  public static String aggregateFunction(DemandRequest apiRequest) {
    if ((apiRequest.isSubscriptionMarketsRequest() && apiRequest.isAllShowsRequest())
        || apiRequest.getPrecomputed() != null) {

      if (apiRequest.getMetricType().startsWith("peak_")) {
        return "peak";
      } else if (apiRequest.getMetricType().startsWith("sum_")) {
        return "sum";
      } else {
        return "avg";
      }
    } else if ("sum".equalsIgnoreCase(apiRequest.getAggregation()) || apiRequest.getMetricType()
        .startsWith("sum_")) {
      return "sum";
    } else if (apiRequest.getMetricType().startsWith("peak_")) {
      return "max";
    }

    return "avg";
  }

  public static String prepareSortBy(DemandRequest request) {
    String sortBy = null;

    if (request.getSortBy() != null) {
      sortBy = request.getSortBy();
    } else if (request.getMetricType() != null) {
      sortBy = request.getMetricType();
    } else if (request instanceof MetricRankRequest) {
      MetricRankRequest metricRankRequest = (MetricRankRequest) request;
      if (metricRankRequest.getContextMetric() != null) {
        sortBy = metricRankRequest.getContextMetric();
      }
    } else {
      sortBy = "dex";
    }
    // set the sort by if not set
    if (StringUtils.isEmpty(request.getSortBy())) {
      request.setSortBy(sortBy);
    }

    return sortBy;
  }

  public static double getDataPointPeak(String metricType, GroupedExpressions gE) {
    double value = gE.getPeak_dexpercapita();
    if (APIConstants.DEX.equalsIgnoreCase(metricType)) {
      value = gE.getPeak_dex();
    }
    return value;
  }

  public static double getDataPointValue(String metricType, GroupedExpressions gE) {
    Portfolio portfolio = gE.getPortfolio();
    double value = gE.getDexpercapita();
    if (APIConstants.SUM_DEXPERCAPITA.equalsIgnoreCase(metricType)) {
      value = portfolio != null ? gE.getDexpercapita() * portfolio.getShortIDList().size()
          : gE.getDexpercapita() * gE.getNum_days();
    } else if (APIConstants.SUM_DEX.equalsIgnoreCase(metricType)) {
      value = portfolio != null ? gE.getDex() * portfolio.getShortIDList().size()
          : gE.getDex() * gE.getNum_days();
    } else if (APIConstants.DEX.equalsIgnoreCase(metricType)) {
      value = gE.getDex();
    }
    return value;
  }

  public static Object parseValue(ContextMetricsSettingV2 setting) throws APIException {
    Object labelBuckets = null;

    try {
      String value = setting.getLabel_buckets();

      /* parse map type values */
      Gson gson = new Gson();
      if (CommonsUtil.isMapType(value)) {
        labelBuckets = gson.fromJson(value, HashMap.class);
      }
      /* parse list type values */
      else if (CommonsUtil.isListType(value)) {
        labelBuckets = gson.fromJson(value, ArrayList.class);

        if (CollectionUtils.isEmpty((List) labelBuckets)) {
          return null;
        }
      }
      /* default : parse string type values */
      else {
        labelBuckets = setting.getLabel_buckets();
      }
    } catch (Exception e) {
      throw new APIException("incorrect label buckets value");
    }

    return labelBuckets;
  }

  public static Object parseValue(ContextMetricsSetting setting) throws APIException {
    Object labelBuckets = null;

    try {
      String value = setting.getLabel_buckets();

      /* parse map type values */
      if (CommonsUtil.isMapType(value)) {
        labelBuckets = new Gson().fromJson(value, HashMap.class);
      }
      /* parse list type values */
      else if (CommonsUtil.isListType(value)) {
        labelBuckets = new Gson().fromJson(value, ArrayList.class);
        if (CollectionUtils.isEmpty((List) labelBuckets)) {
          return null;
        }
      }
      /* default : parse string type values */
      else {
        labelBuckets = setting.getLabel_buckets();
      }
    } catch (Exception e) {
      throw new APIException("incorrect label buckets value");
    }

    return labelBuckets;
  }

  /**
   * @param marketStr
   * @return
   */
  public static List<String> parseMarkets(String marketStr) {
    String primaryMarket = null;

    List<String> uniqueMarkets = new ArrayList<String>();
    String[] marketsArr = marketStr.split(APIConstants.DELIM_COMMA);

    if (marketsArr != null && marketsArr.length >= 1) {
      List<String> allMarkets = new ArrayList<String>(Arrays.asList(marketsArr));

      Iterator<String> mItr = allMarkets.listIterator();

      while (mItr.hasNext()) {
        String m = mItr.next();

        if (m.contains(":sort")) {
          primaryMarket = m.split(DELIM_CONFIG)[0];
          uniqueMarkets.add(primaryMarket);
          mItr.remove();
        }
      }

      uniqueMarkets.addAll(allMarkets);
    }

    return new ArrayList<>(uniqueMarkets);
  }

  public static String getMetricRankColumn(ContextMetricType contextMetric, String interval) {
    if (Interval.DAILY.value().equals(interval)) {
      return "rank";
    } else if (Interval.OVERALL.value().equals(interval)) {
      return contextMetric == ContextMetricType.Dexpercapita ? "demand_rank" : "rank";
    }
    return "rank";
  }

  //TODO: remove now, should think about dependency
  /*public static Map<String, List<GroupedExpressions>> partitionResultByDate(
      List<GroupedExpressions> resultList) {
    Map<String, List<GroupedExpressions>> _map = new LinkedHashMap<>();
    for (GroupedExpressions pR : resultList) {
      if (!Objects.isNull(pR.getDate())) {
        String dateStr = BaseController.dateFormat.get().format(pR.getDate());
        if (!_map.containsKey(dateStr)) {
          _map.put(dateStr, new ArrayList<>());
        }
        _map.get(dateStr).add(pR);
      }
    }

    return _map;
  }*/

  public static Map<String, List<GroupedGenesExpressions>> partitionGenesResultByDate(
      List<GroupedGenesExpressions> resultList) {
    Map<String, List<GroupedGenesExpressions>> _map = new LinkedHashMap<>();

    for (GroupedGenesExpressions pR : resultList) {
      if (_map.get(pR.getDate()) == null) {
        _map.put(pR.getDate(), new ArrayList<>());

      }
      _map.get(pR.getDate()).add(pR);
    }

    return _map;
  }

  public static DemandRequest constructDemandRequest(String dateFrom, String dateTo,
      String marketIso,
      String contentIds, String metricType, String interval, Boolean benchmarkDe,
      boolean fullMetrics) {

    DemandRequest apiRequest = new DemandRequest();
    apiRequest.setMetricType(metricType);
    apiRequest.setInterval(interval);
    apiRequest.setSortBy("-" + metricType);
    apiRequest.setBenchmark_de(benchmarkDe);

    DataQuery query = new DataQuery();
    query.setDateFrom(CommonsDateUtil.parseDateStr(dateFrom));
    query.setDateTo(CommonsDateUtil.parseDateStr(dateTo));
    query.setMarkets(marketIso + ":sort");
    query.setShowranks(true);
    /* if customer want to see topshows outside of their subscriptions */
    query.setContent(contentIds.equalsIgnoreCase("topshows") ? "allshows" : contentIds);
    apiRequest.setQuery(Arrays.asList(query));

    apiRequest.setFullMetrics(fullMetrics);
    apiRequest.setPage(1);
    apiRequest.setSize(100000);

    return apiRequest;
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    Set<Object> seen = ConcurrentHashMap.newKeySet();
    return t -> seen.add(keyExtractor.apply(t));
  }

  public static boolean isWorldwideMarket(String market) {
    return StringUtils.isNotEmpty(market) && (APIConstants.GLOBAL.contains(market.toLowerCase())
        || APIConstants.WORLDWIDE_CODE.equalsIgnoreCase(market));
  }

  public static List<String> requestLabels(String labelStr) {
    return !StringUtils.isEmpty(labelStr) ? Arrays.asList(
            labelStr.split(ParrotConstants.COMMA_DELIM)).stream()
        .map(String::trim).collect(Collectors.toList()) : null;
  }

  public static String readSQLFile(String file) {
    try {
      return IOUtils.toString(
          Thread.currentThread().getContextClassLoader().getResourceAsStream(file),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      System.out.println("Failed to read SQL file {}" + file);
    }
    return null;

  }

  public static String getExpressionsTableByPrecomputed(PreComputed precomputed) {
    return precomputed2SeriesTable.get(precomputed);
  }

  public static boolean isPeriodPermittedForMonitorTier(String period) {
    return StringUtils.isNotEmpty(period) && Arrays
        .asList(TimePeriod.LATESTDAY.value(), TimePeriod.LAST7DAYS.value(),
            TimePeriod.LAST30DAYS.value(),
            TimePeriod.LAST60DAYS.value(), TimePeriod.ALLTIME.value())
        .contains(period);
  }

  public static boolean isPeriodPermittedForMonitorTierWithoutRank(String period) {
    return StringUtils.isNotEmpty(period) && Arrays.asList(TimePeriod.THISYEAR.value())
        .contains(period);
  }

  public static Class<? extends ISubscription> getSubscriptionTableEntity(Entity entity) {
    if (entity == Entity.TALENT) {
      return TalentSubscription.class;
    } else if (entity == Entity.MOVIE) {
      return MovieSubscription.class;
    }
    return Subscription.class;
  }

  public static String getSubscriptionTableName(Entity entity) {
    if (entity == Entity.TALENT) {
      return TableConstants.TALENT_SUBSCRIPTION;
    } else if (entity == Entity.MOVIE) {
      return TableConstants.MOVIE_SUBSCRIPTION;
    }
    return TableConstants.TV_SERIES_SUBSCRIPTION;
  }

  public static String getBenchmarkTableName(Entity entity) {
    if (entity == Entity.TALENT) {
      return TableConstants.TALENT_BENCHMARK_DEMAND;
    } else if (entity == Entity.MOVIE) {
      return TableConstants.MOVIE_BENCHMARK_DEMAND;
    }
    return TableConstants.BENCHMARK_DEMAND;
  }

  public static SubscriptionType getMetadataSubscriptionType(Entity entity) {
    if (entity == Entity.TALENT) {
      return SubscriptionType.TALENT;
    } else if (entity == Entity.MOVIE) {
      return SubscriptionType.MOVIE;
    }
    return SubscriptionType.TITLE;
  }
}
