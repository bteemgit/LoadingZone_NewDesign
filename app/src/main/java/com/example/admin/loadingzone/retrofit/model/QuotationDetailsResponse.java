
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuotationDetailsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("quotation_id")
    @Expose
    private Integer quotationId;
    @SerializedName("job_details")
    @Expose
    private JobDetails jobDetails;
    @SerializedName("quotation_currency")
    @Expose
    private String quotationCurrency;
    @SerializedName("quotation_amount")
    @Expose
    private Integer quotationAmount;
    @SerializedName("date_submitted")
    @Expose
    private String dateSubmitted;
    @SerializedName("date_accepted")
    @Expose
    private String dateAccepted;
    @SerializedName("date_rejected")
    @Expose
    private Object dateRejected;
    @SerializedName("quotation_status")
    @Expose
    private String quotationStatus;
    @SerializedName("quotation_description")
    @Expose
    private String quotationDescription;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public JobDetails getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(JobDetails jobDetails) {
        this.jobDetails = jobDetails;
    }

    public String getQuotationCurrency() {
        return quotationCurrency;
    }

    public void setQuotationCurrency(String quotationCurrency) {
        this.quotationCurrency = quotationCurrency;
    }

    public Integer getQuotationAmount() {
        return quotationAmount;
    }

    public void setQuotationAmount(Integer quotationAmount) {
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

    public Object getDateRejected() {
        return dateRejected;
    }

    public void setDateRejected(Object dateRejected) {
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
