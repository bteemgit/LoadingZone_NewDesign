
package com.example.admin.loadingzone.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("maker_id")
    @Expose
    private Integer makerId;
    @SerializedName("maker_name")
    @Expose
    private String makerName;
    @SerializedName("model")
    @Expose
    private List<Model> model = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Integer getMakerId() {
        return makerId;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public List<Model> getModel() {
        return model;
    }

    public void setModel(List<Model> model) {
        this.model = model;
    }

}
