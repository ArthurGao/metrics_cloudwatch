package com.parrotanalytics.api.request.regions;

import java.util.List;

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
public class RegionsRequest extends APIRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4219762005722217773L;

    /**
     * The operation type. Internal use
     */
    private OperationType operationType;

    private Integer id;

    private String name;
    
    private String flag;

    private List<String> country;

}
