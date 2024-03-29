package com.example.german.librorecetas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class ConsultarReceta extends ActionBarActivity {

    private String APP_DIRECTORY = "Libro de Recetas";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME = "temporal.jpg";

    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;

    String idR;
    String nombreR;
    SQLControlador dbconeccion;

    EditText nombre;
    ListView listaIng;
    EditText preparacion;
    ImageView imagen;
    EditText tipo;
    String Path;
    Button btFoto;
    ArrayList<String> receta;
    ArrayList<String> ingredientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_receta);

        dbconeccion = new SQLControlador(this);
        dbconeccion.abrirBaseDatos();
        idR = getIntent().getStringExtra("idR");
        nombreR = getIntent().getStringExtra("nombreR");

        nombre = (EditText) findViewById(R.id.eTNombreR);
        listaIng = (ListView) findViewById(R.id.listaIngredientesR);
        preparacion = (EditText) findViewById(R.id.eTPreparacionR);
        imagen = (ImageView) findViewById(R.id.iVFotoR);
        btFoto = (Button) findViewById(R.id.bFotoR);
        tipo = (EditText) findViewById(R.id.eTTipoComidaR);

        receta = dbconeccion.leerReceta(Integer.parseInt(idR));
        ingredientes = dbconeccion.leerIngredientesReceta(Integer.parseInt(idR));

        nombre.setText(receta.get(0));
        preparacion.setText(receta.get(1));
        Path = receta.get(2);
        decodeBitMap(Path);
        tipo.setText(receta.get(3));

        final ArrayAdapter<String> adapterLista;
        adapterLista = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ingredientes);
        listaIng.setAdapter(adapterLista);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_consultar_receta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            if (!nombre.getText().toString().isEmpty() && !preparacion.getText().toString().isEmpty() && !tipo.getText().toString().isEmpty()) {
                guardarCambiosEfectuados();
            }
            else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarReceta.this);
                builder.setTitle("Campos incompletos").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
                builder.setMessage("Para poder guardar una receta es necesario rellenar todos los campos, a excepcion de la imagen de la receta y los ingredientes de esta.");
                builder.setNeutralButton("Aceptar",null);
                builder.show();
            }
            return true;
        }
        else if (id == R.id.action_delete) {
            AlertDialog.Builder Adialog = new AlertDialog.Builder(ConsultarReceta.this);
            Adialog.setTitle("Eliminar receta");
            Adialog.setMessage("Desea eliminar la receta");
            Adialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dbconeccion.eliminarReceta(Integer.parseInt(idR));
                    Toast.makeText(getBaseContext(), "Receta eliminada", Toast.LENGTH_SHORT).show();
                    volverListarRecetas();
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
            return true;
        }
        else if (id == R.id.action_help) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarReceta.this);
            builder.setTitle("Ayuda").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_info_details));
            builder.setMessage("Para guardar los cambios reaizados en los diferentes campos de la receta, clicamos en Guardar cambios. De la misma manera, si queremos eliminar una receta tan solo tenemos que clicar en Eliminar receta.");
            builder.setNeutralButton("Aceptar",null);
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void guardarCambiosEfectuados() {
        Receta r = new Receta(nombre.getText().toString(), preparacion.getText().toString(), Path, tipo.getText().toString());
        boolean existeNombreActual = dbconeccion.existeReceta(nombre.getText().toString());

        if (nombre.getText().toString().equals(nombreR) || !existeNombreActual) {
            dbconeccion.actualizarReceta(Integer.parseInt(idR),r);
            Toast.makeText(getBaseContext(), "Receta actualizada", Toast.LENGTH_SHORT).show();
            volverListarRecetas();
        }
        else Toast.makeText(getBaseContext(),"Ya existe una receta con este nombre.",Toast.LENGTH_LONG).show();

    }

    public void volverListarRecetas() {
        Intent listarRecetas_intent = new Intent(getApplicationContext(),ListarRecetas.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(listarRecetas_intent);
    }

    public void makePicture(View v) {
        final CharSequence[] options = {"Hacer foto","Elegir de galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ConsultarReceta.this);
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
                    InputStream is;
                    try {
                        is = getContentResolver().openInputStream(path);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        Bitmap bitmap = BitmapFactory.decodeStream(bis);
                        imagen.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Cursor c = getContentResolver().query(path,null,null,null,null);
                    c.moveToFirst();
                    int index = c.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    Path = c.getString(index);
                    //System.out.println("*************************** Path: " + Path);
                    //Path = path.getPath();
                    //imagen.setImageURI(path);
                }
                break;
        }
    }

    private void decodeBitMap(String dir) {
        Bitmap bitmap;
        Path = dir;
        if (dir.equals("asado_pollo")) {
            //imagen.setImageResource(R.drawable.asado_pollo);
        }
        else if (dir.equals("crepes")) {
            //imagen.setImageResource(R.drawable.crepes_chocolate);
        }
        else if (dir.equals("torrijas")) {
            //imagen.setImageResource(R.drawable.torrijas);
        }
        else if (dir.equals("lasana")) {
            //imagen.setImageResource(R.drawable.lasana);
        }
        else if (dir.equals("zarangollo")) {
            //imagen.setImageResource(R.drawable.zarangollo);
        }
        else {
            bitmap = BitmapFactory.decodeFile(dir);
            imagen.setImageBitmap(bitmap);
        }
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
}
