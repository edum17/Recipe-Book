package com.example.germaneduardomora.librorecetas;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.WritableByteChannel;
import java.sql.Wrapper;

/**
 * Created by German on 26/12/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_Name = "LibroRecetas.sqlite";
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
    public static final String CN_idRI = "_idR";
    public static final String CN_NombreI = "_nombre";

    //Tabla Sustituto
    public static final String TABLA_SUSTITUTO = "Sustituto";
    public static final String CN_idS = "_id";
    public static final String CN_NombreS = "_nombre";
    public static final String CN_idIS = "_idI";

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
            CN_idRI + " INTEGER NOT NULL," +
            CN_NombreI + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + CN_idRI + ") REFERENCES " + TABLA_RECETA + " (" + CN_idR + "));";

    //Creacion de latabla Sustituto
    public static final String CREA_TABLA_SUSTITUTO = "CREATE TABLE " + TABLA_SUSTITUTO + "(" +
            CN_idS + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            CN_NombreS + " TEXT NOT NULL, " +
            CN_idIS + " INTEGER, " +
            "FOREIGN KEY (" + CN_idIS + ") REFERENCES " + TABLA_INGREDIENTE + " (" + CN_idI + "));";

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public DbHelper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREA_TABLA_RECETA);
        db.execSQL(CREA_TABLA_INGREDIENTE);
        db.execSQL(CREA_TABLA_SUSTITUTO);
        insertReceta1(db);
        insertReceta2(db);
        insertReceta3(db);
        insertReceta4(db);
        insertReceta5(db);
    }

    //Actualizamos la base de datos, incluida la SCHEME_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_RECETA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_INGREDIENTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_SUSTITUTO);
        onCreate(db);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void insertReceta1(SQLiteDatabase db) {
        ContentValues asado_pollo = new ContentValues();
        asado_pollo.put(CN_NombreR, "Asado de pollo con patatas");
        asado_pollo.put(CN_Preparacion, "En primer lugar se pelan las patatas, cebollas, y ajos. Seguido de lo cual en la bandeja de hornear se pone aceite, se ponen los ajos, una capa de cebolla, otra capa de patatas y otra de tomate. Se pone el pollo en la capa superior, se incorpora un vaso de vino y se sazona al gusto. Finalmente se hornea a 180 grados durante una hora.");
        asado_pollo.put(CN_Path, "asado_pollo");
        asado_pollo.put(CN_Tipo, "Mediterranea");
        db.insert(TABLA_RECETA, null, asado_pollo);
        ContentValues pollo = new ContentValues();
        pollo.put(CN_NombreI,"Pollo");
        pollo.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, pollo);

        ContentValues patatas = new ContentValues();
        patatas.put(CN_NombreI, "Patatas");
        patatas.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, patatas);

        ContentValues aceite = new ContentValues();
        aceite.put(CN_NombreI, "Aceite");
        aceite.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, aceite);

        ContentValues tomate = new ContentValues();
        tomate.put(CN_NombreI, "Tomate");
        tomate.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, tomate);

        ContentValues sal = new ContentValues();
        sal.put(CN_NombreI, "Sal");
        sal.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, sal);

        ContentValues pimienta = new ContentValues();
        pimienta.put(CN_NombreI, "Pimienta");
        pimienta.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, pimienta);

        ContentValues ajo = new ContentValues();
        ajo.put(CN_NombreI, "Ajo");
        ajo.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, ajo);

        ContentValues cebolla = new ContentValues();
        cebolla.put(CN_NombreI, "Cebolla");
        cebolla.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, cebolla);

        ContentValues vinob = new ContentValues();
        vinob.put(CN_NombreI, "Vino blanco");
        vinob.put(CN_idRI, 1);
        db.insert(TABLA_INGREDIENTE, null, vinob);
    }


    private void insertReceta2(SQLiteDatabase db) {
        ContentValues crepes = new ContentValues();
        crepes.put(CN_NombreR, "Crepes de chocolate con frutas de temporada");
        crepes.put(CN_Preparacion, "Se bate la leche, el huevo y la harina hasta que quede una masa liquida y homogenea. En una sarten caliente se pone un poco de mantequilla y se vierte parte de la masa, se voltea para hacerlo hacia el otro lado. Una vez hecho el crepe, se pone chocolate y se dobla. Finalmente se sirve espolvoreando azucar glas por encima junto con la fruta de temporada.");
        crepes.put(CN_Path, "crepes");
        crepes.put(CN_Tipo, "Francesa");
        db.insert(TABLA_RECETA, null, crepes);

        ContentValues Leche = new ContentValues();
        Leche.put(CN_NombreI,"Leche");
        Leche.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Leche);

        ContentValues Huevo = new ContentValues();
        Huevo.put(CN_NombreI, "Huevo");
        Huevo.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Huevo);

        ContentValues Harina = new ContentValues();
        Harina.put(CN_NombreI, "Harina");
        Harina.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Harina);

        ContentValues Mantequilla = new ContentValues();
        Mantequilla.put(CN_NombreI, "Mantequilla");
        Mantequilla.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Mantequilla);

        ContentValues Chocolate = new ContentValues();
        Chocolate.put(CN_NombreI, "Chocolate");
        Chocolate.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Chocolate);

        ContentValues Azucar = new ContentValues();
        Azucar.put(CN_NombreI, "Azucar glas");
        Azucar.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Azucar);

        ContentValues Fruta = new ContentValues();
        Fruta.put(CN_NombreI, "Fruta");
        Fruta.put(CN_idRI, 2);
        db.insert(TABLA_INGREDIENTE, null, Fruta);
    }

    private void insertReceta3(SQLiteDatabase db) {
        ContentValues torrijas = new ContentValues();
        torrijas.put(CN_NombreR, "Torrijas");
        torrijas.put(CN_Preparacion, "Se cuece la leche con una rama de canela y la corteza de limon. Se retira y se vierte sobre el pan cortado a rodajas previamente. Una vez que quede bien empapado, se frie en una sarten con aceite caliente. Y finalmente se recubre de azucar y canela en polvo.");
        torrijas.put(CN_Path, "torrijas");
        torrijas.put(CN_Tipo, "Mediterranea");
        db.insert(TABLA_RECETA, null, torrijas);

        ContentValues Leche = new ContentValues();
        Leche.put(CN_NombreI,"Leche");
        Leche.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Leche);

        ContentValues Pan = new ContentValues();
        Pan.put(CN_NombreI, "Pan");
        Pan.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Pan);

        ContentValues Huevo = new ContentValues();
        Huevo.put(CN_NombreI, "Huevo");
        Huevo.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Huevo);;

        ContentValues Azucar = new ContentValues();
        Azucar.put(CN_NombreI, "Azucar");
        Azucar.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Azucar);

        ContentValues Aceite = new ContentValues();
        Aceite.put(CN_NombreI, "Aceite");
        Aceite.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Aceite);

        ContentValues Limon = new ContentValues();
        Limon.put(CN_NombreI, "Limon");
        Limon.put(CN_idRI, 3);
        db.insert(TABLA_INGREDIENTE, null, Limon);
    }

    private void insertReceta4(SQLiteDatabase db) {
        ContentValues lasana = new ContentValues();
        lasana.put(CN_NombreR, "Lasana de carne");
        lasana.put(CN_Preparacion, "En una sarten se sofrie la cebolla y el pimiento cortado en juliana, se pone la carne picada y una pizca de sal. Una vez sofrito todo, se le incorpora el tomate frito, de manera que el relleno queda listo. Seguido de lo cual se procede a montar las capas del plato, poniendo en una bandeja para hornear capas de pasta y del sofrito tantas veces como se desee. Finalmente se cubre con bechamel y queso rallado, horneando hasta que quede gratinado.");
        lasana.put(CN_Path, "lasana");
        lasana.put(CN_Tipo, "Italiana");
        db.insert(TABLA_RECETA, null, lasana);

        ContentValues Pasta = new ContentValues();
        Pasta.put(CN_NombreI,"Pasta");
        Pasta.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Pasta);

        ContentValues Carne = new ContentValues();
        Carne.put(CN_NombreI, "Carne picada");
        Carne.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Carne);

        ContentValues Cebolla = new ContentValues();
        Cebolla.put(CN_NombreI, "Cebolla");
        Cebolla.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Cebolla);

        ContentValues Pimiento = new ContentValues();
        Pimiento.put(CN_NombreI, "Pimiento rojo");
        Pimiento.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Pimiento);

        ContentValues Tomate = new ContentValues();
        Tomate.put(CN_NombreI, "Tomate frito");
        Tomate.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Tomate);

        ContentValues Bechamel = new ContentValues();
        Bechamel.put(CN_NombreI, "Bechamel");
        Bechamel.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Bechamel);

        ContentValues Queso = new ContentValues();
        Queso.put(CN_NombreI, "Queso");
        Queso.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Queso);

        ContentValues Sal = new ContentValues();
        Sal.put(CN_NombreI, "Sal");
        Sal.put(CN_idRI, 4);
        db.insert(TABLA_INGREDIENTE, null, Sal);

    }

    private void insertReceta5(SQLiteDatabase db) {
        ContentValues zarangollo = new ContentValues();
        zarangollo.put(CN_NombreR, "Zarangollo");
        zarangollo.put(CN_Preparacion, "En una sarten se sofrie en primer lugar la patata, despues se incorpora la cebolla y el calabacin. Una vez sofrito todo se ponen los huevos y se va removiendo de manera que queda un revuelto. ");
        zarangollo.put(CN_Path, "zarangollo");
        zarangollo.put(CN_Tipo, "Mediterranea");
        db.insert(TABLA_RECETA, null, zarangollo);

        ContentValues Huevo = new ContentValues();
        Huevo.put(CN_NombreI, "Huevo");
        Huevo.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, Huevo);

        ContentValues Cebolla = new ContentValues();
        Cebolla.put(CN_NombreI, "Cebolla");
        Cebolla.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, Cebolla);

        ContentValues Calabacin = new ContentValues();
        Calabacin.put(CN_NombreI, "Calabacin");
        Calabacin.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, Calabacin);

        ContentValues patatas = new ContentValues();
        patatas.put(CN_NombreI, "Patatas");
        patatas.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, patatas);

        ContentValues Sal = new ContentValues();
        Sal.put(CN_NombreI, "Sal");
        Sal.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, Sal);

        ContentValues aceite = new ContentValues();
        aceite.put(CN_NombreI, "Aceite");
        aceite.put(CN_idRI, 5);
        db.insert(TABLA_INGREDIENTE, null, aceite);
    }

}