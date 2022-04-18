package com.parrotanalytics.api.request.user;

import org.joda.time.DateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.parrotanalytics.api.commons.constants.OperationType;
import com.parrotanalytics.api.serialization.SettingValueDeseializer;
import com.parrotanalytics.apicore.model.request.APIRequest;

/**
 * POJO model class for User Settings Requests
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonDeserialize(using = SettingValueDeseializer.class)
public class SettingsRequest extends APIRequest
{
    private OperationType operationType;

    private String settingType;

    private String settingName;

    private Object settingValue;

    private boolean valueRequired;

    private String renameTo;

    private DateTime lastUsed;

    public SettingsRequest()
    {

    }

    public SettingsRequest(String settingType, String settingName, Object settingValue)
    {
        this(settingType, settingName, settingValue, null);
    }

    public SettingsRequest(String settingType, String settingName, Object settingValue, DateTime lastUsed)
    {
        this.settingType = settingType;
        this.settingName = settingName;
        this.settingValue = settingValue;
        this.lastUsed = lastUsed;
    }

    /**
     * @return the operationType
     */
    public OperationType getOperationType()
    {
        return operationType;
    }

    /**
     * @param operationType
     *            the operationType to set
     */
    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    /**
     * @return the settingType
     */
    public String getSettingType()
    {
        return settingType;
    }

    /**
     * @param settingType
     *            the settingType to set
     */
    public void setSettingType(String settingType)
    {
        this.settingType = settingType;
    }

    /**
     * @return the settingName
     */
    public String getSettingName()
    {
        return settingName;
    }

    /**
     * @param settingName
     *            the settingName to set
     */
    public void setSettingName(String settingName)
    {
        this.settingName = settingName;
    }

    /**
     * @return the valueRequired
     */
    public boolean isValueRequired()
    {
        return valueRequired;
    }

    /**
     * @param valueRequired
     *            the valueRequired to set
     */
    public void setValueRequired(boolean valueRequired)
    {
        this.valueRequired = valueRequired;
    }

    /**
     * @return the renameTo
     */
    public String getRenameTo()
    {
        return renameTo;
    }

    /**
     * @param renameTo
     *            the renameTo to set
     */
    public void setRenameTo(String renameTo)
    {
        this.renameTo = renameTo;
    }

    /**
     * @return the settingValue
     */
    public Object getSettingValue()
    {
        return settingValue;
    }

    /**
     * @param settingValue
     *            the settingValue to set
     */
    public void setSettingValue(Object settingValue)
    {
        this.settingValue = settingValue;
    }

    /**
     * @return the lastUsed
     */
    public DateTime getLastUsed()
    {
        return lastUsed;
    }

    /**
     * @param lastUsed
     *            the lastUsed to set
     */
    public void setLastUsed(DateTime lastUsed)
    {
        this.lastUsed = lastUsed;
    }

}
