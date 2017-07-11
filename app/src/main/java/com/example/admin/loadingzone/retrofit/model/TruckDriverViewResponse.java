
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckDriverViewResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("driver-list")
    @Expose
    private List<DriverList> driverList = null;
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

    public List<DriverList> getDriverList() {
        return driverList;
    }

    public void setDriverList(List<DriverList> driverList) {
        this.driverList = driverList;
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
