package com.example.german.librorecetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by German on 30/12/2015.
 */
public class SQLControlador {
    private DbHelper dbhelper;
    private Context ourcontext;
    private SQLiteDatabase database;

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

    //Receta: Inerta, listar, eliminar y modificar
    //====================================
    private ContentValues valoresReceta(Receta r) {
        ContentValues res = new ContentValues();
        res.put(DbHelper.CN_NombreR,r.getNombre());
        res.put(DbHelper.CN_Preparacion, r.getPreparacion());
        res.put(DbHelper.CN_Path, r.getPath());
        res.put(DbHelper.CN_Tipo, r.getTipo());
        return res;
    }

    public void insertarDatos(Receta r) {
        database.insert(DbHelper.TABLA_RECETA, null, valoresReceta(r));
    }

    public ArrayList<String> leerReceta(int idR) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT " + dbhelper.CN_NombreR + "," + dbhelper.CN_Preparacion + "," + dbhelper.CN_Path + "," + dbhelper.CN_Tipo + " FROM " + dbhelper.TABLA_RECETA + " WHERE " + dbhelper.CN_idR + "=" + Integer.toString(idR);
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_nombre")));
            res.add(c.getString(c.getColumnIndex("_preparacion")));
            res.add(c.getString(c.getColumnIndex("_path")));
            res.add(c.getString(c.getColumnIndex("_tipo")));
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Item> listarRecetasIngrediente(String nombreI) {
        //String query = "SELECT " + dbhelper.CN_idR + "," + dbhelper.CN_NombreR + " FROM " + dbhelper.TABLA_RECETA + " WHERE EXISTS (SELECT * FROM " + dbhelper.TABLA_INGREDIENTE + " WHERE " + dbhelper.CN_idR + "=" + dbhelper.CN_idRI + " and " + dbhelper.CN_NombreI + "='" + nombreI + "')";
        String query = "SELECT r._id,r._nombre,r._path FROM Receta r WHERE EXISTS (SELECT * FROM Ingrediente i WHERE i._idR=r._id and i._nombre='" + nombreI + "')";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Item> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Item i = new Item();
            i.setIdR(c.getInt(c.getColumnIndex("_id")));
            i.setNombreR(c.getString(c.getColumnIndex("_nombre")));
            i.setPathR(c.getString(c.getColumnIndex("_path")));
            res.add(i);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Item> listarRecetasNoIngrediente(String nombreI) {
        String query = "SELECT r._id,r._nombre,r._path FROM Receta r WHERE NOT EXISTS (SELECT * FROM Ingrediente i WHERE i._idR=r._id and i._nombre='" + nombreI + "')";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Item> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Item i = new Item();
            i.setIdR(c.getInt(c.getColumnIndex("_id")));
            i.setNombreR(c.getString(c.getColumnIndex("_nombre")));
            i.setPathR(c.getString(c.getColumnIndex("_path")));
            res.add(i);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Item> listarRecetasTipo(String nombreT) {
        String query = "SELECT " + dbhelper.CN_idR + "," + dbhelper.CN_NombreR + "," + dbhelper.CN_Path + " FROM " + dbhelper.TABLA_RECETA + " WHERE '" + nombreT + "' = " + dbhelper.CN_Tipo;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Item> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Item i = new Item();
            i.setIdR(c.getInt(c.getColumnIndex("_id")));
            i.setNombreR(c.getString(c.getColumnIndex("_nombre")));
            i.setPathR(c.getString(c.getColumnIndex("_path")));
            res.add(i);
            c.moveToNext();
        }
        return res;
    }

    public ArrayList<Item> listarRecetas() {
        String query = "SELECT " + dbhelper.CN_idR + "," + dbhelper.CN_NombreR + "," + dbhelper.CN_Path + " FROM " + dbhelper.TABLA_RECETA;
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<Item> res = new ArrayList<>();
        while (c.isAfterLast() == false) {
            Item i = new Item();
            i.setIdR(c.getInt(c.getColumnIndex("_id")));
            i.setNombreR(c.getString(c.getColumnIndex("_nombre")));
            i.setPathR(c.getString(c.getColumnIndex("_path")));
            res.add(i);
            c.moveToNext();
        }
        return res;
    }

    public int _idReceta(String nombreR) {
        String query = "SELECT " + dbhelper.CN_idR + " FROM " + dbhelper.TABLA_RECETA + " WHERE " + dbhelper.CN_NombreR + "='" + nombreR + "'";
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        ArrayList<String> array = new ArrayList<>();
        while (c.isAfterLast() == false) {
            array.add(c.getString(c.getColumnIndex("_id")));
            c.moveToNext();
        }
        return Integer.parseInt(array.get(0));
    }

    public void eliminarReceta(int idR) {
        String query = "DELETE FROM " + dbhelper.TABLA_RECETA + " WHERE " + dbhelper.CN_idR + "=" + Integer.toString(idR);
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
    }

    //Ingrediente: insertar
    //=======================
    private ContentValues valoresIngrediente(Ingrediente i) {
        ContentValues res = new ContentValues();
        res.put(DbHelper.CN_NombreI,i.getNombre());
        res.put(DbHelper.CN_idRI,i.getIdR());
        return res;
    }

    public void insertarIngredientes(Ingrediente i) {
        database.insert(DbHelper.TABLA_INGREDIENTE, null, valoresIngrediente(i));
    }

    public ArrayList<String> leerIngredientesReceta(int idR) {
        ArrayList<String> res = new ArrayList<>();
        String query = "SELECT " + dbhelper.CN_NombreI + " FROM " + dbhelper.TABLA_INGREDIENTE + " WHERE " + dbhelper.CN_idRI + "=" + Integer.toString(idR);
        Cursor c = database.rawQuery(query,null);
        if (c != null) c.moveToFirst();
        while (c.isAfterLast() == false) {
            res.add(c.getString(c.getColumnIndex("_nombre")));
            c.moveToNext();
        }
        return res;
    }
}
