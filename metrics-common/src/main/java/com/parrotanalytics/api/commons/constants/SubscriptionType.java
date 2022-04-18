package com.parrotanalytics.api.commons.constants;

/**
 * Enum for subscription type used in subscriptions table
 *
 * @author Sanjeev Sharma
 */
public enum SubscriptionType
{

    TITLE("tvtitle"),

    MARKET("market"),

    TALENT("talent"),

    MOVIE("movie"),

    MODULE("module"),

    DEFAULT("default"),

    HOMEMARKET("homeMarket"),

    SUMMARY("summary"),

    USER("user"),

    PROJECT("project"),

    ALL("all");

    private String value;

    private SubscriptionType(String value)
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

    public static SubscriptionType fromValue(String value)
    {
        if (value != null)
        {
            for (SubscriptionType instance : SubscriptionType.values())
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
