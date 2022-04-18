package com.parrotanalytics.api.response.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class RoleResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1551754593785717765L;

    /**
     * 
     */

    @JsonProperty("user_roles")
    List<UserRolesResponse> userRoles;

    public List<UserRolesResponse> getUserRoles()
    {
        return userRoles;
    }

    public void setUserRoles(List<UserRolesResponse> userRoles)
    {
        this.userRoles = userRoles;
    }

}
