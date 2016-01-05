package com.example.german.librorecetas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;


public class NuevaReceta extends ActionBarActivity{

    private String APP_DIRECTORY = "Libro de Recetas";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    private String posIng;
    private ArrayList<String> ingSeleccionados;

    EditText nombre;
    ListView listaIng;
    EditText preparacion;
    ImageView imagen;
    String Path;
    EditText tipo;
    Button btFoto;
    Spinner spinIng;

    SQLControlador dbconeccion;

    //Creacion y lanzamiento de la base de datos
    //DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_receta);

        nombre = (EditText) findViewById(R.id.eTNombre);
        listaIng = (ListView) findViewById(R.id.listaIngredientes);
        preparacion = (EditText) findViewById(R.id.eTPreparacion);
        btFoto = (Button) findViewById(R.id.bFoto);
        imagen = (ImageView) findViewById(R.id.iVFoto);
        tipo = (EditText) findViewById(R.id.eTTipoComida);
        spinIng = (Spinner) findViewById(R.id.spIngredientes);
        ingSeleccionados = new ArrayList<>();
        anadirIngredientesLista();

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();
    }

    private void listarIngredientesSeleccionados(final ArrayList<String> seleccionados) {
        //Adaptador ListView
        final ArrayAdapter<String> adapterLista;
        adapterLista = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,seleccionados);
        listaIng.setAdapter(adapterLista);
        listaIng.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Calculamos la posicion del inrediente que se quiere eliminar
                posIng = parent.getItemAtPosition(position).toString();
                AlertDialog.Builder Adialog = new AlertDialog.Builder(NuevaReceta.this);
                Adialog.setTitle("Eliminar ingrediente");
                Adialog.setMessage("Desea eliminar el ingrediente seleccionado");
                Adialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapterLista.remove(posIng);
                        adapterLista.notifyDataSetChanged();
                    }
                });
                Adialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog Alertdialog = Adialog.create();
                Alertdialog.show();
            }
        });
    }

    private void anadirIngredientesLista() {
        //Adaptador Spinner
        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this,R.array.ingredientes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinIng.setAdapter(adapter);
        spinIng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Elemento seleccionado del spinner, que se anadira a la lista de ingredientes de la receta
                String seleccionado = parent.getItemAtPosition(position).toString();
                int i = 0;
                boolean trobat = false;
                while (i < ingSeleccionados.size() && !trobat) {
                    if (ingSeleccionados.get(i).equals(seleccionado)) trobat = true;
                    else ++i;
                }
                if (!trobat) ingSeleccionados.add(seleccionado);
                if (ingSeleccionados.size() > 1) {
                    for (int j = 0; j < ingSeleccionados.size(); ++j)
                        if (ingSeleccionados.get(j).equals("Seleccionar ingredientes"))
                            ingSeleccionados.remove(j);
                }
                if (ingSeleccionados.size() < 1) ingSeleccionados.add("Seleccionar ingredientes");
                listarIngredientesSeleccionados(ingSeleccionados);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nueva_receta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            addRecetaDb();
            Toast.makeText(getBaseContext(), "Receta guardada", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_cancel) {
            dissmiss();
            Toast.makeText(getBaseContext(),"No se ha guardado la receta",Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_help) {
            Toast.makeText(getBaseContext(),"Has pulsado en Ayuda",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void anadirIngredientes(View v) {

    }

    public void makePicture(View v) {
        final CharSequence[] options = {"Hacer foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(NuevaReceta.this);
        builder.setTitle("Elige una opcion");
        builder.setItems(options, new DialogInterface.OnClickListener() {

            //Escucha la opcion del dialogo que hemos elegido
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                if (options[seleccion] == "Hacer foto") {
                    openCamera();
                } else if (options[seleccion] == "Elegir de galeria") {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona imagen"), SELECT_PICTURE);
                } else if (options[seleccion] == "Cancelar") {
                    dialog.dismiss(); //El dialogo se deshace
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitMap(dir); //Decodifica la imagen para presentarsela al usuario
                }
            break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK) {
                    Uri path = data.getData();
                    Path = path.getPath();
                    imagen.setImageURI(path);
                }
            break;
        }
    }

    private void decodeBitMap(String dir) {
        Bitmap bitmap;
        Path = dir;
        bitmap = BitmapFactory.decodeFile(dir);
        imagen.setImageBitmap(bitmap);
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        file.mkdirs();
        String path = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
        File newFile = new File(path);

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); //Mediante este llamada se abirar la camara y captura la imagen
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile)); //Para almacenar imagen o video
        startActivityForResult(intent, PHOTO_CODE);
    }

    //String Nombre,String Ingredientes,String Preparacion, String Path, String Tipo
    public void addRecetaDb() {
        Receta r = new Receta(nombre.getText().toString(), preparacion.getText().toString(), Path, tipo.getText().toString());
        dbconeccion.insertarDatos(r);
        //Insertamos los ingredientes en la base de datos
        for (int i = 0; i < ingSeleccionados.size(); ++i) {
            Ingrediente ing = new Ingrediente(ingSeleccionados.get(i),dbconeccion._idReceta(nombre.getText().toString()));
            dbconeccion.insertarIngredientes(ing);
        }

        dbconeccion.cerrar();
        finish(); //Termina con el activity de alta y vuelve al menu principal
    }

    private void dissmiss() {
        nombre.setText("");
        listaIng.clearAnimation();
        spinIng.clearAnimation();
        preparacion.setText("");
        tipo.setText("");
    }
}
