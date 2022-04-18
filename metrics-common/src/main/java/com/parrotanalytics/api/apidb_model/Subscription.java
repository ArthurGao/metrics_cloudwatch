package com.parrotanalytics.api.apidb_model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * The persistent class for the Subscriptions database table.
 */
@Entity
@Table(name = "subscriptions", schema = "subscription")
@NamedQuery(name = "Subscription.findAll", query = "SELECT s FROM Subscription s")
@IdClass(value = SubscriptionPK.class)
@Data
public class Subscription implements ISubscription {

  private static final long serialVersionUID = -1311679292096181373L;

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

  /**
   * Instantiates a new subscription.
   */
  public Subscription() {
  }

  public Subscription(int idAccount, long subscriptionRefId, String subscriptionType,
      Date subscriptionStart) {
    super();
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = subscriptionType;
    this.subscriptionStart = subscriptionStart;
  }

  @Override
  public String toString() {
    return "Subscription [idAccount=" + idAccount + ", subscriptionType=" + subscriptionType
        + ", subscriptionRefId=" + subscriptionRefId + "]";
  }

}