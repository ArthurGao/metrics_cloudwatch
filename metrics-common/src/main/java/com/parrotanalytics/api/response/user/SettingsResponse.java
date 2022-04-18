package com.parrotanalytics.api.response.user;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * Model class for user settings response
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
        "settings"
})
@JsonInclude(Include.NON_NULL)
public class SettingsResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Map<String, Map<String, Object>> settings;

    public void addSetting(Setting setting)
    {
        if (setting != null && setting.getSettingValue() != null)
        {
            if (this.settings == null)
            {
                settings = new HashMap<String, Map<String, Object>>();
            }

            if (this.settings.get(setting.getSettingType()) == null)
            {
                this.settings.put(setting.getSettingType(), new HashMap<String, Object>());
            }

            this.settings.get(setting.getSettingType()).put(setting.getSettingName(), setting.getSettingValue());
        }

    }

    /**
     * @return the settings
     */
    public Map<String, Map<String, Object>> getSettings()
    {
        return settings;
    }

    /**
     * @param settings
     *            the settings to set
     */
    public void setSettings(Map<String, Map<String, Object>> settings)
    {
        this.settings = settings;
    }

}
