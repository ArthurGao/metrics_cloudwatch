package com.parrotanalytics.api.response.parrotratings;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.rits.cloning.Cloner;

/**
 * 
 * @author sanjeev
 *
 */
@JsonPropertyOrder(
{
        "date", "title", "parrot_id", "market", "datapoints", "datapointsmap"
})
@JsonInclude(Include.NON_NULL)
public class GenePerfPayload implements Cloneable, Serializable
{
    private static final long serialVersionUID = -226782791989672376L;

    private String date;

    @JsonProperty("country")
    private String country;

    @JsonProperty("gene")
    private String gene;

    @JsonProperty("genevalue")
    private String genevalue;
    
    private Double country_avg;



    
    public GenePerfPayload clone()
    {
        return new Cloner().deepClone(this);
    }
}
