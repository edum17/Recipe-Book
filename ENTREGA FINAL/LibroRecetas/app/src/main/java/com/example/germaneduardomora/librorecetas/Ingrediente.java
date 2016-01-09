package com.example.germaneduardomora.librorecetas;

import android.app.Activity;

/**
 * Created by German on 14/12/2015.
 */
public class Ingrediente {
    private static int _id = -1;
    private static int _idR = -1;
    private String _nombre;

    Ingrediente(String Nombre, int iDR) {
        _nombre = Nombre;
        _idR = iDR;
        ++_id;
    }

    public int getId() {
        return _id;
    }

    public int getIdR(){
        return _idR;
    }

    public void setIdR(int iDR) {
        _idR = iDR;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String newNombre) {
        if (!newNombre.equals(null)) _nombre = newNombre;
    }

}
