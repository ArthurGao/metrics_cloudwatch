package com.parrotanalytics.api.response.customerapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.commons.model.DataPoint;
import com.parrotanalytics.apicore.model.response.APIResponse;

import java.util.List;

@JsonPropertyOrder({ "parrot_id", "title", "imdb_id", "idmb_rating", "genes", "subgenes"
})
@JsonInclude(Include.NON_NULL)
public class CustomerTitle extends APIResponse
{
    private static final long serialVersionUID = -2783184443312278667L;

    @JsonProperty("parrot_id")
    private String parrotId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("imdb_rating")
    private Double imdb_rating;

    @JsonProperty("genres")
    private String genres;

    @JsonProperty("subgenres")
    private String subgenres;

    @JsonProperty("short_description")
    private String shortDescription;

    @JsonProperty("long_description")
    private String longDescription;

    @JsonProperty("original_country")
    private String original_country;

    @JsonProperty("original_language")
    private String original_language;

    @JsonProperty("original_network")
    private String original_network;

    @JsonProperty("release_type")
    private String releaseType;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("genre_tags")
    private String genre_tags;

    @JsonProperty("affinity")
    private List<DataPoint> affinity;

    @JsonProperty("minimum_demand_date")
    private String minimumDemandDate;

    @JsonProperty("release_year")
    private String releaseYear;
    public void setParrotId(String parrotId) {
        this.parrotId = parrotId;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return this.genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSubgenres() {
        return this.subgenres;
    }

    public void setSubgenres(String subgenres) {
        this.subgenres = subgenres;
    }

    public String getReleaseYear() {
        return this.releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getOriginal_country()
    {
        return original_country;
    }

    public void setOriginal_country(String original_country)
    {
        this.original_country = original_country;
    }

    public String getOriginal_language()
    {
        return original_language;
    }

    public void setOriginal_language(String original_language)
    {
        this.original_language = original_language;
    }

    public String getOriginal_network()
    {
        return original_network;
    }

    public void setOriginal_network(String original_network)
    {
        this.original_network = original_network;
    }

    public String getGenre_tags()
    {
        return genre_tags;
    }

    public void setGenre_tags(String genre_tags)
    {
        this.genre_tags = genre_tags;
    }

    public Double getImdb_rating()
    {
        return imdb_rating;
    }

    public void setImdb_rating(Double imdb_rating)
    {
        this.imdb_rating = imdb_rating;
    }

    public String getParrotId()
    {
        return parrotId;
    }

    public CustomerTitle withParrotId(String parrotId)
    {
        this.parrotId = parrotId;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public CustomerTitle withTitle(String title)
    {
        this.title = title;
        return this;
    }

    public CustomerTitle withImdbId(String idmbId)
    {
        this.imdbId = idmbId;
        return this;
    }

    public CustomerTitle withGenres(String genres)
    {
        this.genres = genres;
        return this;
    }

    public CustomerTitle withSubgenres(String subgenres)
    {
        this.subgenres = subgenres;
        return this;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((genres == null) ? 0 : genres.hashCode());
        result = prime * result + ((imdbId == null) ? 0 : imdbId.hashCode());
        result = prime * result + ((parrotId == null) ? 0 : parrotId.hashCode());
        result = prime * result + ((subgenres == null) ? 0 : subgenres.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomerTitle other = (CustomerTitle) obj;
        if (genres == null)
        {
            if (other.genres != null)
                return false;
        }
        else if (!genres.equals(other.genres))
            return false;
        if (imdbId == null)
        {
            if (other.imdbId != null)
                return false;
        }
        else if (!imdbId.equals(other.imdbId))
            return false;
        if (parrotId == null)
        {
            if (other.parrotId != null)
                return false;
        }
        else if (!parrotId.equals(other.parrotId))
            return false;
        if (subgenres == null)
        {
            if (other.subgenres != null)
                return false;
        }
        else if (!subgenres.equals(other.subgenres))
            return false;
        if (title == null)
        {
            if (other.title != null)
                return false;
        }
        else if (!title.equals(other.title))
            return false;
        return true;
    }

    public String getImdbId()
    {
        return imdbId;
    }

    public void setImdbId(String imdbId)
    {
        this.imdbId = imdbId;
    }

    public String getShortDescription()
    {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription()
    {
        return longDescription;
    }

    public void setLongDescription(String longDescription)
    {
        this.longDescription = longDescription;
    }

    public List<DataPoint> getAffinity()
    {
        return affinity;
    }

    public void setAffinity(List<DataPoint> affinity)
    {
        this.affinity = affinity;
    }

    public String getReleaseType()
    {
        return releaseType;
    }

    public void setReleaseType(String releaseType)
    {
        this.releaseType = releaseType;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getMinimumDemandDate()
    {
        return minimumDemandDate;
    }

    public void setMinimumDemandDate(String minimumDemandDate)
    {
        this.minimumDemandDate = minimumDemandDate;
    }


}
