package com.example.german.librorecetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by German on 26/12/2015.
 */
public class DataBaseManager {

    //Tabla Receta
    public static final String TABLA_RECETA = "Receta";
    public static final String CN_idR = "idR";
    public static final String CN_NombreR = "Nombre";
    public static final String CN_Preparacion = "Preparacion";
    public static final String CN_Path = "Path";
    public static final String CN_Tipo = "Tipo";

    //Tabla Ingrediente
    public static final String TABLA_INGREDIENTE = "Ingrediente";
    public static final String CN_idI = "idI";
    public static final String CN_idRI = "idR";
    public static final String CN_NombreI = "Nombre";
    public static final String CN_Cantidad = "Cantidad";

    //Tabla Sustituto
    public static final String TABLA_SUSTITUTO = "Sustituto";
    public static final String CN_idS = "idS";
    public static final String CN_NombreS = "Nombre";
    public static final String CN_idIS = "idI";

    //Creacion de la tabla Recetas
    public static final String CREA_TABLA_RECETA = "CREATE TABLE " + TABLA_RECETA + "(" +
            CN_idR + " INTEGER PRIMARY KEY NOT NULL," +
            CN_NombreR + " TEXT NOT NULL," +
            CN_Preparacion + " TEXT NOT NULL," +
            CN_Path + " TEXT," +
            CN_Tipo + " TEXT);";

    //Creacion de la tabla Ingrediente
    public static final String CREA_TABLA_INGREDIENTE = "CREATE TABLE " + TABLA_INGREDIENTE + "(" +
            CN_idI + " INTEGER NOT NULL," +
            CN_idRI + " INTEGER," +
            CN_NombreI + " TEXT NOT NULL," +
            CN_Cantidad + " TEXT NOT NULL," +
            "PRIMARY KEY(" + CN_idI + "," + CN_idRI + ")," +
            "FOREIGN KEY (" + CN_idRI + ") REFERENCES Receta(" + CN_idR + "));";

    //Creacion de latabla Sustituto
    public static final String CREA_TABLA_SUSTITUTO = "CREATE TABLE " + TABLA_SUSTITUTO + "(" +
            CN_idS + " INTEGER PRIMARY KEY NOT NULL," +
            CN_NombreS + " TEXT NOT NULL," +
            CN_idIS + " INTEGER," +
            "FOREIGN KEY (" + CN_idIS + ") REFERENCES Ingrediente(" + CN_idI + "));";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }


    //Receta: Inerta, eliminar y modificar
    //====================================
    public void insertarR(Receta r) {
        db.execSQL("INSERT INTO " + TABLA_RECETA + " VALUES(" +
                r.getIdR() + ",'" +
                r.getNombre() + "','" +
                r.getPreparacion() + "','" +
                r.getPath() + "','" +
                r.getTipo() + "');");
    }

    public void eliminar(Integer identR) {
        db.delete(TABLA_RECETA, CN_idR + " = ?", new String[]{Integer.toString(identR)});
    }


    private ContentValues generarContentValues(String cambio, Integer identR, String nuevoValor) {
        ContentValues valores = new ContentValues();
        valores.put(CN_idR, identR);
        if (cambio.equals("Nombre"))  valores.put(CN_NombreR, nuevoValor);
        else if (cambio.equals("Preparacion")) valores.put(CN_Preparacion, nuevoValor);
        else if (cambio.equals("Path")) valores.put(CN_Path, nuevoValor);
        else valores.put(CN_Tipo, nuevoValor);

        return valores;
    }

    public void modificarNombre(Integer identR, String nuevoValor) {
        db.update(TABLA_RECETA, generarContentValues("Nombre", identR, nuevoValor), CN_idR + " = ?", new String[]{Integer.toString(identR)});
    }

    public void modificarPreparacion(Integer identR, String nuevoValor) {
        db.update(TABLA_RECETA,generarContentValues("Preparacion",identR,nuevoValor),CN_idR + " = ?",new String[]{Integer.toString(identR)});
    }

    public void modificarPath(Integer identR, String nuevoValor) {
        db.update(TABLA_RECETA,generarContentValues("Path",identR,nuevoValor),CN_idR + " = ?",new String[]{Integer.toString(identR)});
    }

    public void modificarTipo(Integer identR, String nuevoValor) {
        db.update(TABLA_RECETA,generarContentValues("Tipo",identR,nuevoValor),CN_idR + " = ?",new String[]{Integer.toString(identR)});
    }
}
