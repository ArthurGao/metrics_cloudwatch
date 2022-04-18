package com.parrotanalytics.api.commons.constants;

/**
 * 
 * @author minhvu
 */
public enum ReleaseDateFilterType
{
    PREMIERE("premiere"),

    SEASON_EPISODE("season_episode"),

    PREMIERE_OR_SEASON_EPISODE("premiere_or_season_episode");

    private String value;

    private ReleaseDateFilterType(String type)
    {
        this.value = type;
    }

    /**
     * Value.
     * 
     * @return the string
     */
    public String value()
    {
        return this.value;
    }

    public static ReleaseDateFilterType fromValue(String value)
    {
        if (value != null)
        {
            for (ReleaseDateFilterType instance : ReleaseDateFilterType.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return SEASON_EPISODE;// default is season_episode release date
    }

    @Override
    public String toString()
    {
        return this.value;
    }

}
