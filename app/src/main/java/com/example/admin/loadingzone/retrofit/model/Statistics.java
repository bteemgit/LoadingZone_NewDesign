
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("vehicles")
    @Expose
    private String vehicles;
    @SerializedName("pending_jobs")
    @Expose
    private Integer pendingJobs;
    @SerializedName("completed_jobs")
    @Expose
    private Integer completedJobs;

    public String getVehicles() {
        return vehicles;
    }

    public void setVehicles(String vehicles) {
        this.vehicles = vehicles;
    }

    public Integer getPendingJobs() {
        return pendingJobs;
    }

    public void setPendingJobs(Integer pendingJobs) {
        this.pendingJobs = pendingJobs;
    }

    public Integer getCompletedJobs() {
        return completedJobs;
    }

    public void setCompletedJobs(Integer completedJobs) {
        this.completedJobs = completedJobs;
    }

}
