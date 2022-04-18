package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.IPortfolio;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Non Managed @Entity for Portfolio Details with items count
 *
 * @author sanjeev
 * @author Minh Vu
 */
@Entity
@SqlResultSetMapping(name = "PortfolioWithCount", classes =
    {
        @ConstructorResult(targetClass = PortfolioWithCount.class, columns =
            {
                @ColumnResult(name = "idPortfolio", type = Integer.class),
                @ColumnResult(name = "description", type = String.class),
                @ColumnResult(name = "name", type = String.class),
                @ColumnResult(name = "type", type = PortfolioType.class),
                @ColumnResult(name = "filter_value", type = String.class),
                @ColumnResult(name = "createdOn", type = Date.class),
                @ColumnResult(name = "updatedOn", type = Date.class),
                @ColumnResult(name = "managed", type = Integer.class),
                @ColumnResult(name = "shared", type = Boolean.class),
                @ColumnResult(name = "itemCount", type = Long.class),
                @ColumnResult(name = "entity", type = com.parrotanalytics.api.commons.constants.Entity.class)
            })
    })
@JsonPropertyOrder(
    {
        "portfolio_id", "name", "description", "managed", "shared", "itemcount", "created_on",
        "updated_on", "entity"
    })
@JsonInclude(Include.NON_NULL)
@Data
@NoArgsConstructor
public class PortfolioWithCount implements IPortfolio {

  private static final long serialVersionUID = 8610655579330610217L;

  @Id
  @Column
  @JsonProperty("portfolio_id")
  private Integer idPortfolio;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private Integer managed;

  @Column(name = "shared", columnDefinition = "TINYINT(1)")
  @JsonProperty("shared")
  private Boolean shared;

  @Column
  private Integer idUser;

  @Temporal(TemporalType.DATE)
  @Column(name = "created_on")
  @JsonProperty("created_on")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private Date createdOn;

  @Temporal(TemporalType.DATE)
  @Column(name = "updated_on")
  @JsonProperty("updated_on")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
  private Date updatedOn;

  @Column
  @JsonProperty("itemcount")
  private Long itemCount;

  @JsonProperty("type")
  @Column
  @Enumerated(EnumType.STRING)
  private PortfolioType type;

  @JsonProperty("entity")
  private com.parrotanalytics.api.commons.constants.Entity entity;

  @JsonProperty("sub_type")
  private String subType;

  @JsonProperty("filter_value")
  private String filterValue;

  public PortfolioWithCount(Integer idPortfolio, String name, String description,
      PortfolioType type,
      String filterValue, Date createdOn, Date updatedOn, Integer managed, Boolean shared,
      Long itemCount, com.parrotanalytics.api.commons.constants.Entity entity) {
    this.idPortfolio = idPortfolio;
    this.name = name;
    this.type = type;
    this.filterValue = filterValue;
    this.description = description;
    this.createdOn = createdOn;
    this.updatedOn = updatedOn;
    this.managed = managed;
    this.shared = shared;
    this.itemCount = itemCount;
    this.entity = entity;
  }
}
