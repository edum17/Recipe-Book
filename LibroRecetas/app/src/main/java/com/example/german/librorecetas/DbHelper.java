package com.example.german.librorecetas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by German on 26/12/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_Name = "cjtRecetas.sqlite";
    private static final int DB_SCHEME_VERSION = 1; //Version actual de la DB

    public DbHelper(Context context) {
        super(context, DB_Name, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREA_TABLA_INGREDIENTE);
        db.execSQL(DataBaseManager.CREA_TABLA_RECETA);
        db.execSQL(DataBaseManager.CREA_TABLA_SUSTITUTO);
    }

    //Actualizamos la base de datos, incluida la SCHEME_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
