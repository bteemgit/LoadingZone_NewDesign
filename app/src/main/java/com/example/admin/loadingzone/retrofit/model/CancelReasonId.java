
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelReasonId {

    @SerializedName("quotation_cancel_reason_id")
    @Expose
    private Integer quotationCancelReasonId;
    @SerializedName("quotation_cancel_reason")
    @Expose
    private String quotationCancelReason;

    public Integer getQuotationCancelReasonId() {
        return quotationCancelReasonId;
    }

    public void setQuotationCancelReasonId(Integer quotationCancelReasonId) {
        this.quotationCancelReasonId = quotationCancelReasonId;
    }

    public String getQuotationCancelReason() {
        return quotationCancelReason;
    }

    public void setQuotationCancelReason(String quotationCancelReason) {
        this.quotationCancelReason = quotationCancelReason;
    }

}
