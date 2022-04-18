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
@Table(name = "accountsettings", schema = "portal")
@IdClass(AccountSettingPK.class)
public class AccountSetting implements Serializable, Cloneable
{
    private static final long serialVersionUID = -4469877241713121095L;

    @Id
    @Column
    private Integer idAccount;

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

    public AccountSetting()
    {
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public AccountSetting withIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
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
    public AccountSetting withSettingType(String settingType)
    {
        this.settingType = settingType;
        return this;
    }

    public String getSettingName()
    {
        return settingName;
    }

    public AccountSetting withSettingName(String settingName)
    {
        this.settingName = settingName;
        return this;
    }

    public String getSettingValue()
    {
        return settingValue;
    }

    public AccountSetting withSettingValue(String settingValue)
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
    public AccountSetting withLastUsedOn(Date lastUsedOn)
    {
        this.lastUsedOn = lastUsedOn;
        return this;
    }

    @Override
    public AccountSetting clone()
    {
        return new Cloner().deepClone(this);
    }

}