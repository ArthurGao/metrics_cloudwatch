package com.parrotanalytics.metrics.model.mapper;

import com.google.protobuf.Timestamp;
import com.parrotanalytics.metrics.commons.OverallDemandResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DemandDataMapper {
  @Mapping(source = "date", target = "date", qualifiedByName  = "timeStampToString")
  com.parrotanalytics.metrics.model.DemandData convert(OverallDemandResponse.DemandData demandDataModel);

  @Named("timeStampToString")
  default String timeStampToString(Timestamp time) {
    return time.toString();
  }
}
