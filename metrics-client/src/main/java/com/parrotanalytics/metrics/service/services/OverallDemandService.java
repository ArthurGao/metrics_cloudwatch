package com.parrotanalytics.metrics.service.services;


import com.parrotanalytics.metrics.service.request.TitlesRequest;
import com.parrotanalytics.metrics.commons.OverallDemandRequest;
import com.parrotanalytics.metrics.commons.OverallDemandResponse;
import com.parrotanalytics.metrics.commons.OverallDemandServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class OverallDemandService{

    @GrpcClient("metrics-service")
    private OverallDemandServiceGrpc.OverallDemandServiceBlockingStub overallDemandStub;


    public OverallDemandResponse getOverallDemand(TitlesRequest request) {
        try {
            OverallDemandRequest overallDemandRequest = OverallDemandRequest.newBuilder().
                setPage(request.getPage()).setMetricType("apiuser@rootaccessapiId").build();
            OverallDemandResponse overallDemandtResponse = overallDemandStub.getOverallDemand(overallDemandRequest);
            return overallDemandtResponse;
        } catch (Exception e){}
        return null;
    }

    public OverallDemandResponse getOverallDemand(OverallDemandRequest request) {
        try {
            OverallDemandResponse overallDemandtResponse = overallDemandStub.getOverallDemand(request);
            return overallDemandtResponse;
        } catch (Exception e){}
        return null;
    }
}
