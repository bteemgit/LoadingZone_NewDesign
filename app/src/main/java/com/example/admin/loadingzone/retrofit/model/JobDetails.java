
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobDetails {

    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("from_location_name")
    @Expose
    private String fromLocationName;
    @SerializedName("to_location_name")
    @Expose
    private String toLocationName;
    @SerializedName("material_name")
    @Expose
    private String materialName;
    @SerializedName("loading_date")
    @Expose
    private String loadingDate;
    @SerializedName("loading_time")
    @Expose
    private String loadingTime;
    @SerializedName("job_status")
    @Expose
    private JobStatus jobStatus;
    @SerializedName("running_status_name")
    @Expose
    private Object runningStatusName;
    @SerializedName("date_requested_relative")
    @Expose
    private String dateRequestedRelative;

    @SerializedName("date_requested")
    @Expose
    private String dateRequested;
    @SerializedName("quotation_count")
    @Expose
    private String quotationCount;

    public String getDateRequestedRelative() {
        return dateRequestedRelative;
    }

    public void setDateRequestedRelative(String dateRequestedRelative) {
        this.dateRequestedRelative = dateRequestedRelative;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(String quotationCount) {
        this.quotationCount = quotationCount;
    }

    public String getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(String locationDistance) {
        this.locationDistance = locationDistance;
    }

    @SerializedName("location_distance")
    @Expose
    private String locationDistance;
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getFromLocationName() {
        return fromLocationName;
    }

    public void setFromLocationName(String fromLocationName) {
        this.fromLocationName = fromLocationName;
    }

    public String getToLocationName() {
        return toLocationName;
    }

    public void setToLocationName(String toLocationName) {
        this.toLocationName = toLocationName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }

    public String getLoadingTime() {
        return loadingTime;
    }

    public void setLoadingTime(String loadingTime) {
        this.loadingTime = loadingTime;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

    public Object getRunningStatusName() {
        return runningStatusName;
    }

    public void setRunningStatusName(Object runningStatusName) {
        this.runningStatusName = runningStatusName;
    }

}
