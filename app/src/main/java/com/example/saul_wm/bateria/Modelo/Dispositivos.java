package com.example.saul_wm.bateria.Modelo;



public class Dispositivos {
    private String idDispositivo;
    private String ubicacion;

    public Dispositivos(String idDispositivo, String ubicacion) {
        this.idDispositivo = idDispositivo;
        this.ubicacion = ubicacion;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
