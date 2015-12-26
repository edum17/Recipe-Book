package com.example.german.librorecetas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by German on 14/12/2015.
 */
public class Receta {
    private static int idR = -1;
    private String nombre;
    private ArrayList<Ingrediente> ingredientes;
    private String preparacion;
    private String path;
    private String tipo;


    Receta (String Nombre,String Preparacion, String Path, String Tipo) {
        nombre = Nombre;
        preparacion = Preparacion;
        path = Path;
        tipo = Tipo;
        ++idR;
    }

    public Integer getIdR() {
        return idR;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPreparacion() {
        return preparacion;
    }

    public void setPreparacion(String preparacion) {
        this.preparacion = preparacion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
