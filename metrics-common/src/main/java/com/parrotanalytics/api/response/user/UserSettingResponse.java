package com.parrotanalytics.api.response.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Jackson Lee
 *
 */

public class UserSettingResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 1913518816551195760L;

    @JsonProperty("avatar_alias")
    private String avatarAlias;

    @JsonProperty("home_market")
    private String homeMarket;

}
