
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DriverTruck {

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
    private Integer avgRunningSpeed;
    @SerializedName("custom_name")
    @Expose
    private String customName;
    @SerializedName("registration_number")
    @Expose
    private Object registrationNumber;
    @SerializedName("chassis_number")
    @Expose
    private Object chassisNumber;
    @SerializedName("licence_number")
    @Expose
    private Object licenceNumber;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;

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

    public Integer getAvgRunningSpeed() {
        return avgRunningSpeed;
    }

    public void setAvgRunningSpeed(Integer avgRunningSpeed) {
        this.avgRunningSpeed = avgRunningSpeed;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public Object getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Object registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Object getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(Object chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public Object getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(Object licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

}
