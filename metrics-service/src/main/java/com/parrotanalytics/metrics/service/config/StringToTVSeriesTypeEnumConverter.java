package com.parrotanalytics.metrics.service.config;

import com.parrotanalytics.api.commons.constants.TVSeriesType;
import org.springframework.core.convert.converter.Converter;

public class StringToTVSeriesTypeEnumConverter implements Converter<String, TVSeriesType> {

  @Override
  public TVSeriesType convert(String source) {
    return TVSeriesType.fromValue(source);
  }
}
