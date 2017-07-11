
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_list")
    @Expose
    private List<VehicleList> vehicleList = null;
    @SerializedName("_links")
    @Expose
    private Links links;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<VehicleList> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<VehicleList> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
