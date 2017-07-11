
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckTypeResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("truck_type")
    @Expose
    private List<TruckType> truckType = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<TruckType> getTruckType() {
        return truckType;
    }

    public void setTruckType(List<TruckType> truckType) {
        this.truckType = truckType;
    }

}
