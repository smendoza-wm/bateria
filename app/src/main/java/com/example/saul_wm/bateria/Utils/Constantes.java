package com.example.saul_wm.bateria.Utils;


import com.example.saul_wm.bateria.Http.APIService;
import com.example.saul_wm.bateria.Http.RetrofitCliente;

public final class Constantes {

    /********************Constantes para la base de datos**************************************************************************/
    public static final int VERSION_BD = 3;
    public static final String NOMBRE_BD = "Base_Bateria";


    /*********************Constantes para los servicios de localizacion de Google**************************************************/
    public static final int REQUEST_LOCATION = 1;

    //Frecuencia con la que se pide una actualizacion [milisegundos]
    public static final long INTERVALO_LOCALIZACION = 5000;

    //Frecuencia mayor por si existe una aplicacion que este muestreando mas rapido [milisegundos]
    public static final long INTERVALO_LOCALIZACION_RAPIDA = INTERVALO_LOCALIZACION / 2;

    //Distancia mininima que debe existir para que se considere un cambio de ubicacion [en metros]
    public static final float MINIMA_DISTANCIA_ENTRE_ACTUALIZACIONES = 300;


    /********************Constantes para el acelerometro*****************************************************************************/
    public static final int NUMERO_MUESTRAS_EVENTO = 20;
    public static final double ACELERACION_MINIMA_EVENTO = 0.60; //   -->m/s^2






    public static final int REQUEST_CHECK_SETTINGS = 0x1;

    public static final String BASE_URL = "";

    public static APIService getAPIService() {
        return RetrofitCliente.getClient(BASE_URL).create(APIService.class);
    }



    public static final int BATERIA_MINIMA = 20; //Valores posibles: 0-100
    public static final long INTERVALO_MUESTRA_ACELERACION = 1000; //2 segundos
    public static final long INTERVALO_MUESTRA_BRUJULA = 1000;
    public static final long TIEMPO_MIN_ACTUALIZACION_GPS = 30000;
    public static final long DISTANCIA_MIN_ACTUALIZACION_GPS = 10;

}
