package com.example.german.librorecetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.channels.WritableByteChannel;
import java.sql.Wrapper;

/**
 * Created by German on 26/12/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_Name = "cjtRecetas.sqlite";
    private static final int DB_Version = 1; //Version actual de la DB

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //Tabla Receta
    public static final String TABLA_RECETA = "Receta";
    public static final String CN_idR = "_id";
    public static final String CN_NombreR = "_nombre";
    public static final String CN_Preparacion = "_preparacion";
    public static final String CN_Path = "_path";
    public static final String CN_Tipo = "_tipo";

    //Tabla Ingrediente
    public static final String TABLA_INGREDIENTE = "Ingrediente";
    public static final String CN_idI = "_id";
    public static final String CN_idRI = "idR";
    public static final String CN_NombreI = "_nombre";
    public static final String CN_Cantidad = "_cantidad";

    //Tabla Sustituto
    public static final String TABLA_SUSTITUTO = "Sustituto";
    public static final String CN_idS = "_id";
    public static final String CN_NombreS = "Nombre";
    public static final String CN_idIS = "idI";

    //Creacion de la tabla Recetas
    public static final String CREA_TABLA_RECETA = "CREATE TABLE " + TABLA_RECETA + "(" +
            CN_idR + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CN_NombreR + " TEXT NOT NULL, " +
            CN_Preparacion + " TEXT NOT NULL, " +
            CN_Path + " TEXT, " +
            CN_Tipo + " TEXT);";

    //Creacion de la tabla Ingrediente
    public static final String CREA_TABLA_INGREDIENTE = "CREATE TABLE " + TABLA_INGREDIENTE + "(" +
            CN_idI + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CN_idRI + " INTEGER," +
            CN_NombreI + " TEXT NOT NULL, " +
            CN_Cantidad + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + CN_idRI + ") REFERENCES Receta(" + CN_idR + "));";

    //Creacion de latabla Sustituto
    public static final String CREA_TABLA_SUSTITUTO = "CREATE TABLE " + TABLA_SUSTITUTO + "(" +
            CN_idS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CN_NombreS + " TEXT NOT NULL, " +
            CN_idIS + " INTEGER, " +
            "FOREIGN KEY (" + CN_idIS + ") REFERENCES Ingrediente(" + CN_idI + "));";

    ////////////////////////////////////////////////////////////////////////////////////////////////


    public DbHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREA_TABLA_INGREDIENTE);
        db.execSQL(CREA_TABLA_RECETA);
        //db.execSQL(CREA_TABLA_SUSTITUTO);
    }

    //Actualizamos la base de datos, incluida la SCHEME_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_RECETA);
        onCreate(db);
        /*
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseManager.TABLA_INGREDIENTE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseManager.TABLA_SUSTITUTO);
        onCreate(db);
        */
    }


    /*

    //Listar recetas por: ingrediente, nombre y tipo
    //==============================================

    public Cursor consultarRecetaPorNombre(String nombreR) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLA_RECETA + " WHERE '" + CN_NombreR + "' = " + nombreR + ";";
        Cursor cursor = db.rawQuery(query,null);
        if (!cursor.equals(null)) cursor.moveToFirst();
        return cursor;
    }

    public Cursor listarRecetaPorNombre(String nombreR) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT " + CN_Path + "," + CN_NombreR + " FROM " + TABLA_RECETA + " WHERE '" + nombreR + "' = " + CN_NombreR;
        Cursor cursor = db.rawQuery(query,null);
        if (!cursor.equals(null)) cursor.moveToFirst();
        return cursor;
    }

    */
}


