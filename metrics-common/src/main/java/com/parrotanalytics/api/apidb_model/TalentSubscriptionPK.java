package com.parrotanalytics.api.apidb_model;

import com.parrotanalytics.api.commons.constants.SubscriptionType;
import java.io.Serializable;
import javax.persistence.Column;
import lombok.Data;

@Data
public class TalentSubscriptionPK implements Serializable {

  private static final long serialVersionUID = -9045120821268954106L;

  @Column(name = "idAccount")
  private int idAccount;

  @Column(name = "subscriptionRefId")
  private long subscriptionRefId;

  @Column(name = "subscriptionType")
  private String subscriptionType;

  public TalentSubscriptionPK() {
  }

  public TalentSubscriptionPK(int idAccount, long subscriptionRefId, String subscriptionType) {
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = subscriptionType;
  }

  public TalentSubscriptionPK(int idAccount, long subscriptionRefId) {
    this.idAccount = idAccount;
    this.subscriptionRefId = subscriptionRefId;
    this.subscriptionType = SubscriptionType.TALENT.value();
  }
}
