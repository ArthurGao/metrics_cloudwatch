package com.parrotanalytics.api.request.decorators;

import com.parrotanalytics.apicore.exceptions.APIException;

public interface Decorator<REQ> {
  void decorateRequest(REQ request) throws APIException;
}
