package com.parrotanalytics.api.apidb_model.nonmanaged;

import java.util.List;
import lombok.Data;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.joda.time.DateTime;

@Data
public class DateInputFilter {

  private DateTime eq;
  private DateTime ne;
  private DateTime gt;
  private DateTime ge;
  private DateTime lt;
  private DateTime le;
  private List<DateTime> between;
}
