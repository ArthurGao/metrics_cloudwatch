package com.parrotanalytics.api.data.repo.api.custom.impl;

import com.parrotanalytics.api.apidb_model.nonmanaged.GenreEntityCount;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.data.repo.api.custom.CatalogGeneRepositoryCustom;
import com.parrotanalytics.api.util.DemandHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class CatalogGeneRepositoryImpl implements CatalogGeneRepositoryCustom {

  @PersistenceContext(unitName = "PARROT_API")
  private EntityManager entityManager;

  public List<GenreEntityCount> getGenreEntityCount(Entity entity) {
    String nativeSql = "select count(distinct cg.short_id) as count, genevalue as genre "
        + "from portal.:catalog_genes: cg join portal.:metadata: m on m.short_id = cg.short_id and m.catalog_state = 'client_ready' "
        + "where cg.gene = 'genre_tags' group by genevalue ";
    nativeSql = nativeSql.replace(":catalog_genes:", DemandHelper.getCatalogGenesTableByEntity(entity));
    nativeSql = nativeSql.replace(":metadata:", DemandHelper.getMetadataTableByEntity(entity));
    Query nativeQuery = entityManager.createNativeQuery(nativeSql, GenreEntityCount.class);
    return nativeQuery.getResultList();
  }
}
