package com.example.saul_wm.bateria.BaseDatos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class BaseDatos extends SQLiteOpenHelper{

    /*Script para crear la base de datos*/
    private static final String dat_bateria = "CREATE TABLE dat_bateria("+
                                            "n_bateria_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "c_bateria_fecha TEXT," +
                                            "n_bateria_porcentaje INTEGER," +
                                            "n_bateria_cargando INTEGER, " +   /*Los booleanos se almacenan como integers*/
                                            "t_bateria_fuenteCarga TEXT )";
    /*Fin script para crear la base de datos*/



    public BaseDatos(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(dat_bateria);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(dat_bateria);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys = ON");
            }
        }
    }
}
