package com.parrotanalytics.api.data.repo.api;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.ExpressionsComputed;

@Repository
public interface PrecomputationRepository extends CrudRepository<ExpressionsComputed, String>
{
    @Modifying
    @Procedure(name = "SP_COMPUTE_EXPRESSION_RANGES")
    void callSPComputeExpressionsRanges(@Param("date_from") Date dateFrom, @Param("date_to") Date dateTo,
            @Param("range_key") String rangeKey, @Param("interval_type") String interval,
            @Param("table_suffix") String tableSuffix);

    @Modifying
    @Procedure(name = "SP_BACKFILL_COMPUTED_DATASETS")
    void callSPBackfillComputedDatasets(@Param("date_from") Date dateFrom, @Param("date_to") Date dateTo,
            @Param("interval_type") String interval, @Param("table_suffix") String tableSuffix);

    @Modifying
    @Query(value = "TRUNCATE TABLE `metrics`.`expressions_computed`", nativeQuery = true)
    void truncateExpressionsComputed();

    @Modifying
    @Query(value = "TRUNCATE TABLE `metrics`.`sp_logging`", nativeQuery = true)
    void truncateSpLogging();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO `metrics`.`benchmark_demand` SELECT DATE, AVG(raw_de) FROM `metrics`.`expressions_daily` WHERE DATE >= :date_to AND `country` = 'US' GROUP BY DATE", nativeQuery = true)
    void insertBenchmarkDemand(@Param("date_to") Date dateTo);

    /* STANDARD expressions modification */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ExpressionsComputed e WHERE e.range_key IN :rangeKeys")
    void deleteExpressionsComputed(@Param("rangeKeys") List<String> rangeKeys);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ExpressionsWeekly e WHERE e.range_key IN :rangeKeys")
    void deleteExpressionsComputedWeekly(@Param("rangeKeys") List<String> rangeKeys);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ExpressionsMonthly e WHERE e.range_key IN :rangeKeys")
    void deleteExpressionsComputedMonthly(@Param("rangeKeys") List<String> rangeKeys);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ExpressionsQuarterly e WHERE e.range_key IN :rangeKeys")
    void deleteExpressionsComputedQuarterly(@Param("rangeKeys") List<String> rangeKeys);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM ExpressionsYearly e WHERE e.range_key IN :rangeKeys")
    void deleteExpressionsComputedYearly(@Param("rangeKeys") List<String> rangeKeys);

}
