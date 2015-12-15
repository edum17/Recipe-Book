package com.example.german.librorecetas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by German on 14/12/2015.
 */
public class Receta {
    private static int id = -1;
    private String nombre = "";
    private ArrayList<Ingrediente> ingredientes;
    private String preparacion = "";


    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    private ImageView foto;

    Receta (String Nombre, ArrayList<Ingrediente>LIngredientes,String Preparacion) {
        nombre = Nombre;
        ingredientes = LIngredientes;
        preparacion = preparacion;
        ++id;
    }
}
