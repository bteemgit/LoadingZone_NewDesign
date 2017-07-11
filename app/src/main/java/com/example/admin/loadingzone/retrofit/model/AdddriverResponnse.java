
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdddriverResponnse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("driver_vehicle_id")
    @Expose
    private Integer driverVehicleId;
    @SerializedName("driver_id")
    @Expose
    private String driverId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getDriverVehicleId() {
        return driverVehicleId;
    }

    public void setDriverVehicleId(Integer driverVehicleId) {
        this.driverVehicleId = driverVehicleId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

}
