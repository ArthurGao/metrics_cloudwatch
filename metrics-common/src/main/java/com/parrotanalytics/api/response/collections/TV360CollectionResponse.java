package com.parrotanalytics.api.response.collections;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TV360CollectionResponse extends TV360CollectionItem
{

    /**
     * 
     */
    private static final long serialVersionUID = -5139905565194610017L;

    @JsonProperty("collections")
    private List<TV360CollectionItem> collections;
    
    

}
