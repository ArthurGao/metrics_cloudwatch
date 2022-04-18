package com.parrotanalytics.api.apidb_model.nonmanaged;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.parboiled.common.StringUtils;

@Data
public class MovieDistributionCompanyQueryBuilder {

  private StringInputFilter filter;
  private static String NESTED_FIELD = "value.keyword";
  private static String PATH = "data.distribution_company";

  public MovieDistributionCompanyQueryBuilder(StringInputFilter filter) {
    this.filter = filter;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    String fullPath = PATH + "." + NESTED_FIELD;
    if (StringUtils.isNotEmpty(filter.getEq())) {
      boolQuery.must().add(QueryBuilders.nestedQuery(PATH,
          QueryBuilders.termQuery(fullPath, filter.getEq()), ScoreMode.Avg));
    }
    if (StringUtils.isNotEmpty(filter.getNe())) {
      boolQuery.mustNot().add(QueryBuilders.nestedQuery(PATH,
          QueryBuilders.termQuery(fullPath, filter.getNe()), ScoreMode.Avg));
    }

    if (CollectionUtils.isNotEmpty(filter.getIn())) {
      boolQuery.must()
          .add(QueryBuilders.nestedQuery(PATH, QueryBuilders.termsQuery(fullPath, filter.getIn()),
              ScoreMode.Avg));
    }

    return boolQuery;
  }
}
