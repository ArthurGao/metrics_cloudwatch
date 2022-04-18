package com.parrotanalytics.api.request.user;

import com.parrotanalytics.apicore.model.request.APIRequest;

public class UserSettingRequest extends APIRequest
{
    protected String avatarAlias;

    protected String homeMarket;

    public String getAvatarAlias()
    {
        return avatarAlias;
    }

    public void setAvatarAlias(String avatarAlias)
    {
        this.avatarAlias = avatarAlias;
    }

    public String getHomeMarket()
    {
        return homeMarket;
    }

    public void setHomeMarket(String homeMarket)
    {
        this.homeMarket = homeMarket;
    }

}
