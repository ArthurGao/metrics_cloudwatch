package com.parrotanalytics.metrics.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DemandDataMapper.class )
public interface OverallDemandReponseMapper {
  @Mapping(source = "dataList", target = "demandData")
  com.parrotanalytics.metrics.model.OverallDemandResponse convert(com.parrotanalytics.metrics.commons.OverallDemandResponse demandDataModel);
}
