package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the Label database table.
 *
 */
@Entity
@Table(name = "label", schema = "portal")
@IdClass(LabelPK.class)
@NamedQuery(name = "Label.findAll", query = "SELECT u FROM Label u")
public class Label implements Cloneable, Serializable {

	@Id
	@Column(name = "idAccount")
	private Integer idAccount;

	@Id
	@Column(name = "short_id")
	private Long shortId;

	@Id
	@Column(name = "label")
	private String label;

	public Label() {

	}

	public Label(Integer idAccount, Long shortId, String label) {
		super();
		this.idAccount = idAccount;
		this.shortId = shortId;
		this.label = label;
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
		if (!(obj instanceof Label)) {
			return false;
		}
		Label other = (Label) obj;
		return this.idAccount.equals(other.idAccount) && this.shortId.equals(other.shortId)
				&& this.label.equals(other.label);
	}

}
