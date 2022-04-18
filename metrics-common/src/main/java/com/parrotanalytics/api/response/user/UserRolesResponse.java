package com.parrotanalytics.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class UserRolesResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1913518816551195760L;

    @JsonProperty("role_name")
    private String roleName;

    // constructor
    public UserRolesResponse()
    {
    }

    // constructor
    public UserRolesResponse(String roleName)
    {
        this.roleName = roleName;
    }

    // getter
    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

}
