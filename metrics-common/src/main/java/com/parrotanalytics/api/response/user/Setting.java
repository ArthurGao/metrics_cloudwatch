package com.parrotanalytics.api.response.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Model class for user settings response
 * 
 * @author Sanjeev Sharma
 *
 */
@JsonPropertyOrder(
{
	"idUser", "settingType", "settingName", "settingValue", "lastUsedOn"
})
@JsonInclude(Include.NON_NULL)
public class Setting
{
	private Integer idUser;
	
    private String settingType;

    private String settingName;

    private Object settingValue;

    private Date lastUsedOn;

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
     * @return the lastUsedOn
     */
    public Date getLastUsedOn()
    {
        return lastUsedOn;
    }

    /**
     * @param lastUsedOn
     *            the lastUsedOn to set
     */
    public void setLastUsedOn(Date lastUsedOn)
    {
        this.lastUsedOn = lastUsedOn;
    }

    /**
     * @return the idUser
     */
	public Integer getIdUser() {
		return idUser;
	}

	/**
     * @param idUser
     *            the idUser to set
     */
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

}
