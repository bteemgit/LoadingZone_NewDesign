
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverListResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("driver-list")
    @Expose
    private List<DriverList> driverList = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<DriverList> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverList> driverList) {
        this.driverList = driverList;
    }

}
