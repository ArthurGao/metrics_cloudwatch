package com.parrotanalytics.api.apidb_model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.nonmanaged.PortfolioType;
import com.parrotanalytics.api.commons.ParrotEntityConverter;
import com.parrotanalytics.api.response.portfolios.PortfolioTitleResponse;
import com.rits.cloning.Cloner;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Rajangam Ganesan
 * @author Minh Vu
 * <p>
 * The persistent class for the Portfolio database table. Portfolio object should be a simple POJO.
 * Please dont complicate the responsiblity of JPA entity object.
 */
@Entity
@Table(name = "portfolio", schema = "portal")
@NamedQuery(name = "Portfolio.findAll", query = "SELECT p FROM Portfolio p")
@JsonPropertyOrder(
    {
        "portfolio_id", "name", "type", "entity", "description", "shared", "datecreated",
        "portfolioitems"
    })
@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class Portfolio implements IPortfolio, Cloneable {

  private static final long serialVersionUID = -7854654973932320655L;

  @Transient
  @JsonProperty("content")
  private List<String> content;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonIgnore
  @Column(name = "created_on")
  private Date createdOn;

  @JsonProperty("description")
  private String description;

  @JsonProperty("filters")
  @Column(name = "filters")
  private HashMap<String, String> filters;

  @Column(name = "idAccount")
  @JsonProperty("id_account")
  private Integer idAccount;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("portfolio_id")
  private Integer idPortfolio;

  @Column(name = "idUser")
  @JsonIgnore
  private Integer idUser;

  @Column(name = "last_modified_by")
  @JsonProperty("last_modified_by")
  private Integer lastModifiedBy;

  @JsonProperty("managed")
  @Column(name = "managed", columnDefinition = "TINYINT(1)")
  private Integer managed;

  @JsonProperty("name")
  @Column(name = "name")
  private String name;

  @Transient
  @JsonProperty("portfolioitems")
  private List<PortfolioTitleResponse> pfItemTitles;

  // internal use
  @Transient
  @JsonProperty("short_ids")
  private List<Long> shortIDList;

  // internal use
  @Transient
  @JsonProperty("string_id")
  private String stringID;

  @Transient
  @JsonProperty("label")
  private String label;

  @Column(name = "shared", columnDefinition = "TINYINT(1)")
  @JsonProperty("shared")
  private Boolean shared;

  @JsonProperty("type")
  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  private PortfolioType type;

  @JsonProperty("filter_value")
  @Column(name = "filter_value")
  private String filterValue;

  @Temporal(TemporalType.TIMESTAMP)
  @JsonIgnore
  @Column(name = "updated_on")
  private Date updatedOn;

  @JsonProperty("entity")
  @Column(name = "entity")
  @Convert(converter = ParrotEntityConverter.class)
  private com.parrotanalytics.api.commons.constants.Entity entity;

  @Override
  public Portfolio clone() {
    return new Cloner().deepClone(this);
  }

}