package com.parrotanalytics.api.data.repo.api.custom;

import com.parrotanalytics.api.apidb_model.nonmanaged.InGenreRank;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.parrotanalytics.api.apidb_model.BenchmarkDemand;
import com.parrotanalytics.api.apidb_model.nonmanaged.BucketTopNExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GenePerformance;
import com.parrotanalytics.api.apidb_model.nonmanaged.GlobalGenrePerformance;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedExpressions;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedGenesExpressions;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.commons.constants.DayOfWeek;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.Interval;
import com.parrotanalytics.api.commons.constants.PreComputed;
import com.parrotanalytics.api.data.repo.api.DemandRepository;
import com.parrotanalytics.api.request.demand.DemandRequest;

/**
 * Interface for custom methods in {@link DemandRepository}.
 *
 * @author Sanjeev
 * @author Minh Vu
 */
public interface DemandRepositoryCustom
{
    /**
     * returns the demand benchmark for US for any market demamd request or WW
     * for worldwide request
     *
     * @param country
     * @param dateFrom
     * @param dateTo
     * @return
     */
    Double benchmarkDemand(String country, Date dateFrom, Date dateTo, Entity entity);

    List<BenchmarkDemand> dailyBenchmarkDemandByCountry(String country, Date dateFrom, Date dateTo, Entity entity);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    Page<BucketTopNExpressions> bucketTopNDemandOverall(String market, Date date, int topN, Integer callAccountID);

    Map<Long, Double> benchmarkDemandAtPeakByMarketCountry(String country, Date dateFrom, Date dateTo,
            List<Long> shortId, Entity entity);

    List<GroupedExpressions> convertWeightedAverage(List<String> markets, List<GroupedExpressions> groupedExpressions);

    Page<GenePerformance> genePerformance(String gene, String market, Date date);

    List<GlobalGenrePerformance> getGenrePerformance(List<Date> dateList, List<Long> shortIDs, String genre);

    List<InGenreRank> getInGenreRank(List<Date> dateRanges, String market, Long shortId, Entity entity);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedGenesExpressions> groupedByGenesData(String dynamicGroupingQuery);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandAsTimeSeries(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandAsTimeSeriesWithStartOfWeek(DayOfWeek startOfWeek, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageRequest, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByCountryAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDsList, PageRequest catalogPage, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDsList, PageRequest catalogPage, Entity entity,
            Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdAsTimeseries(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdAsTimeseriesWithStartOfWeek(DayOfWeek startOfWeek,
            List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs, PageRequest pageable,
            Entity entity, Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdAsTimeseriesWithCustomInterval(Interval interval,
        DayOfWeek startOfWeek, Optional<Integer> customIntervalRange,
        List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDList,
        PageRequest pageRequest,
        Entity entityString, Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdCountryAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> contentShortIDs, PageRequest pageRequest, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdCountryAsOverall(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> contentShortIDs, PageRequest pageRequest, Entity entity,
            Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseries(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseries(Interval interval, List<Date> dateRangeList,
            List<String> marketsList, List<Long> shortIDs, PageRequest pageable, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandByShortIdCountryAsTimeseriesWithStartOfWeek(DayOfWeek startOfWeek,
            List<Date> dateRangeList, List<String> marketsList, List<Long> shortIDs, PageRequest pageable,
            Entity entity, Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> intervalDemandMarketShareAsOverall(DemandRequest apiRequest, PageRequest pageRequest,
            Double benchmarkDE, Double minMultiplier);

    Date latestDemandDate();

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> liveWeightedDemandByShortIdAsOverall(DemandRequest apiRequest, PageRequest pageRequest,
            Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> liveWeightedDemandByShortIdAsTimeseries(DemandRequest apiRequest, PageRequest pageRequest);

    List<GroupedExpressions> marketShareTimeseries(DemandRequest apiRequest, PageRequest pageRequest);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> precomputedDemandAsOverall(PreComputed preComputed, Date dateTo, String rangeKey,
            List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> precomputedDemandByCountryAsOverall(PreComputed preComputed, Date dateTo, String rangeKey,
            List<String> marketsList, List<Long> shortIDList, PageRequest pageRequest, Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> precomputedDemandByShortIdAsTimeseries(PreComputed preComputed, Date dateTo,
            String rangeKey, List<String> marketsList, List<Long> shortIDList, PageRequest pageable,Entity entity, Double benchmarkDE,
            Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    List<GroupedExpressions> precomputedDemandByShortIdCountryAsTimeseries(PreComputed preComputed, Date dateTo,
            String rangeKey, List<String> marketsList, List<Long> shortIDList, PageRequest pageable, Entity entity,
            Double benchmarkDE, Double minMultiplier);

    @Cacheable(cacheNames = APICacheConstants.CACHE_EXPRESSIONS, keyGenerator = "cacheKeyGenerator")
    Page<GroupedExpressions> precomputedDemandByShortIdCountryOverall(DemandRequest apiRequest, PageRequest pageRequest,
            Entity entity, Double benchmarkDE, Double minMultiplier);
}
