package com.parrotanalytics.api.data.repo.api;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.parrotanalytics.api.apidb_model.ExtendedPlatformPK;
import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedPlatformInfo;
import com.parrotanalytics.api.commons.constants.APICacheConstants;
import com.parrotanalytics.api.data.repo.api.custom.CountryPlatformsRepositoryCustom;

@Repository
public interface CountryPlatformsRepository
        extends CrudRepository<ExtendedPlatformInfo, ExtendedPlatformPK>, CountryPlatformsRepositoryCustom
{

    @Cacheable(cacheNames = APICacheConstants.CACHE_PLATFORMS, key = "'pf:' + #platformName + '-' + #clientReady")
    @Query(value = "SELECT p.id AS platform_id, p.name AS platform_name, p.type AS platform_type, p.justwatch_name, p.home_market AS home_market, upper(cg.genecountry) AS availability_market, p.originals_count AS originals_count, p.asset_url, p.tv360_active AS client_ready FROM portal.platform_mappings p JOIN portal.catalog_genes cg ON cg.genevalue = p.name  WHERE cg.gene = 'platform' AND cg.genevalue = ?1 AND p.tv360_active = ?2 GROUP BY p.id, p.name, p.justwatch_name, cg.genecountry", nativeQuery = true)
    public List<ExtendedPlatformInfo> fetchPlatform(String platformName, int clientReady);
}
