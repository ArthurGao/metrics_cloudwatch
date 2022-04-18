package com.parrotanalytics.api.commons.constants;

public enum ProductModule
{
    DEMAND_PORTAL(1, "Demand Portal"),

    ADDON_HISTORICALDATAACCESS(2, "Historical Data Access"),

    ADDON_GLOBALTRENDS(3, "Global Trends"),

    ADDON_TOPCONTENT(4, "TopContent Module"),

    ADDON_DEMANDBREAKDOWN(5, "read"),

    ADDON_TALENT_ENTERPRISE_MODULE(7, "Talent Enterprise Module"),

    ADDON_TALENT_LITE_VIEWER(8, "Talent Lite Viewer"),

    ADDON_MOVIE_ENTERPRISE_MODULE(9, "Movie Enterprise Module"),

    ADDON_MOVIE_LITE_VIEWER(10, "Movie Lite Viewer");

    private Integer id;
    private String name;

    private ProductModule(Integer id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Value.
     * 
     * @return the string
     */
    public String value()
    {
        return this.name;
    }

    public Integer id()
    {
        return this.id;
    }

    public static ProductModule fromID(Integer id)
    {
        if (id != null)
        {
            for (ProductModule module : ProductModule.values())
            {
                if (id.equals(module.id()))
                {
                    return module;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.name;
    }

}