package com.example.saul_wm.bateria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.saul_wm.bateria.Http.HttpGet;
import com.example.saul_wm.bateria.Modelo.LocalizacionJSON;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tv = (TextView)findViewById(R.id.tv_result);
        btn = (Button) findViewById(R.id.btn_get);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_get:
                System.out.println("Empezando a realizar peticion get");
                HttpGet peticionGet = new HttpGet(new HttpGet.AsyncResponse() {
                    @Override
                    public void processFinish(String output) {
                        tv.setText(output);
                        System.out.println("Resultado = " + output);


                        //LocalizacionJSON msg = new Gson().fromJson(output, LocalizacionJSON.class);
                        //System.out.println(msg.toString());
                        //System.out.println(msg);

                    }
                });
                peticionGet.execute("");
                break;
        }
    }
}
