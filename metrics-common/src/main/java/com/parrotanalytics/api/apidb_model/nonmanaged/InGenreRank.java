package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import lombok.Data;

@Entity
@Data
@SqlResultSetMapping(name = "InGenreRank", classes =
    {
        @ConstructorResult(targetClass = InGenreRank.class, columns =
            {
                @ColumnResult(name = "genre", type = String.class),

                @ColumnResult(name = "rank_by_genre", type = Integer.class)
            })
    })
public class InGenreRank {

  @Id
  @JsonProperty("genre")
  private String genre;

  @Column(name = "rank_by_genre")
  @JsonProperty("rank")
  private Integer rank;

}
