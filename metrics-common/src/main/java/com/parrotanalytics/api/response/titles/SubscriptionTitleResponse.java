package com.parrotanalytics.api.response.titles;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.apicore.model.catalogapi.metadata.Images;
import com.parrotanalytics.apicore.model.response.APIResponse;
import com.parrotanalytics.commons.utils.CommonsUtil;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Sanjeev Sharma
 */
@JsonInclude(Include.NON_NULL)
public class SubscriptionTitleResponse extends APIResponse
{
    private static final long serialVersionUID = -6071632934390163778L;

    @JsonProperty("parrot_id")
    private String parrotId;

    private String title;

    @JsonProperty("onboardingdate")
    private String onboardingDate;

    @JsonProperty("subscription_start")
    protected String subscriptionStart;

    @JsonProperty("release_year")
    private String releaseYear;

    private Set<String> genres;

    @JsonProperty("genre_tags")
    private Set<String> genre_tags;

    private Set<String> networks;

    @JsonProperty("titleimage")
    private String titleImage;

    private Images images;

    private List<String> accounts;

    @JsonProperty("data_state")
    private String dataState;

    @JsonProperty("original_country")
    private String originalCountry;

    @JsonProperty("imdb_id")
    private String imdbId;

    public SubscriptionTitleResponse()
    {
    }

    public SubscriptionTitleResponse(String parrotId)
    {
        this.parrotId = parrotId;
    }

    public SubscriptionTitleResponse(String parrotId, String title)
    {
        this.parrotId = parrotId;
        this.title = title;
    }

    /**
     * @return the parrotId
     */
    public String getParrotId()
    {
        return parrotId;
    }

    /**
     * @param parrotId the parrotId to set
     */
    public void setParrotId(String parrotId)
    {
        this.parrotId = parrotId;
    }

    /**
     * @return the title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return the onboardingDate
     */
    public String getOnboardingDate()
    {
        return onboardingDate;
    }

    /**
     * @param onboardingDate the onboardingDate to set
     */
    public void setOnboardingDate(String onboardingDate)
    {
        this.onboardingDate = onboardingDate;
    }

    public String getSubscriptionStart()
    {
        return subscriptionStart;
    }

    public void setSubscriptionStart(String subscriptionStart)
    {
        this.subscriptionStart = subscriptionStart;
    }

    /**
     * @return the genres
     */
    public Set<String> getGenres()
    {
        return genres;
    }

    /**
     * @param genres the genres to set
     */
    public void setGenres(Set<String> genres)
    {
        this.genres = genres;
    }

    /**
     * @param genre
     */
    public void addGenre(String genre)
    {
        if (!CommonsUtil.isNullOrEmpty(genre))
        {
            if (this.genres == null)
            {
                this.genres = new LinkedHashSet<String>(1);
            }

            this.genres.add(genre);
        }
    }

    public Set<String> getNetworks()
    {
        return networks;
    }

    public void setNetworks(Set<String> networks)
    {
        this.networks = networks;
    }

    /**
     * @return the titleImage
     */
    public String getTitleImage()
    {
        return titleImage;
    }

    /**
     * @param titleImage the titleImage to set
     */
    public void setTitleImage(String titleImage)
    {
        this.titleImage = titleImage;
    }

    /**
     * @return the images
     */
    public Images getImages()
    {
        return images;
    }

    /**
     * @param images the images to set
     */
    public void setImages(Images images)
    {
        this.images = images;
    }

    /**
     * @return the dataState
     */
    public String getDataState()
    {
        return dataState;
    }

    /**
     * @param dataState the dataState to set
     */
    public void setDataState(String dataState)
    {
        this.dataState = dataState;
    }

    public List<String> getAccounts()
    {
        return accounts;
    }

    public void setAccounts(List<String> accounts)
    {
        this.accounts = accounts;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parrotId == null) ? 0 : parrotId.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubscriptionTitleResponse other = (SubscriptionTitleResponse) obj;
        if (parrotId == null)
        {
            if (other.parrotId != null)
                return false;
        }
        else if (!parrotId.equals(other.parrotId))
            return false;
        return true;
    }

    public String getReleaseYear()
    {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear)
    {
        this.releaseYear = releaseYear;
    }

    public String getOriginalCountry()
    {
        return originalCountry;
    }

    public void setOriginalCountry(String originalCountry)
    {
        this.originalCountry = originalCountry;
    }

    public String getImdbId()
    {
        return imdbId;
    }

    public void setImdbId(String imdbId)
    {
        this.imdbId = imdbId;
    }

    public Set<String> getGenre_tags()
    {
        return genre_tags;
    }

    public void setGenre_tags(Set<String> genre_tags)
    {
        this.genre_tags = genre_tags;
    }
}
