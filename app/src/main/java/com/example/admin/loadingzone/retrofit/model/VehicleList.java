
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleList {

    @SerializedName("provider_vehicle_id")
    @Expose
    private Integer providerVehicleId;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("insurance_exp_date")
    @Expose
    private String insuranceExpDate;
    @SerializedName("avg_running_speed")
    @Expose
    private String avgRunningSpeed;
    @SerializedName("custom_name")
    @Expose
    private String customName;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @SerializedName("driver")
    @Expose
    private String driver;

    public Integer getProviderVehicleId() {
        return providerVehicleId;
    }

    public void setProviderVehicleId(Integer providerVehicleId) {
        this.providerVehicleId = providerVehicleId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getInsuranceExpDate() {
        return insuranceExpDate;
    }

    public void setInsuranceExpDate(String insuranceExpDate) {
        this.insuranceExpDate = insuranceExpDate;
    }

    public String getAvgRunningSpeed() {
        return avgRunningSpeed;
    }

    public void setAvgRunningSpeed(String avgRunningSpeed) {
        this.avgRunningSpeed = avgRunningSpeed;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

}
