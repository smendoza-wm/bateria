package com.example.saul_wm.bateria.Utils;


public final class Constantes {
    public static final int BATERIA_MINIMA = 20; //Valores posibles: 0-100
    public static final long INTERVALO_MUESTRA_ACELERACION = 2000; //2 segundos
    public static final long INTERVALO_MUESTRA_BRUJULA = 1000;
    public static final long TIEMPO_MIN_ACTUALIZACION_GPS = 30000;
    public static final long DISTANCIA_MIN_ACTUALIZACION_GPS = 10;

    public static final int VERSION_BD = 1;
    public static final String NOMBRE_BD = "Base_Bateria";


    float SHAKE_THRESHOLD = (float) 6.5E-8;
}
