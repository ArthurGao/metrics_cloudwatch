package com.parrotanalytics.api.request.validators;

import com.parrotanalytics.apicore.exceptions.APIException;

public interface Validator<REQ, RESP> {
  RESP validateCall(REQ request) throws APIException;
}
