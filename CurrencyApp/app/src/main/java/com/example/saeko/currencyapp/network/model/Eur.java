package com.example.saeko.currencyapp.network.model;

/**
 * Created by saeko on 2018-01-07.
 */

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eur extends CurrencyInfo  implements Serializable
{

    @SerializedName("gbp")
    @Expose
    private Double gbp;
    @SerializedName("usd")
    @Expose
    private Double usd;
    @SerializedName("jpy")
    @Expose
    private Double jpy;
    @SerializedName("aud")
    @Expose
    private Double aud;
    @SerializedName("inr")
    @Expose
    private Double inr;
    @SerializedName("cad")
    @Expose
    private Double cad;
    @SerializedName("zar")
    @Expose
    private Double zar;
    @SerializedName("nzd")
    @Expose
    private Double nzd;
    @SerializedName("cny")
    @Expose
    private Double cny;
    private final static long serialVersionUID = 2259544549167013295L;

    public Eur(String name, double value) {
        super(name, value);
    }

    public Double getGbp() {
        return gbp;
    }

    public void setGbp(Double gbp) {
        this.gbp = gbp;
    }

    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    public Double getJpy() {
        return jpy;
    }

    public void setJpy(Double jpy) {
        this.jpy = jpy;
    }

    public Double getAud() {
        return aud;
    }

    public void setAud(Double aud) {
        this.aud = aud;
    }

    public Double getInr() {
        return inr;
    }

    public void setInr(Double inr) {
        this.inr = inr;
    }

    public Double getCad() {
        return cad;
    }

    public void setCad(Double cad) {
        this.cad = cad;
    }

    public Double getZar() {
        return zar;
    }

    public void setZar(Double zar) {
        this.zar = zar;
    }

    public Double getNzd() {
        return nzd;
    }

    public void setNzd(Double nzd) {
        this.nzd = nzd;
    }

    public Double getCny() {
        return cny;
    }

    public void setCny(Double cny) {
        this.cny = cny;
    }

}