package com.parrotanalytics.metrics.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DemandData {
  private String date;
  private String market;
  private String label;
  private String parrotId;
  private String value;
  private int overallRank;
}
