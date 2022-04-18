package com.parrotanalytics.api.data.repo.api.custom.impl;

import com.parrotanalytics.api.apidb_model.BenchmarkDemand;
import com.parrotanalytics.api.apidb_model.nonmanaged.BucketTopNExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GenePerformance;
import com.parrotanalytics.api.apidb_model.nonmanaged.GlobalGenrePerformance;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedGenesExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.InGenreRank;
import com.parrotanalytics.api.apidb_model.nonmanaged.ShortIdDateTuple;
import com.parrotanalytics.api.commons.constants.DayOfWeek;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.PreComputed;
import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.data.repo.api.MarketsRepository;
import com.parrotanalytics.api.data.repo.api.custom.DemandRepositoryCustom;
import com.parrotanalytics.api.request.demand.DataQuery;
import com.parrotanalytics.api.request.demand.DemandRequest;
import com.parrotanalytics.api.request.demand.SortInfo;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import com.parrotanalytics.commons.utils.date.DateUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * @author Minh Vu
 */
public class DemandRepositoryImpl implements DemandRepositoryCustom {

  private static final Logger logger = LogManager.getLogger(DemandRepositoryImpl.class);

  private static final String BUCKET_TOPN_QUERY = "sql/bucket_topn_query.sql";

  private static String bucketTopNQuery;

  private static final int CUSTOMER_READY_ACCOUNT = 99;

  private static final String GENRE_COUNTRY_BY_GLOBAL_QUERY = "sql/genres_country_by_global.sql";
  private static String genreCountryByGlobalQuery;
  private static final String GLOBAL_GENRE_PERFORMANCE_QUERY = "sql/global_genre_performance_query.sql";
  private static String globalDemandGenrePerformanceQuery;
  private static final String MARKET_SHARE_OVERALL_QUERY = "sql/market_share_overall_query.sql";
  private static final String MARKET_SHARE_TIMESERIES_QUERY = "sql/market_share_timeseries_query.sql";

  private static String marketShareOverallQuery;
  private static String marketShareTimeseriesQuery;
  private static final String SIMPLE_AVERAGE_BY_SHORTID_COUNTRY_OVERALL_QUERY = "sql/live_native_by_shortid_country_overall_query.sql";
  private static String simpleAverageByShortIdCountryOverallQuery;
  private static final String WEIGHTED_AVERAGE_BY_SHORTID_COUNTRY_OVERALL_QUERY = "sql/native_weighted_by_shortid_country_overall_query.sql";
  private static final String WEIGHTED_AVERAGE_BY_SHORTID_COUNTRY_TIMESERIES_QUERY = "sql/native_weighted_by_shortid_country_timeseries_query.sql";
  private static String weightedAverageByShortIdCountryOverallQuery;
  private static String weightedAverageByShortIdCountryTimeseriesQuery;

  private static List<Integer> internalAccountIDs = new ArrayList<>();

  static {

    simpleAverageByShortIdCountryOverallQuery = DemandHelper
        .readSQLFile(SIMPLE_AVERAGE_BY_SHORTID_COUNTRY_OVERALL_QUERY);
    bucketTopNQuery = DemandHelper.readSQLFile(BUCKET_TOPN_QUERY);
    genreCountryByGlobalQuery = DemandHelper.readSQLFile(GENRE_COUNTRY_BY_GLOBAL_QUERY);
    weightedAverageByShortIdCountryTimeseriesQuery = DemandHelper
        .readSQLFile(WEIGHTED_AVERAGE_BY_SHORTID_COUNTRY_TIMESERIES_QUERY);
    weightedAverageByShortIdCountryOverallQuery = DemandHelper
        .readSQLFile(WEIGHTED_AVERAGE_BY_SHORTID_COUNTRY_OVERALL_QUERY);
    globalDemandGenrePerformanceQuery = DemandHelper.readSQLFile(GLOBAL_GENRE_PERFORMANCE_QUERY);
    marketShareTimeseriesQuery = DemandHelper.readSQLFile(MARKET_SHARE_TIMESERIES_QUERY);
    marketShareOverallQuery = DemandHelper.readSQLFile(MARKET_SHARE_OVERALL_QUERY);
  }

  @Autowired
  private DemandRepository demandDataRepo;

  @PersistenceContext(unitName = "PARROT_API")
  private EntityManager entityManager;

  @Autowired
  public MarketsRepository marketsRepo;

  public List<Integer> getInternalAccountIDs(){
    return this.internalAccountIDs;
  }

  public void addInternalAccountID(Integer internalAccountID){
    this.internalAccountIDs.add(internalAccountID);
  }

  @Override
  public Double benchmarkDemand(String country, Date dateFrom, Date dateTo, Entity entity) {
    Double benchmarkDE;
    String tableName = DemandHelper.getBenchmarkTableName(entity);
    String type =
        country != null && APIConstants.WORLDWIDE_CODE.equalsIgnoreCase(country) ? "worldwide"
            : "market";
    String querySql = String.format(
        "SELECT AVG(benchmark_de) FROM %s.%s WHERE type = #type AND date >= #date_from AND date <= #date_to",
        TableConstants.METRICS_SCHEMA, tableName);
    Query nativeQuery = entityManager.createNativeQuery(querySql);
    nativeQuery.setParameter("type", type);
    nativeQuery.setParameter("date_from", CommonsDateUtil.formatDate(dateFrom));
    nativeQuery.setParameter("date_to", CommonsDateUtil.formatDate(dateTo));
    benchmarkDE = Double.parseDouble(nativeQuery.getSingleResult().toString());

    if (benchmarkDE == null) {
      benchmarkDE = demandDataRepo.defaultBenchmarkDemand(country);
    }

    return benchmarkDE;
  }

  @Override
  public List<BenchmarkDemand> dailyBenchmarkDemandByCountry(String country, Date dateFrom,
      Date dateTo,
      Entity entity) {
    List<BenchmarkDemand> result;
    String tableName = Entity.TALENT == entity ? "talent_benchmark_demand" : "benchmark_demand";

    String querySql = String.format(
        "SELECT date,benchmark_de, market FROM metrics.%s WHERE market = #market AND date >= #date_from AND date <= #date_to",
        tableName);
    Query nativeQuery = entityManager.createNativeQuery(querySql, BenchmarkDemand.class);
    nativeQuery.setParameter("market", country);
    nativeQuery.setParameter("date_from", CommonsDateUtil.formatDate(dateFrom));
    nativeQuery.setParameter("date_to", CommonsDateUtil.formatDate(dateTo));
    result = nativeQuery.getResultList();

    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Page<BucketTopNExpressions> bucketTopNDemandOverall(String market, Date date, int topN,
      Integer callAccountID) {
    String liveQuery = bucketTopNQuery;

    String dateStr = CommonsDateUtil.formatDate(date);

    Query nativeQuery = entityManager.createNativeQuery(liveQuery, BucketTopNExpressions.class);

    if (!CollectionUtils.isEmpty(internalAccountIDs)
        && internalAccountIDs.contains(callAccountID)) {
      nativeQuery.setParameter("account_id", callAccountID);
    } else {
      nativeQuery.setParameter("account_id", CUSTOMER_READY_ACCOUNT);
    }

    nativeQuery.setParameter("iso_code", market);
    nativeQuery.setParameter("date", dateStr);
    nativeQuery.setParameter("topn", topN);

    List<BucketTopNExpressions> resultList = nativeQuery.getResultList();

    return new PageImpl<>(resultList);
  }

  public Map<Long, Double> benchmarkDemandAtPeakByMarketCountry(String country, Date dateFrom,
      Date dateTo,
      List<Long> shortIds, Entity entity) {

    List<Date> dateRangeList = DateUtils.toDateRangeList(new DateTime(dateFrom),
        new DateTime(dateTo));
    PageRequest pageRequest = PageRequest.of(0, dateRangeList.size() * shortIds.size(),
        SortInfo.createSort("max", APIConstants.DEXPERCAPITA, "desc"));

    List<GroupedExpressions> listExprs = demandDataRepo.intervalDemandByShortIdCountryAsTimeseries(
        Interval.DAILY,
        dateRangeList, Arrays.asList(country), shortIds, pageRequest, entity, null, null);
    Map<Long, List<GroupedExpressions>> dailyExs = listExprs.stream()
        .collect(Collectors.groupingBy(GroupedExpressions::getShort_id));
    Map<Long, Double> result = new HashMap<>();
    for (Map.Entry<Long, List<GroupedExpressions>> entry : dailyExs.entrySet()) {
      GroupedExpressions peakEx = entry.getValue().stream()
          .max(Comparator.comparing(GroupedExpressions::getDexpercapita))
          .orElse(entry.getValue().get(0));
      Double benchmarkAtPeak = null;
      if (peakEx != null) {
        Date peakDate = peakEx.getDate();
        benchmarkAtPeak = demandDataRepo.benchmarkDemand(country, peakDate, peakDate, entity);
      } else {
        benchmarkAtPeak = demandDataRepo.defaultBenchmarkDemand(country);
      }
      result.put(entry.getKey(), benchmarkAtPeak);
    }
    return result;
  }

  public GroupedExpressions calculateWeightAvgGroupedExp(
      Map<String, Long> mapIso2CountryPopultation,
      List<GroupedExpressions> expressionsPerShortId, Long shortId, Date date) {
    double weightedDexSum = 0.0;
    double weightedDexpercapitaSum = 0.0;
    double peak_dexpercapita = 0.0;
    double peak_dex = 0.0;
    long populationSum = 0;

    for (GroupedExpressions ex : expressionsPerShortId) {
      if (mapIso2CountryPopultation.containsKey(ex.getCountry())) {
        Long marketPop = mapIso2CountryPopultation.get(ex.getCountry());
        weightedDexSum += ex.getDex() * marketPop;
        weightedDexpercapitaSum += ex.getDexpercapita() * marketPop;
        // there is no definition of peak of region, so
        // we simply
        // pick peak of all peaks in region
        peak_dexpercapita = Math.max(peak_dexpercapita, ex.getPeak_dexpercapita());
        peak_dex = Math.max(peak_dex, ex.getPeak_dex());
        populationSum += marketPop;
      }

    }
    return new GroupedExpressions(date, shortId, null, weightedDexpercapitaSum / populationSum,
        peak_dexpercapita,
        weightedDexSum / populationSum, peak_dex, 0, 0, 0, 0, 1);
  }

  @Override
  public List<GroupedExpressions> convertWeightedAverage(List<String> markets,
      List<GroupedExpressions> groupedExpressions) {
    Map<String, Long> mapIso2CountryPopultation = marketsRepo.getAllIsoCountryPopulationMap();

    Map<String, Long> mapIso2CountryPopOnlyMarkets = mapIso2CountryPopultation.entrySet().stream()
        .filter(entry -> markets.contains(entry.getKey()))
        .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));

    // grouping expressions by each title(short_id)
    Map<ShortIdDateTuple, List<GroupedExpressions>> expressionsByShortId = groupedExpressions.stream()
        .collect(Collectors.groupingBy(ex -> new ShortIdDateTuple(ex.getDate(), ex.getShort_id())));

    List<GroupedExpressions> finalResult = expressionsByShortId.entrySet().stream().map(entry -> {
      List<GroupedExpressions> expressionsPerShortId = entry.getValue();
      Long shortId = entry.getKey().getShortId();
      Date date = entry.getKey().getDate();
      return calculateWeightAvgGroupedExp(mapIso2CountryPopOnlyMarkets, expressionsPerShortId,
          shortId, date);
    }).collect(Collectors.toList());
    return finalResult;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Page<GenePerformance> genePerformance(String gene, String market, Date date) {
    String liveQuery = new StringBuilder(genreCountryByGlobalQuery).toString();
    liveQuery = liveQuery.replace(":table_name:", TableConstants.EXPRESSIONS_DAILY);
    liveQuery = liveQuery.replace(":metrics_schema:", TableConstants.METRICS_SCHEMA);

    String dateStr = CommonsDateUtil.formatDate(date);

    Query nativeQuery = entityManager.createNativeQuery(liveQuery, GenePerformance.class);

    nativeQuery.setParameter("gene", gene);
    nativeQuery.setParameter("iso_code", market);
    nativeQuery.setParameter("date", dateStr);

    List<GenePerformance> resultList = nativeQuery.getResultList();

    return new PageImpl<>(resultList);
  }

  @SuppressWarnings("unchecked")
  public List<GlobalGenrePerformance> getGenrePerformance(List<Date> dateRanges,
      List<Long> shortIds, String genre) {
    String liveQuery = new StringBuilder(globalDemandGenrePerformanceQuery).toString();
    liveQuery = liveQuery.replace(":expressions_daily:", TableConstants.EXPRESSIONS_DAILY);
    liveQuery = liveQuery.replace(":metrics_schema:", TableConstants.METRICS_SCHEMA);

    Query nativeQuery = entityManager.createNativeQuery(liveQuery, GlobalGenrePerformance.class);
    nativeQuery.setParameter("genres", Arrays.asList(genre));
    nativeQuery.setParameter("shortIDList", shortIds);
    nativeQuery.setParameter("dateRangeList", dateRanges);
    return nativeQuery.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<InGenreRank> getInGenreRank(List<Date> dateRanges, String market, Long shortId,
      Entity entity) {

    String nativeSql =
        "SELECT genevalue as genre, ( "
            + "    SELECT overall_rank FROM ( "
            + "        SELECT short_id, RANK() OVER (ORDER BY raw_de DESC ) as overall_rank "
            + "        FROM ( "
            + "            SELECT e.short_id as short_id, AVG(raw_de) AS raw_de "
            + "            FROM :expressions_daily: e JOIN portal.:catalog_genes: c ON e.short_id = c.short_id AND c.gene = 'genre_tags' "
            + "            WHERE date IN #dateRanges AND country = #market AND c.genevalue = cg.genevalue "
            + "            GROUP BY e.short_id "
            + "        ) AS raw "
            + "    ) AS ranks "
            + "    WHERE short_id = cg.short_id "
            + ") AS rank_by_genre "
            + "FROM portal.:catalog_genes: cg WHERE cg.gene = 'genre_tags' AND short_id = #shortId";

    nativeSql = nativeSql.replace(":expressions_daily:",
        DemandHelper.getExpressionsTableWithSchemaByInterval(Interval.DAILY, entity));
    nativeSql = nativeSql.replace(":catalog_genes:",
        DemandHelper.getCatalogGenesTableByEntity(entity));
    List<String> strDateRanges = dateRanges.stream().map(date -> CommonsDateUtil.formatDateTime(date)).collect(
        Collectors.toList());
    Query nativeQuery = entityManager.createNativeQuery(nativeSql, InGenreRank.class);
    nativeQuery.setParameter("dateRanges", strDateRanges);
    nativeQuery.setParameter("market", market);
    nativeQuery.setParameter("shortId", shortId);

    return nativeQuery.getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<GroupedGenesExpressions> groupedByGenesData(String dynamicGroupingQuery) {
    List<GroupedGenesExpressions> data = null;

    Boolean isSingleGroupingData = null;

    List<Object[]> resultList = entityManager.createNativeQuery(dynamicGroupingQuery)
        .getResultList();

    if (CollectionUtils.isNotEmpty(resultList)) {
      data = new ArrayList<>();

      for (Object[] record : resultList) {
        if (isSingleGroupingData == null) {
          isSingleGroupingData = record.length == 4;
        }

        GroupedGenesExpressions groupedGenesExpressions = null;

        if (isSingleGroupingData.booleanValue()) {
          groupedGenesExpressions = new GroupedGenesExpressions(((Date) record[0]),
              ((String) record[1]),
              ((String) record[2]), ((Double) record[3]));
        } else {
          groupedGenesExpressions = new GroupedGenesExpressions(((Date) record[0]),
              ((String) record[1]),
              ((String) record[2]), ((String) record[3]), ((Double) record[4]));
        }

        data.add(groupedGenesExpressions);
      }

    }

    return data;
  }

  @Override
  public List<GroupedExpressions> intervalDemandAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE,
      Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, 0 as short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids :having_clause: ORDER BY AVG(raw_de) DESC";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDList, pageRequest, nativeSql,
        entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandAsTimeSeries(Interval interval,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs,
      PageRequest pageRequest, Entity entity, Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT date, 0 as short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids "
            + "GROUP BY date :having_clause: ORDER BY date ASC";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDs, pageRequest, nativeSql,
        entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandAsTimeSeriesWithStartOfWeek(DayOfWeek startOfWeek,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs,
      PageRequest pageRequest, Entity entity, Double benchmarkDE, Double minMultiplier) {

    dateRangeList = transformDateRangeListToWeeks(startOfWeek, dateRangeList);
    if (dateRangeList.isEmpty()) {
      return Collections.emptyList();
    }
    String nativeSql =
        "SELECT date, 0 as short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM  ( " + expressionWeeklyByStartOfWeek(startOfWeek) + " ) weekly "
            + "GROUP BY date :having_clause: ORDER BY date ASC";
    return queryNativeSql(Interval.WEEKLY, dateRangeList, marketsList, shortIDs, pageRequest,
        nativeSql, entity, benchmarkDE, minMultiplier);
  }

  /**
   * Generate query that derives weekly data from daily table, with the exceptions of not providing
   * overall_rank and rank_by_peak
   *
   * @param startOfWeek DayOfWeek
   * @return sub query that can be used to form other queries
   */
  protected String expressionWeeklyByStartOfWeek(DayOfWeek startOfWeek) {
    String nativeSql =
        " SELECT country, short_id, SUM(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, SUM(real_de) AS real_de, MAX(real_de) AS peak_real_de, MIN(overall_rank) AS best_rank, max(date) as date, '' as range_key, 7 as num_days, 0 as overall_rank, 0 as rank_by_peak "
            + "  FROM :table_name: FORCE INDEX(idx_country) " + "  WHERE date IN #date_ranges "
            + "  AND short_id IN #short_ids " + "  AND country IN #markets "
            + "  GROUP BY short_id, country, FROM_DAYS(TO_DAYS(date) - MOD(TO_DAYS(date) + :offset:, 7)) "
            + "  ORDER BY AVG(raw_de) DESC ";
    String tableName = DemandHelper.getExpressionsTableWithSchemaByInterval(Interval.DAILY,
        Entity.TV_SERIES);
    nativeSql = nativeSql.replace(":table_name:", tableName);
    nativeSql = nativeSql.replace(":offset:", String.valueOf(startOfWeek.getOffset()));
    return nativeSql;
  }

  protected String expressionDailyByCustomIntervalRange(DayOfWeek startOfWeek, Integer customIntervalRange) {
    String nativeSql =
        " SELECT country, short_id, SUM(raw_de) AS raw_de, MAX(raw_de) AS peak_raw_de, SUM(real_de) AS real_de, MAX(real_de) AS peak_real_de, MIN"
            + "(overall_rank) AS best_rank, max(date) as date, '' as range_key, :custom_interval_range: as num_days, 0 as overall_rank, 0 as rank_by_peak "
            + "  FROM :table_name: FORCE INDEX(idx_country) " + "  WHERE date IN #date_ranges "
            + "  AND short_id IN #short_ids " + "  AND country IN #markets "
            + "  GROUP BY short_id, country, FROM_DAYS(TO_DAYS(date) - MOD(TO_DAYS(date) + :offset:, :custom_interval_range:)) "
            + "  ORDER BY AVG(raw_de) DESC ";
    String tableName = DemandHelper.getExpressionsTableWithSchemaByInterval(Interval.DAILY,
        Entity.TV_SERIES);
    nativeSql = nativeSql.replace(":table_name:", tableName);
    nativeSql = nativeSql.replace(":custom_interval_range:", Integer.toString(customIntervalRange));
    nativeSql = nativeSql.replace(":offset:", String.valueOf(startOfWeek.getOffset()));
    return nativeSql;
  }

  /**
   * Generate date range list that is formed by custom defined weeks. weeks are defined to be 7 days
   * from startOfWeek
   *
   * @param startOfWeek   DayOfWeek
   * @param dateRangeList dates consist of complete custom weeks
   * @return
   */
  protected List<Date> transformDateRangeListToWeeks(DayOfWeek startOfWeek,
      List<Date> dateRangeList) {
    int endOfWeekDay = (startOfWeek.getCalendarValue() + 5) % 7 + 1;
    return dateRangeList.stream().filter(date -> {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      return cal.get(Calendar.DAY_OF_WEEK) == endOfWeekDay;
    }).flatMap(date -> {
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      cal.add(Calendar.DAY_OF_WEEK, -7);
      return IntStream.range(0, 7).boxed().map(i -> {
        cal.add(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
      });
    }).collect(Collectors.toList());
  }

  @Override
  public List<GroupedExpressions> intervalDemandByCountryAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE,
      Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, 0 as short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids "
            + "GROUP BY country :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDList, pageRequest, nativeSql,
        entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdAsTimeseries(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String hql =
        "SELECT date, short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex, "
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids "
            + "GROUP BY date, short_id :having_clause: ORDER BY date :direction:";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDList, pageRequest, hql,
        entity, benchmarkDE,
        minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdAsTimeseriesWithStartOfWeek(
      DayOfWeek startOfWeek,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDList,
      PageRequest pageRequest,
      Entity entityString, Double benchmarkDE, Double minMultiplier) {

    dateRangeList = transformDateRangeListToWeeks(startOfWeek, dateRangeList);
    if (dateRangeList.isEmpty()) {
      return Collections.emptyList();
    }
    String hql =
        "SELECT date, short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex, "
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM ( " + expressionWeeklyByStartOfWeek(startOfWeek) + " ) weekly "
            + "GROUP BY date, short_id :having_clause: ORDER BY date :direction:";
    return queryNativeSql(Interval.WEEKLY, dateRangeList, marketsList, shortIDList, pageRequest,
        hql, entityString,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdAsTimeseriesWithCustomInterval(Interval interval,
      DayOfWeek startOfWeek, Optional<Integer> customIntervalRange,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDList,
      PageRequest pageRequest,
      Entity entityString, Double benchmarkDE, Double minMultiplier) {

    int customIntervalRangeValue = customIntervalRange.isPresent() ? customIntervalRange.get()  : 7;

    dateRangeList = transformDateRangeListToWeeks(startOfWeek, dateRangeList);
    if (dateRangeList.isEmpty()) {
      return Collections.emptyList();
    }
    String hql =
        "SELECT date, short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex, "
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM ( " + expressionDailyByCustomIntervalRange(startOfWeek, customIntervalRangeValue) + " ) weekly "
            + "GROUP BY date, short_id :having_clause: ORDER BY date :direction:";
    return queryNativeSql(Interval.WEEKLY, dateRangeList, marketsList, shortIDList, pageRequest,
        hql, entityString,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdCountryAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Double benchmarkDE,
      Double minMultiplier) {
    return intervalDemandByShortIdCountryAsOverall(interval, dateRangeList, marketsList,
        shortIDList, pageRequest,
        Entity.TV_SERIES, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseries(Interval interval,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs, PageRequest pageable,
      Double benchmarkDE, Double minMultiplier) {
    return intervalDemandByShortIdCountryAsTimeseries(interval, dateRangeList, marketsList,
        shortIDs, pageable,
        Entity.TV_SERIES, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdCountryAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids "
            + "GROUP BY country, short_id :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDList, pageRequest, nativeSql,
        entity,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseries(Interval interval,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs, PageRequest pageable,
      Entity entityString, Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT date, short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids "
            + "GROUP BY date, country, short_id :having_clause: ORDER BY date :direction:";


    return queryNativeSql(interval, dateRangeList, marketsList, shortIDs, pageable, nativeSql,
        entityString,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseriesWithStartOfWeek(
      DayOfWeek startOfWeek,
      List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs, PageRequest pageable,
      Entity entity, Double benchmarkDE, Double minMultiplier) {

    dateRangeList = transformDateRangeListToWeeks(startOfWeek, dateRangeList);
    if (dateRangeList.isEmpty()) {
      return Collections.emptyList();
    }
    String nativeSql =
        "SELECT date, short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM ( " + expressionWeeklyByStartOfWeek(startOfWeek) + " ) weekly "
            + "GROUP BY date, country, short_id :having_clause: ORDER BY date :direction:";

    return queryNativeSql(Interval.WEEKLY, dateRangeList, marketsList, shortIDs, pageable,
        nativeSql, entity,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> precomputedDemandAsOverall(PreComputed preComputed, Date dateTo,
      String rangeKey,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, 0 as short_id, MAX(country) as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date = #date_to AND range_key = #range_key "
            + "AND country IN #markets AND short_id IN #short_ids :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(preComputed, dateTo, rangeKey, marketsList, shortIDList, pageRequest,
        nativeSql, entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> precomputedDemandByCountryAsOverall(PreComputed preComputed,
      Date dateTo,
      String rangeKey, List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest,
      Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, 0 as short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date = #date_to AND range_key = #range_key "
            + "AND country IN #markets AND short_id IN #short_ids GROUP BY country :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(preComputed, dateTo, rangeKey, marketsList, shortIDList, pageRequest,
        nativeSql, entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> precomputedDemandByShortIdAsTimeseries(PreComputed preComputed,
      Date dateTo,
      String rangeKey, List<String> marketsList, List<Long> shortIDList, PageRequest pageable,
      Entity entity,
      Double benchmarkDE,
      Double minMultiplier) {
    String nativeSql =
        "SELECT date, short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date = #date_to AND range_key = #range_key "
            + "AND country IN #markets AND short_id IN #short_ids GROUP BY date, short_id :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(preComputed, dateTo, rangeKey, marketsList, shortIDList, pageable,
        nativeSql, entity, benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> precomputedDemandByShortIdCountryAsTimeseries(
      PreComputed precomputed, Date dateTo,
      String rangeKey, List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest,
      Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT date, short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date = #date_to AND range_key = #range_key "
            + "AND country IN #markets AND short_id IN #short_ids GROUP BY date, short_id, country :having_clause: ORDER BY :order_by: :direction:";
    return queryNativeSql(precomputed, dateTo, rangeKey, marketsList, shortIDList, pageRequest,
        nativeSql, entity,
        benchmarkDE, minMultiplier);
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String nativeSql =
        "SELECT MAX(date) as date, short_id, '' as country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date IN #date_ranges AND country IN #markets AND short_id IN #short_ids GROUP BY :having_clause: short_id ORDER BY :order_by: :direction:";
    return queryNativeSql(interval, dateRangeList, marketsList, shortIDList, pageRequest, nativeSql,
        entity,
        benchmarkDE, minMultiplier);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<GroupedExpressions> intervalDemandMarketShareAsOverall(DemandRequest apiRequest,
      PageRequest pageRequest, Double benchmarkDE, Double minMultiplier) {
    String liveQuery = marketShareOverallQuery;

    String tableName = apiRequest.getPrecomputed() != null
        ? DemandHelper.getExpressionsTableByPrecomputed(apiRequest.getPrecomputed())
        : DemandHelper.getExpressionsTableByInterval(Interval.fromValue(apiRequest.getInterval()));

    Sort sort = pageRequest.getSort();

    Iterator<Sort.Order> sortItr = sort.iterator();

    DataQuery dataQuery = apiRequest.getDataQuery();

    while (sortItr.hasNext()) {
      Sort.Order order = sortItr.next();
      liveQuery = liveQuery.replace(":sort_by:", apiRequest.getSortBy());
      liveQuery = liveQuery.replace(":direction:", order.getDirection().toString());
    }
    liveQuery = liveQuery.replace(":table_name:", tableName);
    liveQuery = liveQuery.replace(":peak_raw_de:",
        Interval.DAILY.value().equals(apiRequest.getInterval()) ? "raw_de" : "peak_raw_de");
    liveQuery = liveQuery.replace(":peak_real_de:",
        Interval.DAILY.value().equals(apiRequest.getInterval()) ? "real_de" : "peak_real_de");
    liveQuery = liveQuery.replace(":date_condition:",
        apiRequest.getPrecomputed() != null ? "range_key = #range_key" : "date IN #date_ranges");
    liveQuery = liveQuery.replace(":days_count:",
        apiRequest.getPrecomputed() != null ? Integer.toString(1) : "COUNT(distinct date)");
    liveQuery = liveQuery.replace(":page:", String.valueOf(pageRequest.getPageNumber()));
    liveQuery = liveQuery.replace(":size:", String.valueOf(pageRequest.getPageSize()));
    Query nativeQuery = entityManager.createNativeQuery(liveQuery, GroupedExpressions.class);

    nativeQuery.setParameter("markets", dataQuery.getMarketsList());
    nativeQuery.setParameter("short_ids", dataQuery.getShortIDsList());
    nativeQuery.setParameter("range_key", dataQuery.getPeriod());
    nativeQuery.setParameter("date_ranges", dataQuery.getDateRangeList());
    nativeQuery.setParameter("others_short_ids", dataQuery.getOthersShortIDsList());

    return nativeQuery.getResultList();

  }

  @Override
  public Date latestDemandDate() {
    return demandDataRepo.maxDemandDate();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<GroupedExpressions> marketShareTimeseries(DemandRequest apiRequest,
      PageRequest pageRequest) {
    String liveQuery = new StringBuilder(marketShareTimeseriesQuery).toString();
    String tableName = DemandHelper.getExpressionsTableByInterval(
        Interval.fromValue(apiRequest.getInterval()));
    DataQuery dataQuery = apiRequest.getDataQuery();
    Date dateFrom = dataQuery.getDateFrom().toDate();
    Date dateTo = dataQuery.getDateTo().toDate();
    liveQuery = injectNativeQueryPlaceholders(Interval.fromValue(apiRequest.getInterval()),
        liveQuery, dateFrom,
        dateTo, tableName, null, null);

    Sort sort = pageRequest.getSort();

    Iterator<Sort.Order> sortItr = sort.iterator();

    while (sortItr.hasNext()) {
      Sort.Order order = sortItr.next();
      String sortProperty = order.getProperty();
      liveQuery = liveQuery.replace(":order_by_field:", sortProperty);
      liveQuery = liveQuery.replace(":direction:", order.getDirection().toString());
    }
    liveQuery = liveQuery.replace(":table_name:", tableName);
    liveQuery = liveQuery.replace(":page:", String.valueOf(pageRequest.getPageNumber()));
    liveQuery = liveQuery.replace(":size:", String.valueOf(pageRequest.getPageSize()));

    Query nativeQuery = entityManager.createNativeQuery(liveQuery, GroupedExpressions.class);

    nativeQuery.setParameter("markets", dataQuery.getMarketsList());
    nativeQuery.setParameter("short_ids", dataQuery.getShortIDsList());
    nativeQuery.setParameter("others_short_ids", dataQuery.getOthersShortIDsList());
    nativeQuery.setParameter("date_ranges", dataQuery.getDateRangeList());
    nativeQuery.setParameter("benchmark",
        apiRequest.isBenchmark_de()
            ? demandDataRepo.benchmarkDemand(dataQuery.getMarketsList().get(0),
            dataQuery.getDateFrom().toDate(), dataQuery.getDateTo().toDate(), Entity.TV_SERIES)
            : 0.0);

    return nativeQuery.getResultList();
  }

  @Override
  public List<GroupedExpressions> liveWeightedDemandByShortIdAsOverall(DemandRequest apiRequest,
      PageRequest pageRequest, Double benchmarkDE, Double minMultiplier) {
    return weightedDemandNativeQuery(apiRequest, pageRequest,
        new StringBuilder(weightedAverageByShortIdCountryOverallQuery).toString());

  }

  @SuppressWarnings("unchecked")
  protected List<GroupedExpressions> weightedDemandNativeQuery(DemandRequest apiRequest,
      PageRequest pageRequest,
      String nativeSql) {
    String tableName = DemandHelper.getExpressionsTableByInterval(
        Interval.fromValue(apiRequest.getInterval()));

    Sort sort = pageRequest.getSort();
    Iterator<Order> sortItr = sort.iterator();
    DataQuery dataQuery = apiRequest.getDataQuery();
    Date dateFrom = dataQuery.getDateFrom().toDate();
    Date dateTo = dataQuery.getDateTo().toDate();
    List<Date> dateRangeList = dataQuery.getDateRangeList();
    DayOfWeek dayOfWeek = DayOfWeek.fromValue(apiRequest.getStartOfInterval());
    if (apiRequest.isCustomStartOfInterval()) {
      dateRangeList = transformDateRangeListToWeeks(dayOfWeek, dateRangeList);
      nativeSql = nativeSql.replace(":table_name:",
          " ( " + expressionWeeklyByStartOfWeek(dayOfWeek) + " ) weekly ");
    }
    List<String> dateRanges = dateRangeList.stream().map(d -> CommonsDateUtil.formatDate(d))
        .collect(Collectors.toList());
    nativeSql = injectNativeQueryPlaceholders(Interval.fromValue(apiRequest.getInterval()),
        nativeSql, dateFrom,
        dateTo, tableName, apiRequest.getBenchmarkDE(), apiRequest.getMinimumMultiplier());
    nativeSql = nativeSql.replace(":date_to:", CommonsDateUtil.formatDate(dateTo));
    nativeSql = nativeSql.replace(":countries:",
        StringUtils.join(dataQuery.getMarketsList(), APIConstants.DELIM_COMMA));

    while (sortItr.hasNext()) {
      Order order = sortItr.next();

      if (apiRequest.getMetricType().startsWith("peak_")) {
        nativeSql = nativeSql.replace(":order_by_field:", "peak_dex");
      } else {
        nativeSql = nativeSql.replace(":order_by_field:", "dex");
      }
      nativeSql = nativeSql.replace(":direction:", order.getDirection().toString());
    }

    nativeSql = nativeSql.replace(":page:", String.valueOf(pageRequest.getPageNumber()));
    nativeSql = nativeSql.replace(":size:", String.valueOf(pageRequest.getPageSize()));

    Query nativeQuery = entityManager.createNativeQuery(nativeSql, GroupedExpressions.class);

    if (!CollectionUtils.isEmpty(internalAccountIDs)
        && internalAccountIDs.contains(apiRequest.getApiAccountId())) {
      nativeQuery.setParameter("account_id", apiRequest.getApiAccountId());
    } else {
      nativeQuery.setParameter("account_id", CUSTOMER_READY_ACCOUNT);
    }

    nativeQuery.setParameter("iso_code", dataQuery.getMarketsList());
    nativeQuery.setParameter("markets", dataQuery.getMarketsList());
    nativeQuery.setParameter("short_ids", dataQuery.getShortIDsList());
    nativeQuery.setParameter("content_ids", dataQuery.getShortIDsList());
    nativeQuery.setParameter("date_ranges", dateRanges);
    nativeQuery.setParameter("date_from", CommonsDateUtil.formatDate(dateFrom));
    nativeQuery.setParameter("date_to", CommonsDateUtil.formatDate(dateTo));
    return nativeQuery.getResultList();
  }

  private String injectNativeQueryPlaceholders(PreComputed preComputed, String nativeSql,
      String rangeKey,
      Date dateTo, String tableName, Double benchmarkDE, Double minMultiplier) {
    nativeSql = nativeSql.replace(":table_name:", tableName);
    nativeSql = nativeSql.replace(":metrics_schema:", TableConstants.METRICS_SCHEMA);
    String raw_de = SortInfo.NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS;
    String peak_raw_de = SortInfo.NATIVE_PEAK_DEXPERCAPITA;
    String real_de = SortInfo.NATIVE_SUM_DEX_BY_SUM_NUM_DAYS;
    String peak_real_de = SortInfo.NATIVE_PEAK_DEX;
    String overall_rank = SortInfo.NATIVE_MIN_OVERALL_RANK;
    String best_rank = SortInfo.NATIVE_MIN_BEST_RANK;
    String rank_by_peak = SortInfo.NATIVE_MIN_RANK_BY_PEAK;
    String num_days = SortInfo.NATIVE_SUM_DAYS;
    nativeSql = nativeSql.replace(":raw_de:", raw_de);
    nativeSql = nativeSql.replace(":peak_raw_de:", peak_raw_de);
    nativeSql = nativeSql.replace(":real_de:", real_de);
    nativeSql = nativeSql.replace(":date_to:", CommonsDateUtil.formatDate(dateTo));
    nativeSql = nativeSql.replace(":peak_real_de:",
        String.format("IFNULL(%s,%d)", peak_real_de, 0));
    nativeSql = nativeSql.replace(":overall_rank:",
        String.format("IFNULL(%s,%d)", overall_rank, 0));
    nativeSql = nativeSql.replace(":best_rank:", String.format("IFNULL(%s,%d)", best_rank, 0));
    nativeSql = nativeSql.replace(":rank_by_peak:", rank_by_peak);
    nativeSql = nativeSql.replace(":num_days:", num_days);
    nativeSql = nativeSql.replace(":having_clause:",
        benchmarkDE != null && minMultiplier != null
            ? " HAVING dexpercapita /" + Double.toString(benchmarkDE) + " >= "
            + Double.toString(minMultiplier)
            : "");
    return nativeSql;
  }

  protected String injectNativeQueryPlaceholders(Interval interval, String nativeSql, Date dateFrom,
      Date dateTo,
      String tableName, Double benchmarkDE, Double minMultiplier) {

    nativeSql = nativeSql.replace(":table_name:", tableName);
    nativeSql = nativeSql.replace(":metrics_schema:", TableConstants.METRICS_SCHEMA);

    String raw_de = Interval.DAILY == interval ? SortInfo.NATIVE_AVG_DEXPERCAPITA
        : SortInfo.NATIVE_SUM_DEXPERCAPITA_BY_SUM_NUM_DAYS;
    String peak_raw_de = Interval.DAILY == interval ? SortInfo.NATIVE_MAX_DEXPERCAPITA
        : SortInfo.NATIVE_PEAK_DEXPERCAPITA;
    String real_de = Interval.DAILY == interval ? SortInfo.NATIVE_AVG_DEX
        : SortInfo.NATIVE_SUM_DEX_BY_SUM_NUM_DAYS;
    String peak_real_de =
        Interval.DAILY == interval ? SortInfo.NATIVE_MAX_DEX : SortInfo.NATIVE_PEAK_DEX;
    String overall_rank = SortInfo.NATIVE_MIN_OVERALL_RANK;
    String best_rank = Interval.DAILY == interval ? SortInfo.NATIVE_MIN_OVERALL_RANK
        : SortInfo.NATIVE_MIN_BEST_RANK;
    String rank_by_peak = Interval.DAILY == interval ? SortInfo.NATIVE_MIN_OVERALL_RANK
        : SortInfo.NATIVE_MIN_RANK_BY_PEAK;
    String num_days = Interval.DAILY == interval ? SortInfo.SUM_ONE : SortInfo.NATIVE_SUM_DAYS;

    nativeSql = nativeSql.replace(":raw_de:", raw_de);
    nativeSql = nativeSql.replace(":peak_raw_de:", peak_raw_de);
    nativeSql = nativeSql.replace(":real_de:", real_de);
    nativeSql = nativeSql.replace(":date_to:", CommonsDateUtil.formatDate(dateTo));
    nativeSql = nativeSql.replace(":peak_real_de:",
        String.format("IFNULL(%s,%d)", peak_real_de, 0));
    nativeSql = nativeSql.replace(":overall_rank:",
        String.format("IFNULL(%s,%d)", overall_rank, 0));
    nativeSql = nativeSql.replace(":best_rank:", String.format("IFNULL(%s,%d)", best_rank, 0));
    nativeSql = nativeSql.replace(":rank_by_peak:", rank_by_peak);
    nativeSql = nativeSql.replace(":num_days:", num_days);
    nativeSql = nativeSql.replace(":having_clause:",
        benchmarkDE != null && minMultiplier != null
            ? " HAVING dexpercapita /" + Double.toString(benchmarkDE) + " >= "
            + Double.toString(minMultiplier)
            : "");

    return nativeSql;
  }

  @Override
  public Page<GroupedExpressions> precomputedDemandByShortIdCountryOverall(DemandRequest apiRequest,
      PageRequest pageRequest, Entity entity, Double benchmarkDE, Double minMultiplier) {
    DataQuery dataQuery = apiRequest.getDataQuery();
    String nativeSql =
        "SELECT MAX(date) as date, short_id, country, :raw_de: as dexpercapita, :peak_raw_de: as peak_dexpercapita, :real_de: as dex, :peak_real_de: as peak_dex,"
            + ":overall_rank: as overall_rank, :rank_by_peak: as rank_by_peak, :best_rank: as best_rank, :num_days: as num_days "
            + "FROM :table_name: FORCE INDEX(idx_country) "
            + "WHERE date = #date_to AND range_key = #range_key "
            + "AND country IN #markets AND short_id IN #short_ids GROUP BY short_id, country :having_clause: ORDER BY :order_by: :direction:";
    List<GroupedExpressions> resultList = queryNativeSql(
        Interval.fromValue(apiRequest.getInterval()),
        dataQuery.getDateRangeList(), dataQuery.getMarketsList(), dataQuery.getShortIDsList(),
        pageRequest,
        nativeSql, entity, benchmarkDE, minMultiplier);

    return new PageImpl<>(resultList);
  }

  @SuppressWarnings("unchecked")
  protected List<GroupedExpressions> queryNativeSql(PreComputed preComputed, Date dateTo,
      String rangeKey,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, String nativeSql,
      Entity entityString, Double benchmarkDE, Double minMultiplier) {
    String tableName = DemandHelper.getExpressionsTableByPrecomputed(preComputed, entityString);
    nativeSql = injectNativeQueryPlaceholders(preComputed, nativeSql, rangeKey, dateTo, tableName,
        benchmarkDE,
        minMultiplier);

    Sort sort = pageRequest.getSort();
    Iterator<Order> sortItr = sort.iterator();

    while (sortItr.hasNext()) {
      Order order = sortItr.next();
      String orderBy = order.getProperty();
      nativeSql = nativeSql.replace(":order_by:", orderBy);
      nativeSql = nativeSql.replace(":direction:", order.getDirection().toString());
    }

    nativeSql = nativeSql.replace(":page:", String.valueOf(pageRequest.getPageNumber()));
    nativeSql = nativeSql.replace(":size:", String.valueOf(pageRequest.getPageSize()));

    Query nativeQuery = entityManager.createNativeQuery(nativeSql, GroupedExpressions.class)
        .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
    nativeQuery.setParameter("date_to", CommonsDateUtil.formatDate(dateTo));
    nativeQuery.setParameter("range_key", rangeKey);
    nativeQuery.setParameter("markets", marketsList);
    nativeQuery.setParameter("short_ids", shortIDList);

    nativeQuery.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
    List<GroupedExpressions> result = new ArrayList<>();
    nativeQuery.setMaxResults(pageRequest.getPageSize());
    try {
      result = nativeQuery.getResultList();
    } catch (PersistenceException e) {
      logger.error("Exception occurs in queryNativeSql", e);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  protected List<GroupedExpressions> queryNativeSql(Interval interval, List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, String nativeSql,
      Entity entity,
      Double benchmarkDE, Double minMultiplier) {
    String tableName = DemandHelper.getExpressionsTableWithSchemaByInterval(interval, entity);
    nativeSql = injectNativeQueryPlaceholders(interval, nativeSql, dateRangeList.get(0),
        dateRangeList.get(dateRangeList.size() - 1), tableName, benchmarkDE, minMultiplier);

    Sort sort = pageRequest.getSort();
    Iterator<Order> sortItr = sort.iterator();
    List<String> dateRangeStrList = dateRangeList.stream()
        .map(date -> CommonsDateUtil.formatDate(date))
        .collect(Collectors.toList());
    while (sortItr.hasNext()) {
      Order order = sortItr.next();
      String orderBy = order.getProperty();
      if (Interval.DAILY == interval) {
        // daily table does not have peak_ column
        orderBy = orderBy.replace("peak_", "");
        orderBy = orderBy.replace("SUM(num_days)", "1");
      }
      nativeSql = nativeSql.replace(":order_by:", orderBy);
      nativeSql = nativeSql.replace(":direction:", order.getDirection().toString());
    }

    nativeSql = nativeSql.replace(":page:", String.valueOf(pageRequest.getPageNumber()));
    nativeSql = nativeSql.replace(":size:", String.valueOf(pageRequest.getPageSize()));
    Query nativeQuery = entityManager.createNativeQuery(nativeSql, GroupedExpressions.class)
        .setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);

    nativeQuery.setParameter("markets", marketsList);
    nativeQuery.setParameter("short_ids", shortIDList);
    nativeQuery.setParameter("date_ranges", dateRangeStrList);
    nativeQuery.setFirstResult(pageRequest.getPageNumber() * pageRequest.getPageSize());
    List<GroupedExpressions> result = new ArrayList<>();
    nativeQuery.setMaxResults(pageRequest.getPageSize());
    try {
      result = nativeQuery.getResultList();
    } catch (PersistenceException e) {
      logger.error("Exception occurs in queryNativeSql", e);
    }
    return result;
  }

  @Override
  public List<GroupedExpressions> liveWeightedDemandByShortIdAsTimeseries(DemandRequest apiRequest,
      PageRequest pageRequest) {
    return weightedDemandNativeQuery(apiRequest, pageRequest,
        new StringBuilder(weightedAverageByShortIdCountryTimeseriesQuery).toString());
  }

  @Override
  public List<GroupedExpressions> intervalDemandByShortIdAsOverall(Interval interval,
      List<Date> dateRangeList,
      List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Double benchmarkDE,
      Double minMultiplier) {
    return intervalDemandByShortIdAsOverall(interval, dateRangeList, marketsList, shortIDList,
        pageRequest,
        Entity.TV_SERIES, benchmarkDE, minMultiplier);
  }

}
