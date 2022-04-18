package com.parrotanalytics.api.request.ingenreperformance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.parrotanalytics.api.apidb_model.InternalUser;
import com.parrotanalytics.apicore.model.request.APIRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
public class InGenrePerformanceRequest extends APIRequest implements Cloneable {

  private static final long serialVersionUID = -915903367738297242L;

  /**
   * {@link APIRequest} calling user
   */
  @JsonIgnore
  private InternalUser callUser;

  private List<InGenrePerformanceDataQuery> query;

  private String order;

  public InGenrePerformanceDataQuery getDataQuery() {
    return query.get(0);
  }

  public List<Date> getDateRangeList() {
    return this.getDataQuery().getDateRangeList();
  }

  public void setDateRangeList(List<Date> dateRangeList) {
    this.getDataQuery().setDateRangeList(dateRangeList);
  }

  @Override
  public InGenrePerformanceRequest clone() {
    InGenrePerformanceRequest deepClone = new InGenrePerformanceRequest();
    deepClone.callUser = this.callUser;
    deepClone.query = this.query.stream().map(InGenrePerformanceDataQuery::clone).collect(Collectors.toList());
    deepClone.order = this.order;
    return deepClone;
  }
}
