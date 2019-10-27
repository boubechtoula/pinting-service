package com.wsoft.printingservice.model.dto;

import java.util.Date;

/**
 *
 * @author b.walid
 */
public class JobDto {

    private int jobId;
    private String json;
    private String name;
    private Date startDate;
    private Date endDate;
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int JobId) {
        this.jobId = JobId;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

}
