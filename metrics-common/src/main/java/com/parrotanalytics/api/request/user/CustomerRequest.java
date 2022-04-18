package com.parrotanalytics.api.request.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * REST API {@link Endpoint} create user request.
 * 
 * @author Jackson Lee
 *
 */
public class CustomerRequest extends APIRequest
{

    protected OperationType custAction;

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

    public OperationType getCustAction()
    {
        return custAction;
    }

    public void setCustAction(OperationType custAction)
    {
        this.custAction = custAction;
    }

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

    public String getPrimaryContactNumber()
    {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber)
    {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getPrimaryContactEmail()
    {
        return primaryContactEmail;
    }

    public void setPrimaryContactEmail(String primaryContactEmail)
    {
        this.primaryContactEmail = primaryContactEmail;
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
