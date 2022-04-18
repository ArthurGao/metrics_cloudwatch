package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;

public class UserSettingPK implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4104064511542465614L;

    @Column(name = "idUser")
    private Integer idUser;

    @Column(name = "settingName")
    private String settingName;

    /**
     * Instantiates a new user role pk.
     */
    public UserSettingPK()
    {
    }

    public Integer getIdUser()
    {
        return idUser;
    }

    public void setIdUser(Integer idUser)
    {
        this.idUser = idUser;
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
