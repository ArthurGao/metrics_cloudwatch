package com.parrotanalytics.metrics.service.exception;

import lombok.Getter;

@Getter
public class MetricsServiceException extends RuntimeException {

    // TODO: NilS
    private static final long serialVersionUID = -8111656859346000121L;

    //TODO: Make it generic
    private Object errorCode;

    public MetricsServiceException(Object errorCode) {
  //      super(errorCode.name());
        this.errorCode = errorCode;
    }
}