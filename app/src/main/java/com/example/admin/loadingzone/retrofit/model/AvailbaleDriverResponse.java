
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailbaleDriverResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("available_drivers")
    @Expose
    private List<AvailableDriver> availableDrivers = null;
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

    public List<AvailableDriver> getAvailableDrivers() {
        return availableDrivers;
    }

    public void setAvailableDrivers(List<AvailableDriver> availableDrivers) {
        this.availableDrivers = availableDrivers;
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
