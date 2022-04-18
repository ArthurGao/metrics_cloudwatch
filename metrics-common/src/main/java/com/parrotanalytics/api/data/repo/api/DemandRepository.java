package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.ExpressionsDaily;
import com.parrotanalytics.api.apidb_model.RangeKeyLookup;
import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.api.data.repo.api.custom.DemandRepositoryCustom;
import java.util.Date;
import java.util.List;
import org.eclipse.persistence.annotations.Cache;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@Cache(disableHits = true)
@RepositoryRestResource(exported = false)
public interface DemandRepository extends PagingAndSortingRepository<ExpressionsDaily, String>, DemandRepositoryCustom
{

        @Query(value = "SELECT AVG(benchmark_de) FROM metrics.benchmark_demand WHERE market = ?1 AND date >= ?2 AND date <= ?3", nativeQuery = true)
        Double benchmarkDemandByMarket(String country, Date dateFrom, Date dateTo);



        @Query(value = "SELECT AVG(benchmark_de) FROM metrics.benchmark_demand WHERE date IN (SELECT max(DATE) FROM metrics.benchmark_demand WHERE market = ?1) AND market = ?1", nativeQuery = true)
        Double defaultBenchmarkDemand(String country);

        @Query(value = "SELECT MAX(`end`) FROM `" + TableConstants.METRICS_SCHEMA
                        + "`.`range_key_lookup` WHERE end <= ? and interval_type=? and precomputed = 1", nativeQuery = true)
        Date findMaxDateByIntervalType(Date date, String intervalType);

        @Query(value = "SELECT MIN(`start`) FROM `" + TableConstants.METRICS_SCHEMA
                        + "`.`range_key_lookup` WHERE start >= ? and interval_type=? and precomputed = 1", nativeQuery = true)
        Date findMinDateByIntervalType(Date date, String intervalType);

        @Query(value = "SELECT DISTINCT `range_key` FROM `" + TableConstants.METRICS_SCHEMA
                        + "`.`range_key_lookup` WHERE start=? and end=? and interval_type=? and precomputed = 1", nativeQuery = true)
        List<String> findRangeKeyByDate(Date start, Date end, String intervalType);

        @Query(value = "SELECT r FROM RangeKeyLookup r WHERE r.rangeKey = :rangeKey")
        RangeKeyLookup findRangeKeyLookupByRangeKey(@Param("rangeKey") String rangeKey);

        @Query(value = "SELECT r FROM RangeKeyLookup r WHERE r.start >= :start and r.end <= :end and r.intervalType= :intervalType and r.precomputed = true order by r.start ASC")
        List<RangeKeyLookup> findRangeKeyLookupsByDates(@Param("start") Date start, @Param("end") Date end,
                        @Param("intervalType") String intervalType);

        @Query(value = "SELECT DISTINCT `range_key` FROM `" + TableConstants.METRICS_SCHEMA + "`.`"
                        + TableConstants.EXPRESSIONS_COMPUTED
                        + "` UNION SELECT DISTINCT `range_key` FROM metrics.range_key_lookup WHERE precomputed = 1", nativeQuery = true)
        List<String> findValidRangeKeys();

        @Query(value = "select sum(avg_dexpercapita) from (select short_id, avg(raw_de) as avg_dexpercapita from metrics.expressions_daily where country =? and date between ?  and ? "
                        + "and short_id IN (select subscriptionRefId from subscription.subscriptions where idAccount = 99 and subscriptionType = 'tvtitle') group by short_id) as ad", nativeQuery = true)
        Double marketSumDemand(Date start, Date end, String market);

        @Query(value = "SELECT MAX(DATE) FROM `" + TableConstants.METRICS_SCHEMA
                        + "`.`data_availability_state` WHERE pdr_type = 'Data-Migration'", nativeQuery = true)
        Date maxDemandDate();

        @Query(value = "SELECT MAX(DATE) FROM `" + TableConstants.METRICS_SCHEMA + "`.`"
                        + TableConstants.EXPRESSIONS_COMPUTED + "`", nativeQuery = true)
        Date maxPreComputedDate();

}
