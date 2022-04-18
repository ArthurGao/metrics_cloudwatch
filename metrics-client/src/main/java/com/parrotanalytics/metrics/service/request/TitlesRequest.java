package com.parrotanalytics.metrics.service.request;

import java.io.Serializable;


public class TitlesRequest implements Serializable
{

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
