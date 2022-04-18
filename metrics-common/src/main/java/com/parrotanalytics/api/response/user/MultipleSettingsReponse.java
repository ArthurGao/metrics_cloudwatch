package com.parrotanalytics.api.response.user;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * Model class for multiple user settings response
 * 
 * @author Raja
 *
 */
@JsonPropertyOrder(
{
        "settings"
})
@JsonInclude(Include.NON_NULL)
public class MultipleSettingsReponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private List<Map<String, Object>> settings;

    public void addSetting(Setting setting)
    {
        if (setting != null && setting.getSettingValue() != null)
        {
            if (this.settings == null)
            {
                settings = new LinkedList<Map<String, Object>>();
            }
            
            Map<String, Object> settingMap = new HashMap<String, Object>();
            settingMap.put("user_id", setting.getIdUser());
            //settingMap.put("account_id", setting.getIdAccount());
            settingMap.put("setting_type", setting.getSettingType());
            settingMap.put("setting_name", setting.getSettingName());
            settingMap.put("setting_value", setting.getSettingValue());
            settingMap.put("requested_time", setting.getLastUsedOn());

            this.settings.add(settingMap);
        }

    }

    /**
     * @return the settings
     */
    public List<Map<String, Object>> getSettings()
    {
        return settings;
    }

    /**
     * @param settings
     *            the settings to set
     */
    public void setSettings(List<Map<String, Object>> settings)
    {
        this.settings = settings;
    }

}
