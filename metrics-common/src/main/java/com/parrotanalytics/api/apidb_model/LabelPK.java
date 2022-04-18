package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

public class LabelPK implements Cloneable, Serializable {

	private Integer idAccount;
	
	private Long shortId;
	
	private String label;

	public LabelPK() {

	}


	public Integer getIdAccount() {
		return idAccount;
	}

	public void setIdAccount(Integer idAccount) {
		this.idAccount = idAccount;
	}

	public Long getShortId() {
		return shortId;
	}

	public void setShortId(Long shortId) {
		this.shortId = shortId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idAccount == null) ? 0 : idAccount.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((shortId == null) ? 0 : shortId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LabelPK)) {
			return false;
		}
		LabelPK other = (LabelPK) obj;
		return this.idAccount.equals(other.idAccount) && this.shortId.equals(other.shortId)
				&& this.label.equals(other.label);
	}

}
