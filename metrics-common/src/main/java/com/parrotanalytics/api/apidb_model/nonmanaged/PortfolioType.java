package com.parrotanalytics.api.apidb_model.nonmanaged;

public enum PortfolioType
{
    custom("custom", 0),

    platform("platform", 1),

    network("network", 2),

    genre_tags("genre_tags", 3);

    private String value;
    private Integer order;

    private PortfolioType(String value, Integer order)
    {
        this.value = value;
        this.order = order;
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

    public Integer order()
    {
        return this.order;
    }

    public static PortfolioType fromValue(String value)
    {
        if (value != null)
        {
            for (PortfolioType instance : PortfolioType.values())
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
