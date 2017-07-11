
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VehicleDetailsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("provider_vehicle_id")
    @Expose
    private Integer providerVehicleId;
    @SerializedName("vehicle")
    @Expose
    private Vehicle vehicle;
    @SerializedName("driver")
    @Expose
    private Driver driver;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("insurance_exp_date")
    @Expose
    private String insuranceExpDate;
    @SerializedName("avg_running_speed")
    @Expose
    private String avgRunningSpeed;
    @SerializedName("custom_name")

    @Expose
    private String customName;

    public String getDriver_exists() {
        return driver_exists;
    }

    public void setDriver_exists(String driver_exists) {
        this.driver_exists = driver_exists;
    }

    @SerializedName("driver_exists")
    @Expose
    private String driver_exists;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

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

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
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
