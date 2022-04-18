package com.parrotanalytics.api.commons.constants;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Minh Vu
 */
public enum TVSeriesType {

  SCRIPTED("scripted"),
  UNSCRIPTED("unscripted");

  private String value;

  private TVSeriesType(String type) {
    this.value = type;
  }

  public String value() {
    return this.value;
  }

  @JsonCreator
  public static TVSeriesType fromValue(String value) {
    if (value != null) {
      for (TVSeriesType instance : TVSeriesType.values()) {
        if (value.equalsIgnoreCase(instance.value())) {
          return instance;
        }
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
