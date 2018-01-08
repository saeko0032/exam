package com.example.saeko.currencyapp.network.response;

import com.example.saeko.currencyapp.network.model.Aud;
import com.example.saeko.currencyapp.network.model.Cad;
import com.example.saeko.currencyapp.network.model.Cny;
import com.example.saeko.currencyapp.network.model.Eur;
import com.example.saeko.currencyapp.network.model.Gbp;
import com.example.saeko.currencyapp.network.model.Inr;
import com.example.saeko.currencyapp.network.model.Jpy;
import com.example.saeko.currencyapp.network.model.Nzd;
import com.example.saeko.currencyapp.network.model.Usd;
import com.example.saeko.currencyapp.network.model.Zar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by saeko on 2018-01-07.
 */

public class MyCurrencyResponse implements Serializable {

    @SerializedName("gbp")
    @Expose
    private Gbp gbp;

    @SerializedName("usd")
    @Expose
    private Usd usd;

    @SerializedName("eur")
    @Expose
    private Eur eur;

    @SerializedName("jpy")
    @Expose
    private Jpy jpy;

    @SerializedName("aud")
    @Expose
    private Aud aud;

    @SerializedName("inr")
    @Expose
    private Inr inr;

    @SerializedName("cad")
    @Expose
    private Cad cad;

    @SerializedName("zar")
    @Expose
    private Zar zar;

    @SerializedName("nzd")
    @Expose
    private Nzd nzd;

    @SerializedName("cny")
    @Expose
    private Cny cny;

    public Gbp getGbp() {
        return gbp;
    }

    public void setGbp(Gbp gbp) {
        this.gbp = gbp;
    }

    public Usd getUsd() {
        return usd;
    }

    public void setUsd(Usd usd) {
        this.usd = usd;
    }

    public Eur getEur() {
        return eur;
    }

    public void setEur(Eur eur) {
        this.eur = eur;
    }

    public Jpy getJpy() {
        return jpy;
    }

    public void setJpy(Jpy jpy) {
        this.jpy = jpy;
    }

    public Aud getAud() {
        return aud;
    }

    public void setAud(Aud aud) {
        this.aud = aud;
    }

    public Inr getInr() {
        return inr;
    }

    public void setInr(Inr inr) {
        this.inr = inr;
    }

    public Cad getCad() {
        return cad;
    }

    public void setCad(Cad cad) {
        this.cad = cad;
    }

    public Zar getZar() {
        return zar;
    }

    public void setZar(Zar zar) {
        this.zar = zar;
    }

    public Nzd getNzd() {
        return nzd;
    }

    public void setNzd(Nzd nzd) {
        this.nzd = nzd;
    }

    public Cny getCny() {
        return cny;
    }

    public void setCny(Cny cny) {
        this.cny = cny;
    }

}
