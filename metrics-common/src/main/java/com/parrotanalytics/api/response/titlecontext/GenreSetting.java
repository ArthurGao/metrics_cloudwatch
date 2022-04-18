package com.parrotanalytics.api.response.titlecontext;

public class GenreSetting
{
    private int min_value;

    private int max_value;

    private Object label_buckets;

    public GenreSetting()
    {

    }

    public GenreSetting(int min_value, int max_value, Object label_buckets)
    {
        this.min_value = min_value;
        this.max_value = max_value;
        this.label_buckets = label_buckets;
    }

    public int getMin_value()
    {
        return min_value;
    }

    public void setMin_value(int min_value)
    {
        this.min_value = min_value;
    }

    public int getMax_value()
    {
        return max_value;
    }

    public void setMax_value(int max_value)
    {
        this.max_value = max_value;
    }

    public Object getLabel_buckets()
    {
        return label_buckets;
    }

    public void setLabel_buckets(Object label_buckets)
    {
        this.label_buckets = label_buckets;
    }
}
