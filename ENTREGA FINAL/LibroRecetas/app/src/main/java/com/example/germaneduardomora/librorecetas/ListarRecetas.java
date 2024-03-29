package com.example.germaneduardomora.librorecetas;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class ListarRecetas extends ActionBarActivity {

    SQLControlador dbconeccion;
    RadioButton noIngrediente;
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

        noIngrediente = (RadioButton) findViewById(R.id.rBNoIngrediente);
        ingrediente = (RadioButton) findViewById(R.id.rBIngrediente);
        tipo = (RadioButton) findViewById(R.id.rBTipo);
        texto = (EditText) findViewById(R.id.eTBuscar);
        buscar = (ImageButton) findViewById(R.id.imageButton);

        lista = (ListView) findViewById(R.id.listaRecetas);
        lista.setTextFilterEnabled(true);

        listarTodasRecetas();
    }

    private void listarTodasRecetas() {
        final ListViewAdapter adapter = new ListViewAdapter(ListarRecetas.this,dbconeccion.listarRecetas());
        adapter.notifyDataSetChanged();
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent consultar_receta = new Intent(getApplicationContext(), ConsultarReceta.class);
                String identficador = Long.toString(adapter.getItemId(position));
                String nombre = adapter.getItemName(position);
                consultar_receta.putExtra("idR", identficador);
                consultar_receta.putExtra("nombreR", nombre);
                startActivity(consultar_receta);
            }
        });
    }

    public void clickBuscarR(View v) {
        if (tipo.isChecked()) {
            ArrayList<Item> items = dbconeccion.listarRecetasTipo(texto.getText().toString());
            final ListViewAdapter adapter = new ListViewAdapter(ListarRecetas.this,items);
            if(items.size() != 0) {
                adapter.notifyDataSetChanged();
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent consultar_receta = new Intent(getApplicationContext(), ConsultarReceta.class);
                        String identficador = Long.toString(adapter.getItemId(position));
                        consultar_receta.putExtra("idR", identficador);
                        startActivity(consultar_receta);
                    }
                });
            }
            else {
                Toast.makeText(getBaseContext(),"No se ha encontrado ninguna receta", Toast.LENGTH_LONG).show();
                listarTodasRecetas();
            }
        }

        else if (ingrediente.isChecked()) {
            ArrayList<Item> items = dbconeccion.listarRecetasIngrediente(texto.getText().toString());
            final ListViewAdapter adapter = new ListViewAdapter(ListarRecetas.this,items);
            if(items.size() != 0) {
                adapter.notifyDataSetChanged();
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent consultar_receta = new Intent(getApplicationContext(), ConsultarReceta.class);
                        String identficador = Long.toString(adapter.getItemId(position));
                        consultar_receta.putExtra("idR", identficador);
                        startActivity(consultar_receta);
                    }
                });
            }
            else {
                Toast.makeText(getBaseContext(),"No se ha encontrado ninguna receta", Toast.LENGTH_LONG).show();
                listarTodasRecetas();
            }
        }
        else if (noIngrediente.isChecked()) {
            ArrayList<Item> items = dbconeccion.listarRecetasNoIngrediente(texto.getText().toString());
            final ListViewAdapter adapter = new ListViewAdapter(ListarRecetas.this,items);
            if(items.size() != 0) {
                adapter.notifyDataSetChanged();
                lista.setAdapter(adapter);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent consultar_receta = new Intent(getApplicationContext(), ConsultarReceta.class);
                        String identficador = Long.toString(adapter.getItemId(position));
                        consultar_receta.putExtra("idR", identficador);
                        startActivity(consultar_receta);
                    }
                });
            }
            else {
                Toast.makeText(getBaseContext(),"No se ha encontrado ninguna receta", Toast.LENGTH_LONG).show();
                listarTodasRecetas();
            }
        }
        else listarTodasRecetas();

        //dbconeccion.cerrar();
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
        if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ListarRecetas.this);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Para listar una receta determinada, es necesario seleccionar una de las tres opciones disponible, escribir la palabra a buscar y clicar en el boton de buscar. Una vez obtenidas las recetas deseadas, podemos acceder a una receta clicando sobre ella.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
