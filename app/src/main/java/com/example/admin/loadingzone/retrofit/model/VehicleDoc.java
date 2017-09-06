
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDoc {

    @SerializedName("vehicle_document_id")
    @Expose
    private Integer vehicleDocumentId;
    @SerializedName("vehicle_id")
    @Expose
    private Integer vehicleId;
    @SerializedName("document_title")
    @Expose
    private String documentTitle;
    @SerializedName("document_file")
    @Expose
    private String documentFile;
    @SerializedName("document_date")
    @Expose
    private String documentDate;
    @SerializedName("document_time")
    @Expose
    private String documentTime;

    public Integer getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(Integer vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
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

    public String getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(String documentDate) {
        this.documentDate = documentDate;
    }

    public String getDocumentTime() {
        return documentTime;
    }

    public void setDocumentTime(String documentTime) {
        this.documentTime = documentTime;
    }

}
