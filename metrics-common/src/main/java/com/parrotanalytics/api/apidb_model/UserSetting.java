package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rits.cloning.Cloner;

/**
 * The persistent class for the Products database table.
 * 
 */
@Entity
@Table(name = "usersettings", schema = "portal")
@IdClass(UserSettingPK.class)
public class UserSetting implements Serializable, Cloneable
{
    private static final long serialVersionUID = -4469877241713121095L;

    @Id
    @Column
    private Integer idUser;

    @Column
    private String settingType;

    @Id
    @Column
    private String settingName;

    @Column
    private String settingValue;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastUsedOn;

    public UserSetting()
    {
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public UserSetting withIdUser(Integer idUser)
    {
        this.idUser = idUser;
        return this;
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
    public UserSetting withSettingType(String settingType)
    {
        this.settingType = settingType;
        return this;
    }

    public String getSettingName()
    {
        return settingName;
    }

    public UserSetting withSettingName(String settingName)
    {
        this.settingName = settingName;
        return this;
    }

    public String getSettingValue()
    {
        return settingValue;
    }

    public UserSetting withSettingValue(String settingValue)
    {
        this.settingValue = settingValue;
        return this;
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
    public UserSetting withLastUsedOn(Date lastUsedOn)
    {
        this.lastUsedOn = lastUsedOn;
        return this;
    }

    @Override
    public UserSetting clone()
    {
        return new Cloner().deepClone(this);
    }

}