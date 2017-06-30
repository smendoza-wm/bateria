package com.example.saul_wm.bateria.Modelo;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LocalizacionJSON {

    private String latitud;

    private String longitud;

    private String fecha;

    private String hora;

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}




