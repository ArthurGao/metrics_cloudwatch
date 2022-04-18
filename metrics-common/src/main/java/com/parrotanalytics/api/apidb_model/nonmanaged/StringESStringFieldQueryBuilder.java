package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.apicore.config.APIConstants;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.parboiled.common.StringUtils;

@Data
public class StringESStringFieldQueryBuilder {

  private String value;
  private String esField;
  private boolean negate;

  public StringESStringFieldQueryBuilder(String value, String esField, boolean negate) {
    this.value = value;
    this.esField = esField;
    this.negate = negate;
  }

  public StringESStringFieldQueryBuilder(String value, String esField) {
    this(value, esField, false);
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    if (StringUtils.isNotEmpty(value)) {
      List<QueryBuilder> logicQuery = negate ? boolQuery.mustNot() : boolQuery.must();
      logicQuery
          .add(QueryBuilders.termsQuery(esField,
              Arrays.asList(value.split(APIConstants.DELIM_COMMA))));
    }
    return boolQuery;
  }
}
