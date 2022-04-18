package com.parrotanalytics.api.apidb_model;

import com.parrotanalytics.api.commons.constants.SubscriptionType;
import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data
public class MovieSubscriptionPK implements Serializable {

  private static final long serialVersionUID = -9045120821258954106L;

  @Column(name = "idAccount")
  private int idAccount;

  @Column(name = "subscriptionRefId")
  private long subscriptionRefId;

  @Column(name = "subscriptionType")
  private String subscriptionType;

  public MovieSubscriptionPK() {
  }

  public MovieSubscriptionPK(int idAccount, long subscriptionRefId, String subscriptionType) {
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = subscriptionType;
  }

  public MovieSubscriptionPK(int idAccount, long subscriptionRefId) {
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = SubscriptionType.MOVIE.value();
  }

}
