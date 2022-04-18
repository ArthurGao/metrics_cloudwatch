package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class StringInputFilter {

  @JsonProperty("begins_with")
  private String beginsWith;
  private String eq;
  private String ne;
  private List<String> in;
}
