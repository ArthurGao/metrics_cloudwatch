package com.parrotanalytics.api.response.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

@JsonPropertyOrder(
{
        "customer_id", "company_name", "primary_contact_name", "primary_contact_email", "primary_contact_number",
        "status"
})

public class CustomerResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 5463792041716553023L;

    @JsonProperty("customer_id")
    protected Integer idCustomer;

    @JsonProperty("company_name")
    protected String companyName;

    @JsonProperty("primary_contact_name")
    protected String primaryContactName;

    @JsonProperty("primary_contact_email")
    protected String primaryContactEmail;

    @JsonProperty("primary_contact_number")
    protected String primaryContactNumber;

    @JsonProperty("status")
    protected Integer status;

    public Integer getIdCustomer()
    {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer)
    {
        this.idCustomer = idCustomer;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getPrimaryContactName()
    {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName)
    {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactEmail()
    {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail)
    {
        this.primaryContactEmail = primaryContactEmail;
    }

    public String getPrimaryContactNumber()
    {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber)
    {
        this.primaryContactNumber = primaryContactNumber;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

}
