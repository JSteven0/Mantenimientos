package com.example.mantenimientos;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;

public class FormulariosActivity extends AppCompatActivity {
    private MyDBSQLiteHelper admin;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor fila;

    private ImageButton btnCamara;
    private ImageView imgView;
    private Bitmap bmp1, bmp2;

    private EditText et1, et2, et3, et4, et5, et6, et7;
    private Spinner sp1, sp2, sp3, sp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formularios);
        //Activar el soporte para la ActionBar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        admin = new MyDBSQLiteHelper(this, Vars.db, null, Vars.vers);

        et1 = findViewById(R.id.ET_nombre);
        et2 = findViewById(R.id.ET_apellidos);
        et3 = findViewById(R.id.ET_codigo);
        et4 = findViewById(R.id.ET_estado);
        et5 = findViewById(R.id.ET_marca);
        et6 = findViewById(R.id.ET_serial);
        et7 = findViewById(R.id.ET_especificacion);
        sp1 = findViewById(R.id.SP_equipo);
        sp2 = findViewById(R.id.SP_hardware);
        sp3 = findViewById(R.id.SP_software);
        sp4 = findViewById(R.id.SP_solucion);

        btnCamara = findViewById(R.id.BT_camara);
        imgView = findViewById(R.id.IV_equipo);
        imgView.setImageDrawable(null);
    }

    //Destuir la aplicación
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void abrirCamara(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imgBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imgBitmap);
        }
    }

    public void RellenarFormulario(View view) {
        String nombre = et1.getText().toString();
        String apellidos = et2.getText().toString();
        String codigo = et3.getText().toString();
        String estado = et4.getText().toString();
        String equipo = sp1.getSelectedItem().toString();
        String marca = et5.getText().toString();
        String serial = et6.getText().toString();
        String ProbHardware = sp2.getSelectedItem().toString();
        String ProbSoftware = sp3.getSelectedItem().toString();
        String especific = et7.getText().toString();
        String solucion = sp4.getSelectedItem().toString();
        if(!nombre.equals("") && !apellidos.equals("") && !codigo.equals("") && !estado.equals("") && !marca.equals("") && !serial.equals("") && !especific.equals("")) {
            String imgCodificado = "";
            imgView.buildDrawingCache(true);
            bmp1 = imgView.getDrawingCache(true);
            if(imgView.getDrawable() != null) {
                bmp2 = Bitmap.createScaledBitmap(bmp1, imgView.getWidth(), imgView.getHeight(), true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG, 25, baos);
                byte[] imagen = baos.toByteArray();
                imgCodificado = Base64.encodeToString(imagen, Base64.DEFAULT);
                db = admin.getWritableDatabase();
                cv = new ContentValues();
                cv.put("nombre", nombre);
                cv.put("apellido", apellidos);
                cv.put("codigo", codigo);
                cv.put("estado", estado);
                cv.put("tipoEqu", equipo);
                cv.put("marca", marca);
                cv.put("serial", serial);
                cv.put("probHardware", ProbHardware);
                cv.put("probSoftware", ProbSoftware);
                cv.put("especificacion", especific);
                cv.put("solucionProb", solucion);
                cv.put("imagen", imgCodificado);
                long reg = db.insert("formulario", null, cv);
                if (reg == -1) {
                    Toast.makeText(this, "No se pudo agregar el registro", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Registro almacenado", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et1.requestFocus();
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    sp1.setSelection(0);
                    et5.setText("");
                    et6.setText("");
                    sp2.setSelection(0);
                    sp3.setSelection(0);
                    et7.setText("");
                    sp4.setSelection(0);
                    imgView.destroyDrawingCache();
                    imgView.setImageBitmap(null);
                    imgView.setImageDrawable(null);
                }
                db.close();
            } else {
                Toast.makeText(this, "Por favor, tome la fotografía", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
        }

    }

    public void BuscarFormulario(View view) {
        String codigo = et3.getText().toString();
        if(!codigo.equals("")){
            Bitmap decodedByte = null;
            byte[] decodedString;
            db = admin.getReadableDatabase();
            fila = db.rawQuery("SELECT * FROM formulario WHERE codigo='"+codigo+"'", null);
            if(fila.getCount() > 0){
                String datos = "";
                if (fila.moveToFirst()) {
                    et1.setText(fila.getString(1));
                    et2.setText(fila.getString(2));
                    et4.setText(fila.getString(4));
                    Adapter adapter1 = sp1.getAdapter();
                    for(int i=0; i<adapter1.getCount(); i++){
                        if(fila.getString(5).equals(adapter1.getItem(i))){
                            sp1.setSelection(i);
                        }
                    }
                    et5.setText(fila.getString(6));
                    et6.setText(fila.getString(7));
                    Adapter adapter2 = sp2.getAdapter();
                    for(int i=0; i<adapter2.getCount(); i++){
                        if(fila.getString(8).equals(adapter2.getItem(i))){
                            sp2.setSelection(i);
                        }
                    }
                    Adapter adapter3 = sp3.getAdapter();
                    for(int i=0; i<adapter3.getCount(); i++){
                        if(fila.getString(9).equals(adapter3.getItem(i))){
                            sp3.setSelection(i);
                        }
                    }
                    et7.setText(fila.getString(10));
                    Adapter adapter4 = sp4.getAdapter();
                    for(int i=0; i<adapter4.getCount(); i++){
                        if(fila.getString(11).equals(adapter4.getItem(i))){
                            sp4.setSelection(i);
                        }
                    }
                    decodedString = Base64.decode(fila.getString(12), Base64.DEFAULT);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgView.setImageBitmap(decodedByte);
                }
                db.close();
            } else {
                Toast.makeText(this, "El formulario no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese el código del formulario", Toast.LENGTH_SHORT).show();
        }
    }

    public void EditarFormulario(View view) {
        String nombre = et1.getText().toString();
        String apellidos = et2.getText().toString();
        String codigo = et3.getText().toString();
        String estado = et4.getText().toString();
        String equipo = sp1.getSelectedItem().toString();
        String marca = et5.getText().toString();
        String serial = et6.getText().toString();
        String ProbHardware = sp2.getSelectedItem().toString();
        String ProbSoftware = sp3.getSelectedItem().toString();
        String especific = et7.getText().toString();
        String solucion = sp4.getSelectedItem().toString();
        if(!nombre.equals("") && !apellidos.equals("") && !codigo.equals("") && !estado.equals("") && !marca.equals("") && !serial.equals("") && !especific.equals("")) {
            String imgCodificado = "";
            imgView.buildDrawingCache(true);
            bmp1 = imgView.getDrawingCache(true);
            if(imgView.getDrawable() != null) {
                bmp2 = Bitmap.createScaledBitmap(bmp1, imgView.getWidth(), imgView.getHeight(), true);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp2.compress(Bitmap.CompressFormat.PNG, 25, baos);
                byte[] imagen = baos.toByteArray();
                imgCodificado = Base64.encodeToString(imagen, Base64.DEFAULT);
                db = admin.getWritableDatabase();
                cv = new ContentValues();
                cv.put("nombre", nombre);
                cv.put("apellido", apellidos);
                cv.put("codigo", codigo);
                cv.put("estado", estado);
                cv.put("tipoEqu", equipo);
                cv.put("marca", marca);
                cv.put("serial", serial);
                cv.put("probHardware", ProbHardware);
                cv.put("probSoftware", ProbSoftware);
                cv.put("especificacion", especific);
                cv.put("solucionProb", solucion);
                cv.put("imagen", imgCodificado);
                long reg = db.update("formulario", cv, "codigo='"+codigo+"'", null);
                if(reg == 0) {
                    Toast.makeText(this, "No se pudo editar el formulario", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Formulario editado", Toast.LENGTH_SHORT).show();
                    et1.setText("");
                    et1.requestFocus();
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                    sp1.setSelection(0);
                    et5.setText("");
                    et6.setText("");
                    sp2.setSelection(0);
                    sp3.setSelection(0);
                    et7.setText("");
                    sp4.setSelection(0);
                    imgView.destroyDrawingCache();
                    imgView.setImageBitmap(null);
                    imgView.setImageDrawable(null);
                }
                db.close();
            }
        } else {
            Toast.makeText(this, "Por favor ingrese los datos", Toast.LENGTH_SHORT).show();
        }

    }

    public void EliminarFormulario(View view) {
        String codigo = et3.getText().toString();
        if(!codigo.equals("")) {
            db = admin.getWritableDatabase();
            String [] args = new String[]{codigo};
            int reg = db.delete("formulario", "codigo=?", args);
            if(reg == 0){
                Toast.makeText(this, "No se pudo eliminar el formulario", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Formulario eliminado", Toast.LENGTH_SHORT).show();
                et1.setText("");
                et1.requestFocus();
                et2.setText("");
                et3.setText("");
                et4.setText("");
                sp1.setSelection(0);
                et5.setText("");
                et6.setText("");
                sp2.setSelection(0);
                sp3.setSelection(0);
                et7.setText("");
                sp4.setSelection(0);
            }
            db.close();
        } else {
            Toast.makeText(this, "Por favor ingrese el código del formulario", Toast.LENGTH_SHORT).show();
        }
    }
}
