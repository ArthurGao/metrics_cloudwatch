package com.parrotanalytics.api.apidb_model.nonmanaged;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@Data
public class IntESIntFieldQueryBuilder {

  private IntInputFilter filter;
  private String esField;

  public IntESIntFieldQueryBuilder(IntInputFilter filter, String esField) {
    this.filter = filter;
    this.esField = esField;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    if (filter.getEq() != null) {
      boolQuery.must().add(QueryBuilders.termQuery(esField, filter.getEq()));

    }
    if (filter.getNe() != null) {
      boolQuery.mustNot().add(QueryBuilders.termQuery(esField, filter.getNe()));

    }
    if (filter.getGt() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gt(filter.getGt()));

    }
    if (filter.getGe() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gte(filter.getGe()));

    }
    if (filter.getLt() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).lt(filter.getLt()));
    }
    if (filter.getLe() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).lte(filter.getLe()));
    }
    if (filter.getBetween()!= null && CollectionUtils.size(filter.getBetween()) == 2) {
      Integer from = filter.getBetween().get(0);
      Integer to = filter.getBetween().get(1);
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gte(from).lte(to));
    }

    return boolQuery;
  }
}
