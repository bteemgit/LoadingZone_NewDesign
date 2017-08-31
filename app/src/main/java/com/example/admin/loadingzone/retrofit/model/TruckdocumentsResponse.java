
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TruckdocumentsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("vehicle_document_id")
    @Expose
    private Integer vehicleDocumentId;
    @SerializedName("vehicle_id")
    @Expose
    private String vehicleId;
    @SerializedName("document_title")
    @Expose
    private String documentTitle;
    @SerializedName("document_file")
    @Expose
    private String documentFile;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(Integer vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getDocumentTitle() {
        return documentTitle;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public String getDocumentFile() {
        return documentFile;
    }

    public void setDocumentFile(String documentFile) {
        this.documentFile = documentFile;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
