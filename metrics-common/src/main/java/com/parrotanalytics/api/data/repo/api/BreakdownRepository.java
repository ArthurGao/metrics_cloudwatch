package com.parrotanalytics.api.data.repo.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.BreakdownDaily;
import com.parrotanalytics.api.apidb_model.BreakdownWhitelist;
import com.parrotanalytics.api.apidb_model.nonmanaged.GroupedBreakdown;
import com.parrotanalytics.api.data.repo.api.custom.BreakdownRepositoryCustom;

/**
 * Spring Data Repository for Breakdown Data
 *
 * @author sanjeev
 */
@Repository
@RepositoryRestResource(exported = false)
public interface BreakdownRepository
        extends PagingAndSortingRepository<BreakdownDaily, String>, BreakdownRepositoryCustom
{
    @Query("SELECT NEW com.parrotanalytics.api.apidb_model.nonmanaged.GroupedBreakdown"
            + "(max(b.date), b.country, b.short_id, COALESCE(AVG(b.de_social),0), COALESCE(AVG(b.de_video),0), COALESCE(AVG(b.de_research),0), COALESCE(AVG(b.de_piracy),0)) "
            + "FROM BreakdownDaily b "
            + "WHERE b.date IN :dateRangeList AND b.country IN :marketsList AND b.short_id IN :shortIDList "
            + "GROUP BY b.country, b.short_id")
    Page<GroupedBreakdown> showsDemandByAverage(@Param("dateRangeList") List<Date> dateRangeList,
            @Param("marketsList") List<String> marketsList, @Param("shortIDList") List<Long> shortIDList,
            Pageable page);

    @Query("SELECT NEW com.parrotanalytics.api.apidb_model.nonmanaged.GroupedBreakdown"
            + "(b.date, b.country, b.short_id, AVG(b.de_social), AVG(b.de_video), AVG(b.de_research), AVG(b.de_piracy)) "
            + "FROM BreakdownDaily b "
            + "WHERE b.date IN :dateRangeList AND b.country IN :marketsList AND b.short_id IN :shortIDList "
            + "GROUP BY b.date, b.country, b.short_id")
    Page<GroupedBreakdown> showsDemandByDAILY(@Param("dateRangeList") List<Date> dateRangeList,
            @Param("marketsList") List<String> marketsList, @Param("shortIDList") List<Long> shortIDList,
            Pageable page);

    @Query("SELECT b FROM BreakdownWhitelist b WHERE b.short_id IN :shortIDList AND b.country IN :marketsList")
    List<BreakdownWhitelist> whiteListedBreakdownTitles(@Param("marketsList") List<String> marketsList,
            @Param("shortIDList") List<Long> shortIDList);

    @Query(value = "SELECT distinct country FROM metrics.breakdown_whitelist where onboarded_on IN ?", nativeQuery = true)
    List<String> getWhitelistedMarkets(List<Date> dateList);

    @Query("SELECT max(b.date) FROM BreakdownDaily b")
    Date getLatestBreadownDate();

}