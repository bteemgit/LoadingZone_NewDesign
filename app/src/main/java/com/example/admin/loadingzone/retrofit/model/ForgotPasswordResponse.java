
package com.example.admin.loadingzone.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("errors")
    @Expose
    private Errors errors;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

}
