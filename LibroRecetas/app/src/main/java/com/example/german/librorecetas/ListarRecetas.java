package com.example.german.librorecetas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ListarRecetas extends ActionBarActivity {

    SQLControlador dbconeccion;
    RadioButton receta;
    RadioButton ingrediente;
    RadioButton tipo;
    EditText texto;
    ImageButton buscar;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_recetas);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();

        receta = (RadioButton) findViewById(R.id.rBNombre);
        ingrediente = (RadioButton) findViewById(R.id.rBIngrediente);
        tipo = (RadioButton) findViewById(R.id.rBTipo);
        texto = (EditText) findViewById(R.id.eTBuscar);
        buscar = (ImageButton) findViewById(R.id.imageButton);

        lista = (ListView) findViewById(R.id.listaRecetas);
        lista.setTextFilterEnabled(true);

        //Muestra los dos campos deseados, el path de la imagen(imagen) y nombre de la receta

    }

    public void clickBuscarR(View v) {
        if (receta.isChecked()) {
            Cursor cursor = dbconeccion.leerRecetaNombre(texto.getText().toString());
            String[] from = new String[]{DbHelper.CN_idR,DbHelper.CN_NombreR};
            int[] to = new int[] {R.id._idR,R.id.nombreReceta};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListarRecetas.this, R.layout.formato_fila, cursor, from, to);

            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);
        }
        else if (ingrediente.isChecked()) {
            Cursor cursor = dbconeccion.leerRecetaIngrediente(texto.getText().toString());
            String[] from = new String[]{DbHelper.CN_idR,DbHelper.CN_NombreR};
            int[] to = new int[] {R.id._idR,R.id.nombreReceta};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListarRecetas.this, R.layout.formato_fila, cursor, from, to);

            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);
        }
        else if (tipo.isChecked()) {
            Cursor cursor = dbconeccion.leerRecetaTipo(texto.getText().toString());
            String[] from = new String[]{DbHelper.CN_idR,DbHelper.CN_NombreR};
            int[] to = new int[] {R.id._idR,R.id.nombreReceta};

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListarRecetas.this, R.layout.formato_fila, cursor, from, to);

            adapter.notifyDataSetChanged();
            lista.setAdapter(adapter);
        }

        dbconeccion.cerrar();
    }

    public void clickListarRecetas(View v) {
        Cursor cursor = dbconeccion.listarRecetas();
        String[] from = new String[]{DbHelper.CN_idR,DbHelper.CN_NombreR};
        int[] to = new int[] {R.id._idR,R.id.nombreReceta};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(ListarRecetas.this, R.layout.formato_fila, cursor, from, to);

        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);

        dbconeccion.cerrar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listar_recetas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
