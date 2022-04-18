package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.commons.utils.date.CommonsDateUtil;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.joda.time.DateTime;

@Data
public class ShowReleaseDateQueryBuilder {

  private DateInputFilter dateFilter;
  private boolean negate;

  public ShowReleaseDateQueryBuilder(String releaseDate, boolean negate) {
    String[] values = releaseDate.split(APIConstants.DELIM_COMMA);
    DateTime from = values.length >= 2 ? CommonsDateUtil.parseDateStr(values[1]) : DateTime.now();
    DateTime to = values.length == 3 ? CommonsDateUtil.parseDateStr(values[2]) : DateTime.now();
    dateFilter = new DateInputFilter();
    dateFilter.setBetween(Arrays.asList(from, to));
    this.negate = negate;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    DateTime from = this.dateFilter.getBetween().get(0);
    DateTime to = this.dateFilter.getBetween().get(1);
    List<QueryBuilder> logicQuery = negate ? boolQuery.mustNot() : boolQuery.must();
    logicQuery.add(
        QueryBuilders.rangeQuery("data.start_date").gte(CommonsDateUtil.formattedDate(from))
            .lte(CommonsDateUtil.formattedDate(to)));
    return boolQuery;
  }
}
