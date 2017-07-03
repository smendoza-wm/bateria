package com.example.saul_wm.bateria;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saul_wm.bateria.Http.HttpGet;
import com.example.saul_wm.bateria.Modelo.Dispositivos;
import com.example.saul_wm.bateria.Modelo.LocalizacionJSON;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    Button btn;
    int posicion;
    Dispositivos dispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        /*tv = (TextView)findViewById(R.id.tv_result);
        btn = (Button) findViewById(R.id.btn_get);
        btn.setOnClickListener(this);*/

        ListView lv = (ListView) findViewById(R.id.lv_dispositivos); //Se relaciona el ListView donde van a estar los botones
        ArrayList<Dispositivos> list = new ArrayList<>();
        Dispositivos dis = new Dispositivos("dispositivo", "localizacion");
        Dispositivos dis2 = new Dispositivos("dis", "loc");

        list.add(dis);
        list.add(dis2);

        DispositivosAdapter adapter = new DispositivosAdapter(this, list); //Se inicializa el adaptador
        if (lv != null)
            lv.setAdapter(adapter);//Se rellena el listView con los botones de los niños
    }

    @Override
    public void onClick(View v) {
      /*  switch(v.getId()){
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
        }*/    }

    public class DispositivosAdapter extends ArrayAdapter<Dispositivos> {
        ViewHolder holder;
        Dispositivos dispositivos;
        List<Dispositivos> objects;
        Context context;


        public DispositivosAdapter(Context context, List<Dispositivos> objects) {
            super(context, 0, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // Obtener inflater.
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // ¿Ya se infló este view?
            if (null == convertView) {
                //Si no existe, entonces inflarlo con lista_ninos_botones.xml
               // if (actividadADesplegar == BOTONES_INFORMACION) {
                    convertView = inflater.inflate(
                            R.layout.lista_dispositivos,
                            parent,
                            false);


                    holder = new ViewHolder();
                    holder.dispositivo = (TextView) convertView.findViewById(R.id.tv_idDispositivo);
                    holder.localizacion = (TextView) convertView.findViewById(R.id.tv_localizacion);
                    holder.actualizar = (Button) convertView.findViewById(R.id.btn_actualizar);
                    //holder.botonEditar = (ImageView) convertView.findViewById(R.id.iv_editar_nino);
               /* } else {
                    convertView = inflater.inflate(R.layout.lista_ninos_botones, parent, false);
                    holder = new ViewHolder();
                    holder.avatar = (ImageButton) convertView.findViewById(R.id.iv_avatar);
                    holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                }*/
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            // Lead actual.
            posicion = position;
            dispositivo = getItem(position);

            // Setup.
            holder.dispositivo.setText("Dispositivo pos: " + position);
            holder.localizacion.setText("Localizacion ");
            holder.actualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Presiono boton " + position, Toast.LENGTH_LONG).show();
                }
            });

            return convertView;
        }

        public class ViewHolder {
            TextView dispositivo;
            TextView localizacion;
            Button actualizar;
        }

    }
}
