package com.example.german.librorecetas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Type;


public class MainActivity extends ActionBarActivity {

    private Button btNuevaR, btListaR;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creacion de los botones
        //btNuevaR = (Button) findViewById(R.id.btNuevaR);
        //btListaR = (Button) findViewById(R.id.btListaR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Si se desea registrar una receta, accedemos a Nueva receta. En caso de querer consultar una receta especifica, accedemos a Lista de recetas.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            //Toast.makeText(getBaseContext(),"Has pulsado en Ayuda",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_about) {
            final Toast t = Toast.makeText(getBaseContext(),"Libro de Recetas es una aplicacion para gestionar recetas de cocina.\nGerman Eduardo Mora Macias.\nLa version de la aplicacion v.1.0.",Toast.LENGTH_LONG);
            t.setGravity(Gravity.BOTTOM, 20, 20);
            t.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickNuevaR(View v) {
        //Toast.makeText(getBaseContext(), "Has pulsado Nueva Receta", Toast.LENGTH_SHORT).show();
        //Vamos a la pantalla de la nueva receta
        btNuevaR = (Button) findViewById(R.id.btNuevaR);
        Intent nueva_receta = new Intent(getApplicationContext(), com.example.german.librorecetas.NuevaReceta.class);
        startActivity(nueva_receta);
    }

    public void clickListarR(View v) {
        //Toast.makeText(getBaseContext(),"Has pulsado Lista de Recetas",Toast.LENGTH_SHORT).show();
        //Vamos a la pantalla de listar recetas
        btListaR = (Button) findViewById(R.id.btListaR);
        Intent listar_recetas = new Intent(getApplicationContext(),ListarRecetas.class);
        startActivity(listar_recetas);
    }
}

