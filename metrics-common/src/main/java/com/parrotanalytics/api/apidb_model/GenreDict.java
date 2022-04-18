package com.parrotanalytics.api.apidb_model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre_dict", schema = "metrics")
public class GenreDict implements Serializable
{

    private static final long serialVersionUID = -8124221445458192504L;

    @Id
    @Column(name = "genre_id")
    private Integer genreId;

    @Column(name = "genre")
    private String genre;

    public Integer getGenreId()
    {
        return genreId;
    }

    public void setGenreId(Integer genreId)
    {
        this.genreId = genreId;
    }

    public String getGenre()
    {
        return genre;
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
    }

}
