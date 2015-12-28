package com.example.german.librorecetas;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by German on 28/12/2015.
 */
public class CAdapter extends CursorAdapter {

    public CAdapter(Context context, Cursor cursor) {
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.activity_listar_recetas,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nombre = (TextView) view.findViewById(R.id.eTNombre);
        String nombreTxt = cursor.getString(cursor.getColumnIndexOrThrow("Nombre"));
        nombre.setText(nombreTxt);
    }
}
