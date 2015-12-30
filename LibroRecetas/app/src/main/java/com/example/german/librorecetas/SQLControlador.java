package com.example.german.librorecetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by German on 30/12/2015.
 */
public class SQLControlador {
    private DbHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;


    private ContentValues valores(Receta r) {
        ContentValues res = new ContentValues();
        res.put(DbHelper.CN_NombreR,r.getNombre());
        res.put(DbHelper.CN_Preparacion, r.getPreparacion());
        res.put(DbHelper.CN_Path, r.getPath());
        res.put(DbHelper.CN_Tipo, r.getTipo());
        return res;
    }

    public SQLControlador(Context c) {
        ourcontext = c;
    }

    public SQLControlador abrirBaseDatos() throws SQLException {
        dbhelper = new DbHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void cerrar() {
        dbhelper.close();
    }

    //Receta: Inerta, eliminar y modificar
    //====================================
    public void insertarDatos(Receta r) {
        database.insert(DbHelper.TABLA_RECETA, null, valores(r));
    }

    public Cursor leerDatos(String nombreR) {
        String query = "SELECT " + dbhelper.CN_idR + "," + dbhelper.CN_NombreR + " FROM " + dbhelper.TABLA_RECETA + " WHERE '" + nombreR + "' = " + dbhelper.CN_NombreR;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        return c;
    }
}
