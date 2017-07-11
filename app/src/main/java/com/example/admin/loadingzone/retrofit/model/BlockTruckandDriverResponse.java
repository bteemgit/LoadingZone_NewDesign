
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockTruckandDriverResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_block_id")
    @Expose
    private Integer vehicleBlockId;
    @SerializedName("provider_vehicle_id")
    @Expose
    private String providerVehicleId;
    @SerializedName("job_driver_id")
    @Expose
    private String jobDriverId;
    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("expected_start_date")
    @Expose
    private String expectedStartDate;
    @SerializedName("job_starting_date")
    @Expose
    private Object jobStartingDate;
    @SerializedName("expected_end_date")
    @Expose
    private String expectedEndDate;
    @SerializedName("job_ended_date")
    @Expose
    private Object jobEndedDate;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getVehicleBlockId() {
        return vehicleBlockId;
    }

    public void setVehicleBlockId(Integer vehicleBlockId) {
        this.vehicleBlockId = vehicleBlockId;
    }

    public String getProviderVehicleId() {
        return providerVehicleId;
    }

    public void setProviderVehicleId(String providerVehicleId) {
        this.providerVehicleId = providerVehicleId;
    }

    public String getJobDriverId() {
        return jobDriverId;
    }

    public void setJobDriverId(String jobDriverId) {
        this.jobDriverId = jobDriverId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getExpectedStartDate() {
        return expectedStartDate;
    }

    public void setExpectedStartDate(String expectedStartDate) {
        this.expectedStartDate = expectedStartDate;
    }

    public Object getJobStartingDate() {
        return jobStartingDate;
    }

    public void setJobStartingDate(Object jobStartingDate) {
        this.jobStartingDate = jobStartingDate;
    }

    public String getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(String expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Object getJobEndedDate() {
        return jobEndedDate;
    }

    public void setJobEndedDate(Object jobEndedDate) {
        this.jobEndedDate = jobEndedDate;
    }

}
