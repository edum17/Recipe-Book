package com.example.german.librorecetas;

import android.app.Activity;

/**
 * Created by German on 14/12/2015.
 */
public class Ingrediente {
    private static int _id = -1;
    private String _nombre = "";
    private int _cantidad = 0;

    Ingrediente(String Nombre, int Cantidad) {
        _nombre = Nombre;
        _cantidad = Cantidad;
        ++_id;
    }

    public int getId() {
        return _id;
    }

    public String getNombre() {
        return _nombre;
    }

    public int getCantidad() {
        return _cantidad;
    }

    public void setNombre(int id, String newNombre) {
        if (id != -1) _nombre = newNombre;
    }

    public void setCantidad(int id, int newCantidad) {
        if (id != -1) _cantidad = newCantidad;
    }
}
