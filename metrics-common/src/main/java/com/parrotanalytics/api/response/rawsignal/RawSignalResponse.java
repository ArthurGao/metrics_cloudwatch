package com.parrotanalytics.api.response.rawsignal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.response.parrotratings.DemandResponse;

import lombok.Data;

@Data
public class RawSignalResponse extends DemandResponse
{
    private static final long serialVersionUID = -625627087346443763L;

    @JsonProperty("source")
    private String source;

}