package com.parrotanalytics.api.response.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.response.user.UserAccountResponse;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class AccountResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -6745879693447766627L;

    @JsonProperty("user_accounts")
    List<UserAccountResponse> userAccounts;

    public List<UserAccountResponse> getUserAccounts()
    {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccountResponse> userAccounts)
    {
        this.userAccounts = userAccounts;
    }

}
