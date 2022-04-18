package com.parrotanalytics.api.request.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.commons.constants.Endpoint;

// TODO: Auto-generated Javadoc
/**
 * REST API {@link Endpoint} create user request.
 * 
 * @author Jackson Lee
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelpdeskRequest
{

    /** The new password. */
    @JsonProperty("new_password")
    protected String newPassword;

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

}
