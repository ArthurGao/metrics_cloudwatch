package com.parrotanalytics.api.apidb_model.nonmanaged;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.parboiled.common.StringUtils;

@Data
public class MovieOriginalLanguageQueryBuilder {

  private StringInputFilter filter;
  private String NESTED_FIELD = "value";
  private String PATH = "data.original_language";
  private static String ORDINAL_NESTED_FIELD = "ordinal";

  public MovieOriginalLanguageQueryBuilder(StringInputFilter filter) {
    this.filter = filter;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    String fullPath = PATH + "." + NESTED_FIELD;
    String fullOrdinalPath = PATH + "." + ORDINAL_NESTED_FIELD;

    if (StringUtils.isNotEmpty(filter.getEq())) {

      BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();

      nestedBoolQuery.must().add(QueryBuilders.matchQuery(fullOrdinalPath, 0));
      nestedBoolQuery.must().add(QueryBuilders.termQuery(fullPath, filter.getEq()));

      NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(PATH, nestedBoolQuery,
          ScoreMode.Avg);

      boolQuery.must()
          .add(nestedQuery);
    }

    if (StringUtils.isNotEmpty(filter.getNe())) {
      BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();

      nestedBoolQuery.must().add(QueryBuilders.matchQuery(fullOrdinalPath, 0));
      nestedBoolQuery.must().add(QueryBuilders.termQuery(fullPath, filter.getNe()));

      NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(PATH, nestedBoolQuery,
          ScoreMode.Avg);

      boolQuery.mustNot().add(nestedQuery);
    }

    if (CollectionUtils.isNotEmpty(filter.getIn())) {
      BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();

      nestedBoolQuery.must().add(QueryBuilders.matchQuery(fullOrdinalPath, 0));
      nestedBoolQuery.must().add(QueryBuilders.termsQuery(fullPath, filter.getIn()));

      NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery(PATH, nestedBoolQuery,
          ScoreMode.Avg);

      boolQuery.must()
          .add(nestedQuery);
    }

    return boolQuery;
  }
}
