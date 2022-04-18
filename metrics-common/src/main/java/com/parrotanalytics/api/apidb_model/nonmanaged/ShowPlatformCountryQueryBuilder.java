package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.parrotanalytics.apicore.config.APIConstants;
import java.util.Arrays;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

@Data
public class ShowPlatformCountryQueryBuilder {

  private String platform;
  private String geneCountry;

  public ShowPlatformCountryQueryBuilder(String platform, String geneCountry) {
    this.platform = platform;
    this.geneCountry = geneCountry;
  }

  public QueryBuilder buildQuery() {
    BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
    boolQuery.must().add(QueryBuilders.termsQuery("data.platforms.platform_name",
        Arrays.asList(platform.split(APIConstants.DELIM_COMMA))));
    if (StringUtils.isNotEmpty(geneCountry)) {
      boolQuery.must().add(QueryBuilders.termQuery("data.platforms.country_iso",
          geneCountry));
    }
    return boolQuery;
  }
}
