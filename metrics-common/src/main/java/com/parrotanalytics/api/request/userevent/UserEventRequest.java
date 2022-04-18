package com.parrotanalytics.api.request.userevent;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.parrotanalytics.apicore.model.request.IRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserEventRequest implements IRequest, Serializable
{

    private static final long serialVersionUID = 3511419098929726738L;

    @JsonProperty("event_id")
    private Integer eventId;

    @JsonProperty("user_id")
    private Integer userId;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("page")
    private String page;

    @JsonProperty("session_id")
    private String sessionId;

    @JsonProperty("page_ref")
    private String pageRef;

    @JsonProperty("market")
    private String market;

    @JsonProperty("platform")
    @SerializedName("platform")
    private String platform;

    @JsonProperty("section")
    private String section;

    @JsonProperty("rendering_state")
    private String renderingState;

    @JsonProperty("ui_event")
    private String uiEvent;

    @JsonProperty("previous_page")
    private String previousPage;

    @JsonProperty("domain")
    private String domain;

    public UserEventRequest()
    {
    }

}