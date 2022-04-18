package com.parrotanalytics.api.apidb_model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * The persistent class for the Talent Subscriptions database table.
 */
@Entity
@Table(name = "talent_subscriptions", schema = "subscription")
@IdClass(value = TalentSubscriptionPK.class)
@Data
public class TalentSubscription implements ISubscription {

  private static final long serialVersionUID = -2488999794757962860L;

  @Id
  @Column(name = "idAccount")
  private int idAccount;

  @Id
  @Column(name = "subscriptionType")
  private String subscriptionType;

  @Id
  @Column(name = "subscriptionRefId")
  private long subscriptionRefId;

  @Temporal(TemporalType.DATE)
  @Column(name = "subscriptionStart")
  private Date subscriptionStart;

  public TalentSubscription() {
  }

  public TalentSubscription(int idAccount, long subscriptionRefId, String subscriptionType,
      Date subscriptionStart) {
    super();
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = subscriptionType;
    this.subscriptionStart = subscriptionStart;
  }

  @Override

  public String toString() {
    return "TalentSubscription [idAccount=" + idAccount + ", subscriptionType=" + subscriptionType
        + ", subscriptionRefId=" + subscriptionRefId + "]";
  }

}