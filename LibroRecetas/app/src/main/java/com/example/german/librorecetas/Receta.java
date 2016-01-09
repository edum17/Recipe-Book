package com.example.german.librorecetas;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by German on 14/12/2015.
 */
public class Receta {
    private int _id = -1;
    private String _nombre;
    private String _preparacion;
    private String _path;
    private String _tipo;


    Receta (String Nombre,String Preparacion, String Path, String Tipo) {
        this._nombre = Nombre;
        this._preparacion = Preparacion;
        this._path = Path;
        this._tipo = Tipo;
        ++_id;
    }

    public Integer getIdR() {
        return _id;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String nombre) {
        this._nombre = nombre;
    }

    public String getPreparacion() {
        return _preparacion;
    }

    public void setPreparacion(String preparacion) {
        this._preparacion = preparacion;
    }

    public String getPath() {
        return _path;
    }

    public void setPath(String path) {
        this._path = path;
    }

    public String getTipo() {
        return _tipo;
    }

    public void setTipo(String tipo) { this._tipo = tipo;}
}

