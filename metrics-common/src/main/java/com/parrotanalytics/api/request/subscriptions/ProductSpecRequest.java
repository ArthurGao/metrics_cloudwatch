package com.parrotanalytics.api.request.subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.request.APIRequest;

public class ProductSpecRequest extends APIRequest
{
    @JsonProperty("product_offering_spec_id")
    private Integer productOfferingSpecId;

    @JsonProperty("specification_name")
    private String specificationName;

    @JsonProperty("specification_value")
    private String specificationValue;

    public Integer getProductOfferingSpecId()
    {
        return productOfferingSpecId;
    }

    public void setProductOfferingSpecId(Integer productOfferingSpecId)
    {
        this.productOfferingSpecId = productOfferingSpecId;
    }

    public String getSpecificationName()
    {
        return specificationName;
    }

    public void setSpecificationName(String specificationName)
    {
        this.specificationName = specificationName;
    }

    public String getSpecificationValue()
    {
        return specificationValue;
    }

    public void setSpecificationValue(String specificationValue)
    {
        this.specificationValue = specificationValue;
    }

}
