package com.parrotanalytics.metrics.service.exception;

import com.google.protobuf.Timestamp;
import io.grpc.StatusRuntimeException;
import java.time.Instant;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class MetricsServiceExceptionHandler {

    @GrpcExceptionHandler(MetricsServiceException.class)
    public StatusRuntimeException handleValidationError(MetricsServiceException cause) {

        Instant time = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond())
                .setNanos(time.getNano()).build();
        return null;
    /*    CityScoreExceptionResponse exceptionResponse =
                CityScoreExceptionResponse.newBuilder()
                        .setErrorCode(cause.getErrorCode())
                        .setTimestamp(timestamp)
                        .build();


        Status status = Status.newBuilder()
                        .setCode(Code.INVALID_ARGUMENT.getNumber())
                        .setMessage("Invalid city code")
                        .addDetails(Any.pack(exceptionResponse))
                        .build();

        return StatusProto.toStatusRuntimeException(status);*/
    }
}
