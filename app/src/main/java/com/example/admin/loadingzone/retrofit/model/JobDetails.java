
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
    @SerializedName("material_description")
    @Expose
    private String materialDescription;
    @SerializedName("loading_date")
    @Expose
    private String loadingDate;
    @SerializedName("loading_time")
    @Expose
    private String loadingTime;

    @SerializedName("origin_destination_distance")
    @Expose
    private Double origin_destination_distance;

    @SerializedName("location_distance")
    @Expose
    private Integer locationDistance;
    @SerializedName("quotation_count")
    @Expose
    private String quotationCount;
    @SerializedName("date_requested")
    @Expose
    private String dateRequested;
    @SerializedName("date_requested_relative")
    @Expose
    private Object dateRequestedRelative;
    @SerializedName("job_status")
    @Expose
    private JobStatus jobStatus;
    @SerializedName("running_status_name")
    @Expose
    private Object runningStatusName;

    public String getPreferred_loading_time() {
        return preferred_loading_time;
    }

    public void setPreferred_loading_time(String preferred_loading_time) {
        this.preferred_loading_time = preferred_loading_time;
    }

    public String getPreferred_loading_date() {
        return preferred_loading_date;
    }

    public void setPreferred_loading_date(String preferred_loading_date) {
        this.preferred_loading_date = preferred_loading_date;
    }

    @SerializedName("preferred_loading_date")
    @Expose
    private String preferred_loading_time;
    @SerializedName("preferred_loading_time")
    @Expose
    private String preferred_loading_date;
    public Double getOrigin_destination_distance() {
        return origin_destination_distance;
    }

    public void setOrigin_destination_distance(Double origin_destination_distance) {
        this.origin_destination_distance = origin_destination_distance;
    }
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

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
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

    public Integer getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(Integer locationDistance) {
        this.locationDistance = locationDistance;
    }

    public String getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(String quotationCount) {
        this.quotationCount = quotationCount;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public Object getDateRequestedRelative() {
        return dateRequestedRelative;
    }

    public void setDateRequestedRelative(Object dateRequestedRelative) {
        this.dateRequestedRelative = dateRequestedRelative;
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
