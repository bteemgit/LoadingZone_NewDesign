
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

    public String getJob_code() {
        return job_code;
    }

    public void setJob_code(String job_code) {
        this.job_code = job_code;
    }

    @SerializedName("job_code")
    @Expose
    private String job_code;

    public String getQuotation_code() {
        return quotation_code;
    }

    public void setQuotation_code(String quotation_code) {
        this.quotation_code = quotation_code;
    }

    @SerializedName("quotation_code")
    @Expose
    private String quotation_code;

    @SerializedName("job_title")
    @Expose
    private String jobTitle;


    public String getLoadingDate() {
        return loadingDate;
    }

    public void setLoadingDate(String loadingDate) {
        this.loadingDate = loadingDate;
    }


    @SerializedName("loading_date")
    @Expose
    private String loadingDate;


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
    @SerializedName("customer")
    @Expose
    private Customer_ customer;
    @SerializedName("loading_time")
    @Expose
    private String loading_time;



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

    public String getLoading_time() {
        return loading_time;
    }

    public void setLoading_time(String loading_time) {
        this.loading_time = loading_time;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public Customer_ getCustomer() {
        return customer;
    }

    public void setCustomer(Customer_ customer) {
        this.customer = customer;
    }


}
