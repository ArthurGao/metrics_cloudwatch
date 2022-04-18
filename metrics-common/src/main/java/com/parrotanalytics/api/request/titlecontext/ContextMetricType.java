package com.parrotanalytics.api.request.titlecontext;

public enum ContextMetricType
{
    ConsolidatedMetrics("consolidated_metrics"),

    PeakDexpercapRank("peak_dexpercap_rank"),

    DexpercapRank("dexpercap_rank"),

    // use PeakDexpercapRank instead
    @Deprecated
    PeakDexcRank("peak_dexc_rank"),

    Longevity("longevity"),

    Travelability("travelability"),

    DemandMultiplier("demand_multiplier"),

    MetricsGrading("metrics_grading"),

    Engagement("engagement"),

    Adoption("adoption"),

    Fandom("fandom"),

    Monetization("monetization"),

    Reach("reach"),

    Season("season"),

    @Deprecated
    SeasonRank("season_rank"),

    Franchisability("franchisability"),

    Momentum("momentum"),

    BreakdownChange("breakdown_change"),

    Affinity("affinity"),

    GTrend("gtrend"),

    De_Social("social"),

    De_Social_Chatter("chatter"),

    // most piracy rank
    De_Piracy("piracy"),

    // soical research rank
    De_Research("research"),

    // social video rank
    De_Video("video"),

    DatasourceMetrics("datasource_metrics"),

    // demand rank
    Dexpercapita("dexpercapita"),

    // demand growth over rolling period
    DemandChange("demand_change");

    private String value;

    private ContextMetricType(String value)
    {
        this.value = value;
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

    public static ContextMetricType fromValue(String value)
    {
        if (value != null)
        {
            for (ContextMetricType instance : ContextMetricType.values())
            {
                if (value.equalsIgnoreCase(instance.value()))
                {
                    return instance;
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.value;
    }
}
