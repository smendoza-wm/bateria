package com.example.saul_wm.bateria.Http;

import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HttpPost extends AsyncTask<String, Void, Void> {

    private Exception exception;

    protected Void doInBackground(String... datos) {
        try {
            URL url = new URL(datos[0]);
            String latitud = datos[1];
            String longitud = datos[2];
            enviaDatos(url, latitud, longitud);

        } catch (Exception e) {
            this.exception = e;
        }
        return null;
    }

    protected void onPostExecute() {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

    private void enviaDatos(URL url, String latitud, String longitud) {
        // Obtener la conexión
        HttpURLConnection con = null;
        Date date = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String hora = hourFormat.format(date);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = dateFormat.format(date);

        try {
            // Construir los datos a enviar
            String data = "posicion=" + ",latitud;" + URLEncoder.encode(latitud+"","UTF-8")
                    + ",longitud;" + URLEncoder.encode(longitud+"", "UTF-8")
                    + ",fecha;" + URLEncoder.encode(fecha+"", "UTF-8")
                    + ",hora;" + URLEncoder.encode(hora+"", "UTF-8");

            con = (HttpURLConnection)url.openConnection();

            // Activar método POST
            con.setDoOutput(true);

            // Tamaño previamente conocido
            con.setFixedLengthStreamingMode(data.getBytes().length);

            // Establecer application/x-www-form-urlencoded debido a la simplicidad de los datos
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            OutputStream out = new BufferedOutputStream(con.getOutputStream());

            out.write(data.getBytes());
            out.flush();
            out.close();
            System.out.println("Termine de enviar la peticion post");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(con!=null)
                con.disconnect();
        }
    }

}