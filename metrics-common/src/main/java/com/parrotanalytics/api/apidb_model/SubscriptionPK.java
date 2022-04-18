package com.parrotanalytics.api.apidb_model;

import javax.persistence.Column;
import java.io.Serializable;

public class SubscriptionPK implements Serializable
{

    private static final long serialVersionUID = 6594158933707390501L;

    @Column(name = "idAccount")
    private int idAccount;

    @Column(name = "subscriptionRefId")
    private long subscriptionRefId;

    @Column(name = "subscriptionType")
    private String subscriptionType;

    public SubscriptionPK()
    {
    }

    public SubscriptionPK(int idAccount, long subscriptionRefId, String subscriptionType)
    {
        this.idAccount = idAccount;
        this.subscriptionRefId = subscriptionRefId;
        this.subscriptionType = subscriptionType;
    }

    public SubscriptionPK(int idAccount, long subscriptionRefId)
    {
        this.idAccount = idAccount;
        this.subscriptionRefId = subscriptionRefId;
        this.subscriptionType = "tvtitle";
    }

    public int getIdAccount()
    {
        return idAccount;
    }

    public void setIdAccount(int idAccount)
    {
        this.idAccount = idAccount;
    }

    public long getSubscriptionRefId()
    {
        return subscriptionRefId;
    }

    public void setSubscriptionRefId(long subscriptionRefId)
    {
        this.subscriptionRefId = subscriptionRefId;
    }

    public String getSubscriptionType()
    {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType)
    {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        SubscriptionPK that = (SubscriptionPK) o;

        if (idAccount != that.idAccount)
            return false;
        if (subscriptionRefId != that.subscriptionRefId)
            return false;
        return subscriptionType.equals(that.subscriptionType);
    }

    @Override
    public int hashCode()
    {
        int result = idAccount;
        result = 31 * result + (int) (subscriptionRefId ^ (subscriptionRefId >>> 32));
        result = 31 * result + subscriptionType.hashCode();
        return result;
    }
}
