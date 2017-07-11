
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakerResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_maker")
    @Expose
    private List<VehicleMaker> vehicleMaker = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<VehicleMaker> getVehicleMaker() {
        return vehicleMaker;
    }

    public void setVehicleMaker(List<VehicleMaker> vehicleMaker) {
        this.vehicleMaker = vehicleMaker;
    }

}
