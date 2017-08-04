
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelJobReasonResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("quotation_cancel_reason")
    @Expose
    private List<QuotationCancelReason> quotationCancelReason = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<QuotationCancelReason> getQuotationCancelReason() {
        return quotationCancelReason;
    }

    public void setQuotationCancelReason(List<QuotationCancelReason> quotationCancelReason) {
        this.quotationCancelReason = quotationCancelReason;
    }

}
