
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReason {


    @SerializedName("cancel_reason_details")
    @Expose
    private Reason reason;

    @SerializedName("quotation_cancel_reason")
    @Expose
    private String quotation_cancel_reason;
    @SerializedName("quotation_cancel_id")
    @Expose
    private Integer quotationCancelId;
    @SerializedName("quotation_id")
    @Expose
    private Integer quotationId;
    @SerializedName("cancel_reason_id")
    @Expose
    private CancelReasonId cancelReasonId;
    @SerializedName("cancel_comment")
    @Expose
    private String cancelComment;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
    public Integer getQuotationCancelId() {
        return quotationCancelId;
    }

    public void setQuotationCancelId(Integer quotationCancelId) {
        this.quotationCancelId = quotationCancelId;
    }

    public Integer getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Integer quotationId) {
        this.quotationId = quotationId;
    }

    public CancelReasonId getCancelReasonId() {
        return cancelReasonId;
    }

    public void setCancelReasonId(CancelReasonId cancelReasonId) {
        this.cancelReasonId = cancelReasonId;
    }
    public String getQuotation_cancel_reason() {
        return quotation_cancel_reason;
    }

    public void setQuotation_cancel_reason(String  quotation_cancel_reason) {
        this.quotation_cancel_reason = quotation_cancel_reason;
    }
    public String getCancelComment() {
        return cancelComment;
    }

    public void setCancelComment(String cancelComment) {
        this.cancelComment = cancelComment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
