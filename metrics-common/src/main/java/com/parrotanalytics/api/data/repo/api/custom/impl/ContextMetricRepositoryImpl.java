package com.parrotanalytics.api.data.repo.api.custom.impl;

import com.parrotanalytics.api.apidb_model.LiveAffinityIndex;
import com.parrotanalytics.api.apidb_model.LiveDatasourceMetrics;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.TableConstants;
import com.parrotanalytics.api.data.repo.api.custom.ContextMetricRepositoryCustom;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class ContextMetricRepositoryImpl implements ContextMetricRepositoryCustom {

  @PersistenceContext(unitName = "PARROT_API")
  private EntityManager entityManager;

  private Map<Entity, String> entity2AffinityTables = Stream
      .of(new AbstractMap.SimpleEntry<>(Entity.TV_SERIES, TableConstants.LIVE_AFFINITY_INDEX),
          new AbstractMap.SimpleEntry<>(Entity.TALENT, TableConstants.TALENT_LIVE_AFFINITY_INDEX),
          new AbstractMap.SimpleEntry<>(Entity.MOVIE, TableConstants.MOVIE_LIVE_AFFINITY_INDEX))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

  private Map<Entity, String> entity2DatasourcesMetricsTables = Stream
      .of(new AbstractMap.SimpleEntry<>(Entity.TV_SERIES, TableConstants.LIVE_DATASOURCE_METRICS),
          new AbstractMap.SimpleEntry<>(Entity.TALENT,
              TableConstants.TALENT_LIVE_DATASOURCE_METRICS),
          new AbstractMap.SimpleEntry<>(Entity.MOVIE, TableConstants.MOVIE_LIVE_DATASOURCE_METRICS))
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

	@Override
	public List<LiveAffinityIndex> getLiveAffinityIndex(List<Long> shortIDFroms, Entity entity)
	{
		String schemaName = TableConstants.METRICS_SCHEMA;
		String tableName = this.entity2AffinityTables.get(entity);
		tableName = tableName != null ? tableName : TableConstants.LIVE_AFFINITY_INDEX;
		String queryStr = String.format("SELECT * from %s.%s WHERE short_id_from IN #short_id_from ORDER BY `rank` ASC", schemaName,
				tableName);
		Query query = entityManager.createNativeQuery(queryStr, LiveAffinityIndex.class);
		query.setParameter("short_id_from", shortIDFroms);
		return query.getResultList();
	}

  @Override
  public List<LiveDatasourceMetrics> getLiveDataSourceMetrics(List<Long> shortIDs, Entity entity) {
    String schemaName = TableConstants.METRICS_SCHEMA;
    String tableName = this.entity2DatasourcesMetricsTables.get(entity);
    tableName = tableName != null ? tableName : TableConstants.LIVE_DATASOURCE_METRICS;
    String queryStr = String.format("SELECT * from %s.%s WHERE short_id IN #short_ids", schemaName,
        tableName);
    Query query = entityManager.createNativeQuery(queryStr, LiveDatasourceMetrics.class);
    query.setParameter("short_ids", shortIDs);
    return query.getResultList();
  }

}
