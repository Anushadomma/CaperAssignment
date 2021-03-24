package com.example.caperassignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CaperData {
    @SerializedName("qrUrl")
    @Expose
    private String qrUrl;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("id")
    @Expose
    private Integer id;

    public CaperData() {
    }

    public CaperData(String qrUrl,String name, Integer price,Integer id) {
        super();
        this.qrUrl = qrUrl;
        this.name = name;
        this.price = price;
        this.id = id;

    }
    public String getqrUrl() {
        return qrUrl;
    }

    public void setqrUrl(String code) {
        this.qrUrl = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
