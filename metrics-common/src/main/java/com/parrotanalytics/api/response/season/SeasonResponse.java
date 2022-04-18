package com.parrotanalytics.api.response.season;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import com.parrotanalytics.apicore.model.catalogapi.metadata.CatalogItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.SeasonItem;
import com.parrotanalytics.apicore.model.catalogapi.metadata.TableConstants;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Minh Vu
 *
 */
@JsonPropertyOrder(
{
        "warning", "error", "pageinfo"
})
public class SeasonResponse extends APIResponse
{

    private static final long serialVersionUID = -9075939959303982649L;

    /*
     * Title
     */
    @SerializedName("title")
    @JsonProperty("title")
    public String title;

    /*
     * Parrot ID
     */

    @SerializedName("parrot_id")
    @JsonProperty("parrot_id")
    public String parrotID;

    /*
     * Short ID
     */
    @SerializedName(CatalogItem.SHORT_ID)
    @JsonProperty(CatalogItem.SHORT_ID)
    public Long shortID;

    @SerializedName(CatalogItem.DISPLAY_SERIALIZED_NAME)
    @JsonProperty(CatalogItem.DISPLAY_SERIALIZED_NAME)
    public String displayName;

    @SerializedName("description_lang")
    @JsonProperty("description_lang")
    protected String descriptionLang;

    @SerializedName("short_description")
    @JsonProperty("short_description")
    protected String shortDescription;

    @SerializedName("long_description")
    @JsonProperty("long_description")
    protected String longDescription;

    @SerializedName("total_seasons")
    @JsonProperty("total_seasons")
    private String totalSeasons;

    @SerializedName("total_episodes")
    @JsonProperty("total_episodes")
    private String totalEpisodes;

    @SerializedName("latest_season_date")
    @JsonProperty("latest_season_date")
    private String latestSeasonDate;

    @SerializedName("latest_episode_date")
    @JsonProperty("latest_episode_date")
    private String latestEpisodeDate;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getParrotID()
    {
        return parrotID;
    }

    public void setParrotID(String parrotID)
    {
        this.parrotID = parrotID;
    }

    public Long getShortID()
    {
        return shortID;
    }

    public void setShortID(Long shortID)
    {
        this.shortID = shortID;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public String getDescriptionLang()
    {
        return descriptionLang;
    }

    public void setDescriptionLang(String descriptionLang)
    {
        this.descriptionLang = descriptionLang;
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

    public String getTotalSeasons()
    {
        return totalSeasons;
    }

    public void setTotalSeasons(String totalSeasons)
    {
        this.totalSeasons = totalSeasons;
    }

    public String getTotalEpisodes()
    {
        return totalEpisodes;
    }

    public void setTotalEpisodes(String totalEpisodes)
    {
        this.totalEpisodes = totalEpisodes;
    }

    public String getLatestSeasonDate()
    {
        return latestSeasonDate;
    }

    public void setLatestSeasonDate(String latestSeasonDate)
    {
        this.latestSeasonDate = latestSeasonDate;
    }

    public String getLatestEpisodeDate()
    {
        return latestEpisodeDate;
    }

    public void setLatestEpisodeDate(String latestEpisodeDate)
    {
        this.latestEpisodeDate = latestEpisodeDate;
    }

    @JsonProperty("seasons")
    private List<SeasonItem> seasons;

    public SeasonResponse()
    {
        this(false, false);
    }

    public SeasonResponse(boolean errorEnabled, boolean warningEnabled)
    {
        super(errorEnabled, warningEnabled);
    }

    public List<SeasonItem> getSeasons()
    {
        return seasons;
    }

    public void setSeasons(List<SeasonItem> seasons)
    {
        this.seasons = seasons;
    }

    @JsonIgnore
    public void addItem(SeasonItem item)
    {
        if (seasons == null)
        {
            seasons = new ArrayList<SeasonItem>();
        }
        seasons.add(item);
    }

    @JsonIgnore
    public void addAllItems(List<SeasonItem> items)
    {
        if (seasons == null)
        {
            seasons = new ArrayList<SeasonItem>();
        }
        seasons.addAll(items);
    }

    @JsonIgnore
    public boolean isEmpty()
    {
        if (seasons == null || seasons.isEmpty())
        {
            return false;
        }
        return true;
    }

}
