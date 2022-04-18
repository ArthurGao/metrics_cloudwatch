package com.parrotanalytics.api.apidb_model.nonmanaged;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

@Data
public class ShowReleaseTypeQueryBuilder {

  private String releaseType;

  public ShowReleaseTypeQueryBuilder(String releaseType) {
    this.releaseType = releaseType;
  }

  public QueryBuilder buildQuery() {
    return new StringESStringFieldQueryBuilder(releaseType, "data.release_type").buildQuery();
  }
}
