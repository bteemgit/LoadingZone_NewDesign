
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobLoaddetailsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("assigned_vehicle")
    @Expose
    private AssignedVehicle assignedVehicle;
    @SerializedName("assigned_driver")
    @Expose
    private AssignedDriver assignedDriver;
    @SerializedName("load_status")
    @Expose
    private LoadStatus loadStatus;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public AssignedVehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(AssignedVehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public AssignedDriver getAssignedDriver() {
        return assignedDriver;
    }

    public void setAssignedDriver(AssignedDriver assignedDriver) {
        this.assignedDriver = assignedDriver;
    }

    public LoadStatus getLoadStatus() {
        return loadStatus;
    }

    public void setLoadStatus(LoadStatus loadStatus) {
        this.loadStatus = loadStatus;
    }

}
