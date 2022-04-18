package com.parrotanalytics.api.request.collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author minhvu
 *
 */
@Setter
@Getter
public class CollectionsRequest extends APIRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1502043617212734106L;

    /**
     * The operation type. Internal use
     */
    private OperationType operationType;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("label")
    private String label;

    @JsonProperty("market")
    private String market;

    @JsonProperty("period")
    private String period;

    @JsonProperty("country")
    private String country;

    @JsonProperty("platform")
    private String platform;

    @JsonProperty("genre")
    private String genre;

    @JsonProperty("is_template")
    private boolean isTemplate;

}
