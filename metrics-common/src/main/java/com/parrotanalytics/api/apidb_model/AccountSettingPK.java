package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class AccountSettingPK implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4104064511542465614L;

    @Column(name = "idAccount")
    private Integer idAccount;

    @Column(name = "settingName")
    private String settingName;

    /**
     * Instantiates a new user role pk.
     */
    public AccountSettingPK()
    {
    }

    public Integer getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(Integer idAccount)
    {
        this.idAccount = idAccount;
    }

    public String getSettingName()
    {
        return settingName;
    }

    public void setSettingName(String settingName)
    {
        this.settingName = settingName;
    }

}
