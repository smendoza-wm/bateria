package com.example.saul_wm.bateria.Bateria;

/*###########################################################################################
  #                                                                                         #
  #  @author:      Saul Mendoza <saul.mendoza@webmaps.com.mx>                               #
  #  @created:     30/06/2017                                                               #
  #  @description: Clase padre que contiene los campos comunes para monitorear la bateria   #
  #  @copyright:   Copyright (c) 2017, WebMaps                                              #
  #                                                                                         #
  ###########################################################################################*/

import android.content.Context;

import com.example.saul_wm.bateria.Utils.Constantes;

public class Bateria {

    //Constantes para ver el tipo de carga
    public static final String CARGA_USB = "USB";
    public static final String CARGA_AC = "AC";

    protected int nivel = 0;  //nivel (porcentaje) de la bateria 0-100
    protected boolean bateriaBaja = false; //bandera para saber si el nivel esta por debajo del minimo establecido
    protected boolean cargando = false; //bandera para saber si el dispositivo esta en carga
    protected String fuenteCarga = "NO_CARGA"; //tipo de fuente con el que se carga el dispositivo [AC/USB/NO_CARGA]

    //Context
    protected Context context;

    //Constuctor
    public Bateria(Context context){
        this.context = context;
    }

    //Getters
    public int getNivel() {
        return nivel;
    }

    public boolean isBateriaBaja() {
        return bateriaBaja;
    }

    public boolean isCargando() {
        return cargando;
    }

    public String getFuenteCarga() {
        return fuenteCarga;
    }

    //Setters

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setBateriaBaja(boolean bateriaBaja) {
        this.bateriaBaja = bateriaBaja;
    }

    public void setCargando(boolean cargando) {
        this.cargando = cargando;
    }

    public void setFuenteCarga(String fuenteCarga) {
        this.fuenteCarga = fuenteCarga;
    }
}
