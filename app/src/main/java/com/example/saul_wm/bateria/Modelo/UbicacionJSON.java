package com.example.saul_wm.bateria.Modelo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UbicacionJSON {

    @SerializedName("idDispositivo")
    @Expose
    private String idDispositivo;
    @SerializedName("localizacion")
    @Expose
    private LocalizacionJSON localizacion;

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public LocalizacionJSON getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(LocalizacionJSON localizacion) {
        this.localizacion = localizacion;
    }

}
