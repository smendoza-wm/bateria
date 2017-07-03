package com.example.saul_wm.bateria.Http;

import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpGet extends AsyncTask<String, Void, String>{

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;

    public HttpGet(AsyncResponse delegate){
        this.delegate = delegate;
    }




    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            System.out.println("Peticion a: " + url);
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    System.out.println(sb);
                    return sb.toString();
                default:
                    System.out.println("Estatus " + status);
            }

        } catch (MalformedURLException ex) {
            System.out.println("Estatus " + ex.toString());
        } catch (IOException ex) {
            System.out.println("Estatus " + ex.toString());
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    protected String doInBackground(String... params) {
        return getJSON(params[0], 30000);

    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        delegate.processFinish(result);
    }
}
