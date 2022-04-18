package com.parrotanalytics.api.apidb_model.nonmanaged;

public enum AccessibleTier
{
    Monitor("Monitor"), Enterprise("Enterprise");

    private String value;

    private AccessibleTier(String value)
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

    public static AccessibleTier fromValue(String value)
    {
        if (value != null)
        {
            for (AccessibleTier instance : AccessibleTier.values())
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
