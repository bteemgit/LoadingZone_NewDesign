package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 10/25/2017.
 */

public class Reason {
    @SerializedName("quotation_cancel_reason")
    @Expose
    private String quotation_cancel_reason;

    public String getQuotation_cancel_reason() {
        return quotation_cancel_reason;
    }

    public void setQuotation_cancel_reason(String quotation_cancel_reason) {
        this.quotation_cancel_reason = quotation_cancel_reason;
    }
}
