package com.parrotanalytics.api.request.ingenreperformance;

import com.parrotanalytics.api.request.demand.DataQuery;
import com.rits.cloning.Cloner;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the DataQuery object used in /demand, /rank, /contextmetric endpoints. It is recommended
 * to keep its field members as simple as possible. Dont use any object which has circular
 * references. Otherwise, clone() function will behave strangely
 *
 * @author minhvu
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InGenrePerformanceDataQuery extends DataQuery {

  private static final long serialVersionUID = -6668825920741719040L;

  public void setMarket(String market) {
    this.setMarkets(market);
  }

  @Override
  public InGenrePerformanceDataQuery clone() {
    return new Cloner().deepClone(this);
  }
}
