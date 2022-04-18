package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import lombok.Data;

@Entity
@Data
@SqlResultSetMapping(name = "GenreEntityCount", classes =
    {
        @ConstructorResult(targetClass = GenreEntityCount.class, columns =
            {
                @ColumnResult(name = "genre", type = String.class),

                @ColumnResult(name = "count", type = Integer.class)
            })
    })
public class GenreEntityCount implements Serializable {

  private static final long serialVersionUID = -4485682182074687323L;

  @Id
  @JsonProperty("genre")
  private String genre;

  @JsonProperty("count")
  private Integer count;

}
