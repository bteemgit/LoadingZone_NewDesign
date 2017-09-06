
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckdocumentsViewResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_docs")
    @Expose
    private List<VehicleDoc> vehicleDocs = null;
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

    public List<VehicleDoc> getVehicleDocs() {
        return vehicleDocs;
    }

    public void setVehicleDocs(List<VehicleDoc> vehicleDocs) {
        this.vehicleDocs = vehicleDocs;
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
