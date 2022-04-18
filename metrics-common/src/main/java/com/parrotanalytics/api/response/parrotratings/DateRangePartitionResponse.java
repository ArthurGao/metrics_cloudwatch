package com.parrotanalytics.api.response.parrotratings;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.parrotanalytics.api.apidb_model.nonmanaged.DateRangePartition;
import com.parrotanalytics.apicore.model.response.APIResponse;

/**
 * 
 * @author Minh Vu
 *
 */
@JsonPropertyOrder(
{
        "pageinfo", "date_partitions"
})
@JsonInclude(Include.NON_NULL)
public class DateRangePartitionResponse extends APIResponse
{
    /**
     * 
     */
    private static final long serialVersionUID = 2526660028698229565L;
    /**
     * 
     */

    @JsonProperty("date_partitions")
    private List<DateRangePartition> datePartitions = new ArrayList<DateRangePartition>();

    public List<DateRangePartition> getDatePartitions()
    {
        return datePartitions;
    }

    public void setDatePartitions(List<DateRangePartition> datePartitions)
    {
        this.datePartitions = datePartitions;
    }

}
