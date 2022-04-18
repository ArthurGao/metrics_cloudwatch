package com.parrotanalytics.api.response.customevent;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.parrotanalytics.api.apidb_model.CustomEvent;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Minh Vu
 *
 */
@JsonPropertyOrder(
{
        "warning", "error", "pageinfo"
})
public class CustomEventsResponse extends APIResponse
{

    private static final long serialVersionUID = 5711183275095011134L;

    @SerializedName("custom_events")
    @JsonProperty("custom_events")
    private List<CustomEventResponse> customEvents;

    public CustomEventsResponse()
    {

    }

    public List<CustomEventResponse> getCustomEvents()
    {
        return customEvents;
    }

    public void setCustomEvents(List<CustomEventResponse> customEvents)
    {
        this.customEvents = customEvents;
    }

}
