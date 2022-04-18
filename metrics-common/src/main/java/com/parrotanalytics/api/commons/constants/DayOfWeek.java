package com.parrotanalytics.api.commons.constants;

import java.util.Calendar;

public enum DayOfWeek {

  SUN("sun", Calendar.SUNDAY, 6),

  MON("mon", Calendar.MONDAY, 5),

  TUE("tue", Calendar.TUESDAY, 4),

  WED("wed", Calendar.WEDNESDAY, 3),

  THU("thu", Calendar.THURSDAY, 2),

  FRI("fri", Calendar.FRIDAY, 1),

  SAT("sat", Calendar.SATURDAY, 0);

  private final String value;

  /**
   * Value in Calendar.get(Calendar.DAY_OF_WEEK)
   */
  private final int calendarValue;

  /**
   * Offset for calculate the start/end of week in mysql using expressing startDateOfWeek =
   * FROM_DAYS(TO_DAYS(date) - MOD(TO_DAYS(date) + offset, 7))
   */
  private final int offset;


  private DayOfWeek(String value, int calendarValue, int offset) {
    this.value = value;
    this.calendarValue = calendarValue;
    this.offset = offset;
  }

  /**
   * Value.
   *
   * @return the string
   */
  public String value() {
    return this.value;
  }

  public int getOffset() {
    return this.offset;
  }

  public int getCalendarValue() {
    return this.calendarValue;
  }

  /**
   * From value.
   *
   * @param value the value
   * @return the source event type
   */
  public static DayOfWeek fromValue(String value) {
    if (value != null) {
      for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
        if (value.equalsIgnoreCase(dayOfWeek.value())) {
          return dayOfWeek;
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
