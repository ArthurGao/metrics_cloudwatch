package com.parrotanalytics.api.response.titlecontext;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.nonmanaged.CMSettingV2;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * @author Minh Vu
 */
@JsonPropertyOrder(
{})
@JsonInclude(Include.NON_NULL)
public class CMSettingsResponseV2 extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = -4874642415480717282L;

    public CMSettingsResponseV2()
    {
    }

    private List<CMSettingV2> settings;

    public List<CMSettingV2> getSettings()
    {
        return settings;
    }

    public void setSettings(List<CMSettingV2> settings)
    {
        this.settings = settings;
    }
}
