package com.example.german.librorecetas;

import android.app.Activity;

/**
 * Created by German on 14/12/2015.
 */
public class Ingrediente {
    private static int id = -1;
    private String nombre = "";
    private int cantidad = 0;

    Ingrediente(String Nombre, int Cantidad) {
        nombre = Nombre;
        cantidad = Cantidad;
        ++id;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setNombre(int id, String newNombre) {
        if (id != -1) nombre = newNombre;
    }

    public void setCantidad(int id, int newCantidad) {
        if (id != -1) cantidad = newCantidad;
    }
}
