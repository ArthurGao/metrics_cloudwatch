package com.parrotanalytics.api.commons.constants;

public enum MergeLogicOperator {

  AND("and"), OR("or");

  private String value;

  private MergeLogicOperator(String type) {
    this.value = type;
  }

  /**
   * Value.
   *
   * @return the string
   */
  public String value() {
    return this.value;
  }

  public static MergeLogicOperator fromValue(String value) {
    if (value != null) {
      for (MergeLogicOperator instance : MergeLogicOperator.values()) {
        if (value.equalsIgnoreCase(instance.value())) {
          return instance;
        }
      }
    }
    //default to TVSeries by default
    return OR;
  }

  @Override
  public String toString() {
    return this.value;
  }

}
