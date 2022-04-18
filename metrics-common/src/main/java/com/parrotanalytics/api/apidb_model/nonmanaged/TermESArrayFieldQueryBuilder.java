package com.parrotanalytics.api.apidb_model.nonmanaged;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.parboiled.common.StringUtils;

@Data
public class TermESArrayFieldQueryBuilder {

  private StringInputFilter filter;
  private String esField;
  private boolean nestedObject;
  private String path;

  public TermESArrayFieldQueryBuilder(StringInputFilter filter, String esField) {
    this.filter = filter;
    this.esField = esField;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    if (StringUtils.isNotEmpty(filter.getEq())) {

      boolQuery.must().add(QueryBuilders.termQuery(esField, filter.getEq()));

    }
    if (StringUtils.isNotEmpty(filter.getNe())) {
      boolQuery.mustNot().add(QueryBuilders.termQuery(esField, filter.getNe()));

    }
    if (CollectionUtils.isNotEmpty(filter.getIn())) {
      boolQuery.must().add(QueryBuilders.termsQuery(esField, filter.getIn()));
    }

    return boolQuery;
  }
}
