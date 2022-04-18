package com.parrotanalytics.api.data.repo.api;

import com.parrotanalytics.api.apidb_model.*;
import com.parrotanalytics.api.data.repo.api.custom.ContextMetricRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Minh Vu
 */
@Repository
@RepositoryRestResource(exported = false)
public interface ContextMetricRepository
        extends PagingAndSortingRepository<ConsolidatedContextMetrics, ConsolidatedContextMetricsPK>,
        ContextMetricRepositoryCustom
{

    @Query(value = "SELECT distinct genevalue from portal.catalog_genes where gene = 'genre_tags' AND short_id = ?", nativeQuery = true)
    public List<String> getGenreTagsForTitle(Long shortId); 

    @Query(value = "SELECT a from LiveDatasourceMetrics a WHERE a.short_id IN :short_ids AND a.source IN :sources AND a.metric IN :metrics")
    public List<LiveDatasourceMetrics> getLiveDataSourceMetricsByMetrics(@Param("short_ids") List<Long> shortIDs,
            @Param("sources") List<String> sources, @Param("metrics") List<String> metrics);

    @Query("SELECT e from ConsolidatedContextMetrics e JOIN GenreDict g on e.genreId = g.genreId "
            + "WHERE e.rangeKey = :rangeKey AND e.country IN :marketsList AND e.shortId IN :shortIDList AND g.genre IN :genres and e.metric = :metric ")
    public List<ConsolidatedContextMetrics> contextMetricsByRangeKeyCountryShortIdGenreAsOverall(
            @Param("rangeKey") String rangeKey, @Param("marketsList") List<String> countries,
            @Param("shortIDList") List<Long> shortIds, @Param("genres") List<String> genres,
            @Param("metric") String metric, Pageable page);

    @Query("SELECT e from ConsolidatedContextMetricsV2 e WHERE e.rangeKey = :rangeKey AND e.country IN :marketsList AND e.shortId IN :shortIDList  AND e.metric IN :metrics ")
    public List<ConsolidatedContextMetricsV2> multiContextMetricsByRangeKeyCountryShortIdOverall(
            @Param("rangeKey") String rangeKey, @Param("marketsList") List<String> countries,
            @Param("shortIDList") List<Long> shortIds, @Param("metrics") List<String> metrics, Pageable page);

    @Query("SELECT e from ConsolidatedContextMetrics e JOIN GenreDict g on e.genreId = g.genreId "
            + "WHERE e.rangeKey = :rangeKey AND e.country IN :marketsList AND e.shortId IN :shortIDList AND e.metric = :metric AND g.genre='overall' ")
    public List<ConsolidatedContextMetrics> contextMetricsByRangeKeyCountryShortIdAsOverall(
            @Param("rangeKey") String rangeKey, @Param("marketsList") List<String> countries,
            @Param("shortIDList") List<Long> shortIds, @Param("metric") String metric, Pageable page);

    @Query(value = "SELECT s from ContextMetricsSetting s")
    public List<ContextMetricsSetting> getContextMetricSettings();

    @Query(value = "SELECT s from ContextMetricsSettingV2 s WHERE s.period=:period AND s.metric_type IN :metrics")
    public List<ContextMetricsSettingV2> getContextMetricSettingsV2(@Param("period") String period,
            @Param("metrics") List<String> metrics);

}
