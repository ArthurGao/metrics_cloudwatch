package com.parrotanalytics.api.response.userevent;

import com.google.gson.annotations.SerializedName;
import com.parrotanalytics.apicore.model.response.APIResponse;
import org.codehaus.jackson.annotate.JsonProperty;

public class UserEventResponse extends APIResponse
{

    private static final long serialVersionUID = 3511419098929726738L;

    @JsonProperty("event_id")
    private Integer event_id;

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("ip_address")
    private String ip_address;

    @JsonProperty("page")
    private String page;

    @JsonProperty("session_id")
    private String session_id;

    @JsonProperty("page_ref")
    private String page_ref;

    @JsonProperty("market")
    private String market;

    @JsonProperty("platform")
    @SerializedName("platform")
    private String platform;

    @JsonProperty("section")
    private String section;

    @JsonProperty("rendering_state")
    private String rendering_state;

    @JsonProperty("ui_event")
    private String ui_event;

    @JsonProperty("previous_page")
    private String previous_page;

    @JsonProperty("domain")
    @SerializedName("domain")
    private String domain;

    public UserEventResponse()
    {
    }

    public Integer getEvent_id()
    {
        return event_id;
    }

    public void setEvent_id(Integer event_id)
    {
        this.event_id = event_id;
    }

    public String getIp_address()
    {
        return ip_address;
    }

    public void setIp_address(String ip_address)
    {
        this.ip_address = ip_address;
    }

    public String getPage()
    {
        return page;
    }

    public void setPage(String page)
    {
        this.page = page;
    }

    public String getSession_id()
    {
        return session_id;
    }

    public void setSession_id(String session_id)
    {
        this.session_id = session_id;
    }

    public String getPage_ref()
    {
        return page_ref;
    }

    public void setPage_ref(String page_ref)
    {
        this.page_ref = page_ref;
    }

    public String getMarket()
    {
        return market;
    }

    public void setMarket(String market)
    {
        this.market = market;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public String getSection()
    {
        return section;
    }

    public void setSection(String section)
    {
        this.section = section;
    }

    public String getRendering_state()
    {
        return rendering_state;
    }

    public void setRendering_state(String rendering_state)
    {
        this.rendering_state = rendering_state;
    }

    public String getUi_event()
    {
        return ui_event;
    }

    public void setUi_event(String ui_event)
    {
        this.ui_event = ui_event;
    }

    public String getPrevious_page()
    {
        return previous_page;
    }

    public void setPrevious_page(String previous_page)
    {
        this.previous_page = previous_page;
    }

    public String getDomain()
    {
        return domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public Integer getUser_id()
    {
        return user_id;
    }

    public void setUser_id(Integer user_id)
    {
        this.user_id = user_id;
    }
}