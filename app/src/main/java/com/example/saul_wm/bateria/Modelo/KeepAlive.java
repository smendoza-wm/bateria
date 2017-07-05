package com.example.saul_wm.bateria.Modelo;

public class KeepAlive {
    private static KeepAlive instancia = null;

    public static KeepAlive getInstance() {
        if (instancia == null) {
            // Sólo se accede a la zona sincronizada
            // cuando la instancia no está creada
            synchronized(KeepAlive.class) {
                // En la zona sincronizada sería necesario volver
                // a comprobar que no se ha creado la instancia
                if (instancia == null) {
                    instancia = new KeepAlive();
                }
            }
        }
        return instancia;
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

