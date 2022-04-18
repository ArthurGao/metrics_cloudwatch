package com.parrotanalytics.api.response.customer;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class CustomerListResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1715219421798967314L;

    @JsonProperty("customers")
    List<CustomerResponse> customers;

    public List<CustomerResponse> getCustomers()
    {
        return customers;
    }

    public void setCustomers(List<CustomerResponse> customers)
    {
        this.customers = customers;
    }

}
