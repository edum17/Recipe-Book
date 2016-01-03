package com.example.german.librorecetas;

import android.app.Activity;

/**
 * Created by German on 14/12/2015.
 */
public class Ingrediente {
    private static int _id = -1;
    private String _nombre;

    Ingrediente(String Nombre, int iD) {
        _nombre = Nombre;
        _id = iD;
    }

    public int getId() {
        return _id;
    }
    
    public void setID(int id){
        if(id != -1) _id = id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String newNombre) {
        if (!newNombre.equals(null)) _nombre = newNombre;
    }

}
