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


public class ListarRecetas extends ActionBarActivity {

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
        DbHelper helper = new DbHelper(this,null,null,1);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = helper.listarRecetaPorNombre("A");


        CAdapter cadapter = new CAdapter(this,cursor);
        lista.setAdapter(cadapter);
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
