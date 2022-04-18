package com.parrotanalytics.api.data.repo.api.custom.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.parrotanalytics.api.apidb_model.nonmanaged.ExtendedPlatformInfo;
import com.parrotanalytics.api.data.repo.api.custom.CountryPlatformsRepositoryCustom;

public class CountryPlatformsRepositoryImpl implements CountryPlatformsRepositoryCustom
{
    @PersistenceContext(unitName = "PARROT_API")
    private EntityManager entityManager;

    @Override
    public List<ExtendedPlatformInfo> fetchPlatforms(int clientReady, List<String> catalogStates)
    {
        String queryStr = "SELECT p.id AS platform_id, p.name AS platform_name, p.type AS platform_type,"
                + " p.justwatch_name, p.home_market AS home_market, upper(cg.genecountry) AS availability_market,"
                + " p.originals_count AS originals_count, p.asset_url, p.tv360_active AS client_ready "
                + "FROM portal.platform_mappings p JOIN portal.catalog_genes cg ON cg.genevalue = p.name "
                + "and cg.parrot_id IN (SELECT parrot_id from portal.series_metadata WHERE catalog_state IN #catalog_states)  "
                + "WHERE cg.gene = 'platform' AND p.tv360_active = #tv360_active GROUP BY p.id, p.name, p.justwatch_name, cg.genecountry";

        Query query = entityManager.createNativeQuery(queryStr, ExtendedPlatformInfo.class);
        query.setParameter("tv360_active", clientReady);
        query.setParameter("catalog_states", catalogStates);

        return query.getResultList();
    }

}
