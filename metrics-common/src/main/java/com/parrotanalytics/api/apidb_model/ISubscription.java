package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;
import java.util.Date;

public interface ISubscription extends Serializable {

  public int getIdAccount();

  public void setIdAccount(int idAccount);

  public long getSubscriptionRefId();

  public void setSubscriptionRefId(long subscriptionRefId);

  public String getSubscriptionType();

  public void setSubscriptionType(String subscriptionType);

  public Date getSubscriptionStart();

  public void setSubscriptionStart(Date subscriptionStart);
}