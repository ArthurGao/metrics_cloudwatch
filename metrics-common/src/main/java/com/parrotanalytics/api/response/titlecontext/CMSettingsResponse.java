package com.parrotanalytics.api.response.titlecontext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.apicore.model.response.APIResponse;

import java.util.List;

/**
 * @author Minh Vu
 */
@JsonPropertyOrder({})
@JsonInclude(Include.NON_NULL)
public class CMSettingsResponse extends APIResponse
{
    public CMSettingsResponse()
    {

    }

    private List<CMSetting> settings;

    public List<CMSetting> getSettings()
    {
        return settings;
    }

    public void setSettings(List<CMSetting> settings)
    {
        this.settings = settings;
    }
}
