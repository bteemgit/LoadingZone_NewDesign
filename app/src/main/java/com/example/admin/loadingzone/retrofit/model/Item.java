
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("quotation_id")
    @Expose
    private Integer quotationId;
    @SerializedName("job_id")
    @Expose
    private Integer jobId;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("service_provider")
    @Expose
    private ServiceProvider serviceProvider;
    @SerializedName("quotation_currency")
    @Expose
    private String quotationCurrency;
    @SerializedName("quotation_amount")
    @Expose
    private String quotationAmount;
    @SerializedName("date_submitted")
    @Expose
    private String dateSubmitted;
    @SerializedName("date_accepted")
    @Expose
    private String dateAccepted;
    @SerializedName("date_rejected")
    @Expose
    private String dateRejected;
    @SerializedName("quotation_status")
    @Expose
    private String quotationStatus;
    @SerializedName("quotation_description")
    @Expose
    private String quotationDescription;

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getQuotationCurrency() {
        return quotationCurrency;
    }

    public void setQuotationCurrency(String quotationCurrency) {
        this.quotationCurrency = quotationCurrency;
    }

    public String getQuotationAmount() {
        return quotationAmount;
    }

    public void setQuotationAmount(String quotationAmount) {
        this.quotationAmount = quotationAmount;
    }

    public String getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(String dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }

    public String getDateAccepted() {
        return dateAccepted;
    }

    public void setDateAccepted(String dateAccepted) {
        this.dateAccepted = dateAccepted;
    }

    public String getDateRejected() {
        return dateRejected;
    }

    public void setDateRejected(String dateRejected) {
        this.dateRejected = dateRejected;
    }

    public String getQuotationStatus() {
        return quotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        this.quotationStatus = quotationStatus;
    }

    public String getQuotationDescription() {
        return quotationDescription;
    }

    public void setQuotationDescription(String quotationDescription) {
        this.quotationDescription = quotationDescription;
    }

}
