
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckYearResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_year")
    @Expose
    private List<VehicleYear> vehicleYear = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<VehicleYear> getVehicleYear() {
        return vehicleYear;
    }

    public void setVehicleYear(List<VehicleYear> vehicleYear) {
        this.vehicleYear = vehicleYear;
    }

}
