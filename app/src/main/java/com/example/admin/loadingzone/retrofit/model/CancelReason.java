
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReason {

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
    private Object cancelComment;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

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

    public Object getCancelComment() {
        return cancelComment;
    }

    public void setCancelComment(Object cancelComment) {
        this.cancelComment = cancelComment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

}
