package com.parrotanalytics.api.response.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class UsersResponse extends APIResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1913518816551195760L;

    @JsonProperty("user")
    private List<UserResponse> user;

    public List<UserResponse> getUser()
    {
        return user;
    }

    public void setUser(List<UserResponse> user)
    {
        this.user = user;
    }

}
