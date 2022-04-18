package com.parrotanalytics.api.response.ingenreperformance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parrotanalytics.apicore.model.response.APIResponse;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class InGenrePerformanceResponse extends APIResponse {
  private static final long serialVersionUID = -7215626057712019743L;

  private List<InGenrePerformance> data;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dateFrom;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date dateTo;
}
