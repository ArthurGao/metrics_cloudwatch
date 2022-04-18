package com.parrotanalytics.api.response.titlecontext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.parrotanalytics.api.apidb_model.ContextMetricsSetting;
import com.parrotanalytics.api.apidb_model.GenreDict;
import com.parrotanalytics.api.util.DemandHelper;
import com.parrotanalytics.apicore.config.APIConstants;
import com.parrotanalytics.apicore.exceptions.APIException;

/**
 * @author Minh Vu
 */
@JsonInclude(Include.NON_NULL)
public class CMSetting implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -5733533840516528076L;

    private String metric_type;

    private String title;

    @Column(name = "min_value")
    private int min_value;

    @Column(name = "max_value")
    private int max_value;

    @JsonProperty("genres")
    private Map<String, GenreSetting> genres;
    private String period;

    public CMSetting(List<ContextMetricsSetting> contextMetricsSettings, List<GenreDict> genreDicts)
    {
        genres = new HashMap<>();
        final Map<Integer, List<GenreDict>> genreMap = genreDicts.stream()
                .collect(Collectors.groupingBy(GenreDict::getGenreId));
        contextMetricsSettings.forEach(c -> {
            this.metric_type = c.getMetric_type();
            this.title = c.getDisplay_name();
            this.period = c.getPeriod();
            Object labelBuckets = null;
            try
            {
                labelBuckets = DemandHelper.parseValue(c);
            }
            catch (APIException e)
            {
                labelBuckets = new ArrayList<>();
            }
            GenreDict genre = CollectionUtils.isNotEmpty(genreMap.get(c.getGenreId()))
                    ? genreMap.get(c.getGenreId()).get(0)
                    : null;
            if (genre != null)
                genres.put(genre.getGenre(), new GenreSetting(c.getMin_value(), c.getMax_value(), labelBuckets));
        });
        Optional<GenreDict> overallGenre = genreDicts.stream().filter(g -> g.getGenre().equals(APIConstants.OVERALL))
                .findFirst();

        if (overallGenre != null)
        {

            Optional<ContextMetricsSetting> overall = contextMetricsSettings.stream()
                    .filter(c -> overallGenre.get().getGenreId().equals(c.getGenreId())).findFirst();
            if (overall.isPresent())
            {
                this.min_value = overall.get().getMin_value();
                this.max_value = overall.get().getMax_value();
            }
            else
            {
                this.min_value = contextMetricsSettings.get(0).getMin_value();
                this.max_value = contextMetricsSettings.get(0).getMax_value();
            }

        }

    }

    public int getMin_value()
    {
        return min_value;
    }

    public void setMin_value(int min_value)
    {
        this.min_value = min_value;
    }

    public String getMetric_type()
    {
        return metric_type;
    }

    public void setMetric_type(String metric_type)
    {
        this.metric_type = metric_type;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public Map<String, GenreSetting> getGenres()
    {
        return genres;
    }

    public void setGenres(Map<String, GenreSetting> genres)
    {
        this.genres = genres;
    }

    public int getMax_value()
    {
        return max_value;
    }

    public void setMax_value(int max_value)
    {
        this.max_value = max_value;
    }
}
