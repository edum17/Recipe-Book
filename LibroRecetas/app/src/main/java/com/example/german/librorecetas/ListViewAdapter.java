package com.example.german.librorecetas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by German on 05/01/2016.
 */
public class ListViewAdapter extends BaseAdapter {


    Context context;
    ArrayList <Item> recetas;
    LayoutInflater inflater;


    public ListViewAdapter(Context context, ArrayList<Item> receta) {
        this.context = context;
        this.recetas = receta;
    }

    @Override
    public int getCount() {
        return recetas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return recetas.get(position).getIdR();
    }

    public String getItemName(int position) {
        return recetas.get(position).getNombreR();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView txtNombre;
        ImageView imgImagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.formato_fila, parent, false);

        txtNombre = (TextView) itemView.findViewById(R.id.fila_lista_nombre);
        imgImagen = (ImageView) itemView.findViewById(R.id.fila_lista_imagen);

        txtNombre.setText(recetas.get(position).getNombreR());
        String dir = recetas.get(position).getPathR();
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imgImagen.setImageBitmap(bitmap);

        return itemView;
    }
}
