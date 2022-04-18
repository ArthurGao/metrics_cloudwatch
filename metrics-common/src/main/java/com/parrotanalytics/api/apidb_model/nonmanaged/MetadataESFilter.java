package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.api.apidb_model.DataQueryFilter;
import com.parrotanalytics.api.commons.constants.Entity;
import com.parrotanalytics.api.commons.constants.MergeLogicOperator;
import com.parrotanalytics.apicore.config.APIConstants;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * A POJO object encapsulates all filter criterias for Metadata.
 *
 * @author minhvu
 */
@Data
public abstract class MetadataESFilter implements Serializable {

  private static final long serialVersionUID = -5221709144619014829L;

  public MetadataESFilter() {
  }

  public MetadataESFilter(Entity entity) {
    this.entity = entity;
  }

  private Entity entity;

  private List<Long> shortIDs;

  private List<String> catalogStates;

  private List<String> parrotIDs;

  private List<String> titles;

  protected MergeLogicOperator operator;

  private List<DataQueryFilter> and;

  private List<DataQueryFilter> or;

  protected boolean ignoreCatalogState;

  public BoolQueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    if (!ignoreCatalogState) {
      boolQuery.must()
          .add(QueryBuilders.termsQuery("catalog_state", Arrays.asList(APIConstants.CLIENT_READY)));
    }
    if (CollectionUtils.isNotEmpty(and)) {
      mergeLeafDataQueryFilter(boolQuery, and, MergeLogicOperator.AND);
    }
    if (CollectionUtils.isNotEmpty(or)) {
      mergeLeafDataQueryFilter(boolQuery, or, MergeLogicOperator.OR);
    }
    return boolQuery;
  }

  public BoolQueryBuilder mergeQuery(BoolQueryBuilder mainQuery, QueryBuilder componentQuery) {
    if (operator == MergeLogicOperator.AND) {
      mainQuery.must().add(componentQuery);
    } else if (operator == MergeLogicOperator.OR) {
      mainQuery.should().add(componentQuery);
    }
    return mainQuery;
  }

  public BoolQueryBuilder mergeLeafDataQueryFilter(BoolQueryBuilder boolQuery,
      List<DataQueryFilter> listLeafQueryFilters, MergeLogicOperator operator) {
    for (DataQueryFilter leafDataQuery : listLeafQueryFilters) {
      MovieESFilter leafFilter = new MovieESFilter(leafDataQuery);
      leafFilter.setIgnoreCatalogState(true);
      BoolQueryBuilder leafBoolQuery = (BoolQueryBuilder) leafFilter.buildQuery();
      if (operator == MergeLogicOperator.AND) {
        boolQuery.must().add(leafBoolQuery);
      } else if (operator == MergeLogicOperator.OR) {
        boolQuery.should().add(leafBoolQuery);
      }
    }
    return boolQuery;
  }
}
