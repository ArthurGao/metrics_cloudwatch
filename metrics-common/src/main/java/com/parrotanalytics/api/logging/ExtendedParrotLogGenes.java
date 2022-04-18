package com.parrotanalytics.api.logging;

import java.util.List;

/**
 * The Class ExtendedParrotLog adds the message, level and class fields to the
 * regular ParrotLog.
 * 
 * @author chris
 * @author Jackson Lee
 */
public class ExtendedParrotLogGenes
{
    private List<String> genre, mood, country, network;

    private List<Integer> episodes, releasedYears;

    public List<String> getGenre()
    {
        return genre;
    }

    public void setGenre(List<String> genre)
    {
        this.genre = genre;
    }

    public List<String> getMood()
    {
        return mood;
    }

    public void setMood(List<String> mood)
    {
        this.mood = mood;
    }

    public List<String> getCountry()
    {
        return country;
    }

    public void setCountry(List<String> country)
    {
        this.country = country;
    }

    public List<String> getNetwork()
    {
        return network;
    }

    public void setNetwork(List<String> network)
    {
        this.network = network;
    }

    public List<Integer> getEpisodes()
    {
        return episodes;
    }

    public void setEpisodes(List<Integer> episodes)
    {
        this.episodes = episodes;
    }

    public List<Integer> getReleasedYears()
    {
        return releasedYears;
    }

    public void setReleasedYears(List<Integer> releasedYears)
    {
        this.releasedYears = releasedYears;
    }

}
