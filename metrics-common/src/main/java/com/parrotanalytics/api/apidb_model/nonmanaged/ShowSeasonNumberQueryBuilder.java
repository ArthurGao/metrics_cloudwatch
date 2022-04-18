package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.apicore.config.APIConstants;
import java.util.List;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@Data
public class ShowSeasonNumberQueryBuilder {

  private String seasonNumber;
  private boolean negate;

  public ShowSeasonNumberQueryBuilder(String seasonNumber, boolean negate) {
    this.seasonNumber = seasonNumber;
    this.negate = negate;
  }

  public QueryBuilder buildQuery() {
    String[] values = seasonNumber.split(APIConstants.DELIM_COMMA);
    int minNum = values.length >= 1 ? Integer.parseInt(values[0]) : 0;
    int maxNum = values.length == 2 ? Integer.parseInt(values[1]) : Integer.MAX_VALUE;
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    List<QueryBuilder> logicQuery = negate ? boolQuery.mustNot() : boolQuery.must();
    logicQuery
        .add(QueryBuilders.rangeQuery("data.total_seasons").gte(minNum).lte(maxNum));

    return boolQuery;
  }
}
