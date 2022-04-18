package com.parrotanalytics.metrics.service.controller;

import com.parrotanalytics.metrics.commons.OverallDemandRequest;
import com.parrotanalytics.metrics.commons.OverallDemandResponse;
import com.parrotanalytics.metrics.commons.OverallDemandResponse.DemandData;
import com.parrotanalytics.metrics.model.mapper.DemandDataMapper;
import com.parrotanalytics.metrics.model.mapper.OverallDemandReponseMapper;
import com.parrotanalytics.metrics.service.request.TitlesRequest;
import com.parrotanalytics.metrics.service.services.OverallDemandService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated

public class OverallDemandController {

  @Autowired
  private OverallDemandService overallDemandService;

  @GetMapping(path= "/getOverallDemand", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public OverallDemandResponse getOverallDemand(@RequestParam Map<String,String> requestMap) {
    TitlesRequest titlesRequest = new TitlesRequest();
    titlesRequest.setPage(1);
    return overallDemandService.getOverallDemand(titlesRequest);
  }


  @GetMapping(path= "/getOverallDemandGrpcGateWay", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getOverallDemand(@RequestBody OverallDemandRequest request) {
    return ResponseEntity.ok(overallDemandService.getOverallDemand(request));
  }

  @Autowired
  private OverallDemandReponseMapper overallDemandReponseMapper;

  @GetMapping(path= "/getOverallDemandGrpcConverter", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<?> getOverallDemandByConverter(@RequestBody OverallDemandRequest request) {
    OverallDemandResponse grpcResponse = overallDemandService.getOverallDemand(request);
    return ResponseEntity.ok(overallDemandReponseMapper.convert(grpcResponse));
  }
}
