package com.example.saul_wm.bateria.Modelo;

/**
 * Created by Saul-WM on 04/07/2017.
 */

public class KeepAlive {
    private static final KeepAlive ourInstance = new KeepAlive();

    public static KeepAlive getInstance() {
        return ourInstance;
    }

    private KeepAlive() {
    }

    private long ultimaHoraActualizacion;
    private long horaActual;

    public long getUltimaHoraActualizacion() {
        return ultimaHoraActualizacion;
    }

    public void setUltimaHoraActualizacion(long ultimaHoraActualizacion) {
        this.ultimaHoraActualizacion = ultimaHoraActualizacion;
    }

    public long getHoraActual() {
        return horaActual;
    }

    public void setHoraActual(long horaActual) {
        this.horaActual = horaActual;
    }

}
