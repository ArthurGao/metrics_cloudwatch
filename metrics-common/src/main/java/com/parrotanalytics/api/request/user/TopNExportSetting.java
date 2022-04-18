package com.parrotanalytics.api.request.user;

import com.parrotanalytics.api.apidb_model.UserSetting;

/**
 * model class used for storing JSONified settings value for users 'topnexport'
 * settings in {@link UserSetting} table
 * 
 * @author Sanjeev Sharma
 *
 */
public class TopNExportSetting extends ExportSetting
{
    private int page = 1;

    private int size;

    /**
     * @return the page
     */
    public int getPage()
    {
        return page;
    }

    /**
     * @param page
     *            the page to set
     */
    public void setPage(int page)
    {
        this.page = page;
    }

    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(int size)
    {
        this.size = size;
    }

}
