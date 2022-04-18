package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.DateTime;

@Data
public class DateESDateFieldQueryBuilder {

  private DateInputFilter filter;
  private String esField;

  public DateESDateFieldQueryBuilder(DateInputFilter filter, String esField) {
    this.filter = filter;
    this.esField = esField;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

    if (filter.getEq() != null) {
      boolQuery.must().add(QueryBuilders.termQuery(esField, CommonsDateUtil.formattedDate(filter.getEq())));

    }
    if (filter.getNe() != null) {
      boolQuery.mustNot().add(QueryBuilders.termQuery(esField, CommonsDateUtil.formattedDate(filter.getNe())));

    }
    if (filter.getGt() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gt(CommonsDateUtil.formattedDate(filter.getGt())));

    }
    if (filter.getGe() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gte(CommonsDateUtil.formattedDate(filter.getGe())));

    }
    if (filter.getLt() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).lt(CommonsDateUtil.formattedDate(filter.getLt())));
    }
    if (filter.getLe() != null) {
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).lte(CommonsDateUtil.formattedDate(filter.getLe())));
    }
    if (filter.getBetween() != null && CollectionUtils.size(filter.getBetween()) == 2) {
      DateTime from = filter.getBetween().get(0);
      DateTime to = filter.getBetween().get(1);
      boolQuery.must().add(QueryBuilders.rangeQuery(esField).gte(CommonsDateUtil.formattedDate(from)).lte(CommonsDateUtil.formattedDate(to)));
    }

    return boolQuery;
  }
}
