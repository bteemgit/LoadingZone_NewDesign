
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JobList {

    @SerializedName("job_id")
    @Expose
    private String jobId;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("from_location")
    @Expose
    private FromLocation fromLocation;
    @SerializedName("to_location")
    @Expose
    private ToLocation toLocation;
    @SerializedName("material")
    @Expose
    private Material material;
    @SerializedName("material_description")
    @Expose
    private String materialDescription;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("date_of_loading")
    @Expose
    private String dateOfLoading;
    @SerializedName("payment_type")
    @Expose
    private PaymentType paymentType;
    @SerializedName("truck_type")
    @Expose
    private TruckType truckType;
    @SerializedName("truck_size")
    @Expose
    private TruckSize truckSize;
    @SerializedName("currency")
    @Expose
    private Currency currency;
    @SerializedName("location_distance")
    @Expose
    private Double locationDistance;
    @SerializedName("date_requested")
    @Expose
    private String dateRequested;
    @SerializedName("date_requested_relative")
    @Expose
    private String dateRequestedRelative;
    @SerializedName("budget")
    @Expose
    private String budget;
    @SerializedName("quotation_count")
    @Expose
    private String quotationCount;
    @SerializedName("has_active_quotations")
    @Expose
    private String hasActiveQuotations;
    @SerializedName("job_status")
    @Expose
    private JobStatus jobStatus;

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    @SerializedName("job_title")
    @Expose
    private String job_title;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public FromLocation getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(FromLocation fromLocation) {
        this.fromLocation = fromLocation;
    }

    public ToLocation getToLocation() {
        return toLocation;
    }

    public void setToLocation(ToLocation toLocation) {
        this.toLocation = toLocation;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateOfLoading() {
        return dateOfLoading;
    }

    public void setDateOfLoading(String dateOfLoading) {
        this.dateOfLoading = dateOfLoading;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public TruckType getTruckType() {
        return truckType;
    }

    public void setTruckType(TruckType truckType) {
        this.truckType = truckType;
    }

    public TruckSize getTruckSize() {
        return truckSize;
    }

    public void setTruckSize(TruckSize truckSize) {
        this.truckSize = truckSize;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getLocationDistance() {
        return locationDistance;
    }

    public void setLocationDistance(Double locationDistance) {
        this.locationDistance = locationDistance;
    }

    public String getDateRequested() {
        return dateRequested;
    }

    public void setDateRequested(String dateRequested) {
        this.dateRequested = dateRequested;
    }

    public String getDateRequestedRelative() {
        return dateRequestedRelative;
    }

    public void setDateRequestedRelative(String dateRequestedRelative) {
        this.dateRequestedRelative = dateRequestedRelative;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getQuotationCount() {
        return quotationCount;
    }

    public void setQuotationCount(String quotationCount) {
        this.quotationCount = quotationCount;
    }

    public String getHasActiveQuotations() {
        return hasActiveQuotations;
    }

    public void setHasActiveQuotations(String hasActiveQuotations) {
        this.hasActiveQuotations = hasActiveQuotations;
    }

    public JobStatus getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(JobStatus jobStatus) {
        this.jobStatus = jobStatus;
    }

}
