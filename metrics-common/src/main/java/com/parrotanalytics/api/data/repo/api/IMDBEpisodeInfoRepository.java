package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.IMDBEpisodeInfo;
import com.parrotanalytics.api.apidb_model.IMDBEpisodeInfoPK;

/**
 * @author Minh Vu
 * @since v1
 */
@Repository
public interface IMDBEpisodeInfoRepository extends JpaRepository<IMDBEpisodeInfo, IMDBEpisodeInfoPK>
{
    @Query("SELECT a from IMDBEpisodeInfo a WHERE a.series_id = :series_id AND a.country_iso = :country_iso AND a.release_date != null ORDER BY a.season_num ASC, a.episode_num ASC")
    List<IMDBEpisodeInfo> findBySeriesTitleId(@Param(value = "series_id") String seriesId,
            @Param(value = "country_iso") String countryIso);

}
