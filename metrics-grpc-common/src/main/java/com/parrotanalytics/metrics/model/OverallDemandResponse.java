package com.parrotanalytics.metrics.model;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class OverallDemandResponse {
  private List<DemandData> demandData;
}
