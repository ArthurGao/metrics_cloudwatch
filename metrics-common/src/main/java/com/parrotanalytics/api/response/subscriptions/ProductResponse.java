package com.parrotanalytics.api.response.subscriptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ProductResponse
{
    @JsonProperty("product_offering_id")
    private Integer idProductOfferingId;

    @JsonProperty("product_name")
    private String productName;

    @JsonProperty("product_description")
    private String productDescription;

    @JsonProperty("product_status")
    private Integer productStatus;

    public ProductResponse()
    {
    }
    

    public ProductResponse(Integer idProductOfferingId, String productName, String productDescription,
            Integer productStatus)
    {
        super();
        this.idProductOfferingId = idProductOfferingId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productStatus = productStatus;
    }



    public Integer getIdProductOfferingId()
    {
        return idProductOfferingId;
    }

    public void setIdProductOfferingId(Integer idProductOfferingId)
    {
        this.idProductOfferingId = idProductOfferingId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getProductDescription()
    {
        return productDescription;
    }

    public void setProductDescription(String productDescription)
    {
        this.productDescription = productDescription;
    }

    public Integer getProductStatus()
    {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }

}
