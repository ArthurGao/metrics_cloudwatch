package com.parrotanalytics.api.apidb_model.nonmanaged;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.datasourceint.dsdb.model.base.NonManagedEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@SqlResultSetMapping(name = "GlobalGenrePerformance", classes = {
        @ConstructorResult(targetClass = GlobalGenrePerformance.class, columns = {
                @ColumnResult(name = "date", type = Date.class),

                @ColumnResult(name = "country", type = String.class),

                @ColumnResult(name = "short_id", type = Long.class),

                @ColumnResult(name = "genre", type = String.class),

                @ColumnResult(name = "global_avg", type = Double.class),

                @ColumnResult(name = "genre_avg", type = Double.class),

        })
})
public class GlobalGenrePerformance implements NonManagedEntity
{

    private static final long serialVersionUID = -7259954277274320707L;

    @Id
    @Temporal(TemporalType.DATE)
    @JsonProperty("date")
    private Date date;

    @Id
    @JsonProperty("country")
    private String country;

    @Id
    @JsonProperty("short_id")
    private Long short_id;

    @Id
    @JsonProperty("genre")
    private String genre;

    @JsonProperty("genre_avg")
    private Double genre_avg;

    @JsonProperty("global_avg")
    private Double global_avg;

    public GlobalGenrePerformance()
    {
        super();
    }

    public GlobalGenrePerformance(Date date, String country, Long short_id, String genre, Double global_avg,
            Double genre_avg)
    {
        this.date = date;
        this.country = country;
        this.short_id = short_id;
        this.genre = genre;
        this.global_avg = global_avg;
        this.genre_avg = genre_avg;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

    public Double getGenre_avg()
    {
        return genre_avg;
    }

    public void setGenre_avg(Double genre_avg)
    {
        this.genre_avg = genre_avg;
    }

    public Double getGlobal_avg()
    {
        return global_avg;
    }

    public void setGlobal_avg(Double global_avg)
    {
        this.global_avg = global_avg;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Long getShort_id()
    {
        return short_id;
    }

    public void setShort_id(Long short_id)
    {
        this.short_id = short_id;
    }
}
