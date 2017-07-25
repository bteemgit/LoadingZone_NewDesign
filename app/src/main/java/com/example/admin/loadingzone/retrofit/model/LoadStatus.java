
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoadStatus {

    @SerializedName("vehicle_status_id")
    @Expose
    private Integer vehicleStatusId;
    @SerializedName("vehicle_id")
    @Expose
    private Integer vehicleId;
    @SerializedName("running_status")
    @Expose
    private RunningStatus runningStatus;
    @SerializedName("location_latitude")
    @Expose
    private Object locationLatitude;
    @SerializedName("location_longitude")
    @Expose
    private Object locationLongitude;
    @SerializedName("location_name")
    @Expose
    private String locationName;
    @SerializedName("status_date")
    @Expose
    private String statusDate;
    @SerializedName("status_time")
    @Expose
    private String statusTime;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(String statusTime) {
        this.statusTime = statusTime;
    }

    public Integer getVehicleStatusId() {
        return vehicleStatusId;
    }

    public void setVehicleStatusId(Integer vehicleStatusId) {
        this.vehicleStatusId = vehicleStatusId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public RunningStatus getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(RunningStatus runningStatus) {
        this.runningStatus = runningStatus;
    }

    public Object getLocationLatitude() {
        return locationLatitude;
    }

    public void setLocationLatitude(Object locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public Object getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Object locationLongitude) {
        this.locationLongitude = locationLongitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
