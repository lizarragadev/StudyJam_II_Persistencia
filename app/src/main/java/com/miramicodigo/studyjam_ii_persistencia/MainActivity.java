package com.miramicodigo.studyjam_ii_persistencia;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     *
     * @author Gustavo Lizarraga
     * @version 1.0
     * @date 15/12/2016
     * #DevStudyJam
     * www.miramicodigo.com
     *
     * */

    private EditText etPreferencesUno;
    private EditText etPreferencesDos;
    private EditText etInterno;
    private EditText etExterno;

    private Button btnPreferencesGuardar;
    private Button btnPreferencesLeer;
    private Button btnInternoGuardar;
    private Button btnInternoLeer;
    private Button btnExternoGuardar;
    private Button btnExternoLeer;

    private SharedPreferences sharedPreferences;

    private String nombreArchivoInterno = "prueba_archivo_int.txt";
    private String nombreArchivoExterno = "prueba_archivo_ext.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("mis_preferencias", Context.MODE_PRIVATE);

        initUI();
        verificaPermiso();

        btnPreferencesGuardar.setOnClickListener(this);
        btnPreferencesLeer.setOnClickListener(this);
        btnInternoGuardar.setOnClickListener(this);
        btnInternoLeer.setOnClickListener(this);
        btnExternoGuardar.setOnClickListener(this);
        btnExternoLeer.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnPreferencesGuardar:
                guardarPreferences();
                break;
            case R.id.btnPreferencesLeer:
                leerPreferences();
                break;
            case R.id.btnInternoGuardar:
                guardarInterno();
                break;
            case R.id.btnInternoLeer:
                leerInterno();
                break;
            case R.id.btnExternoGuardar:
                guardarExterno();
                break;
            case R.id.btnExternoLeer:
                leerExterno();
                break;
        }
    }

    public void guardarPreferences() {
        String valor1 = etPreferencesUno.getText().toString();
        String valor2 = etPreferencesDos.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("valor1", valor1);
        editor.putString("valor2", valor2);
        editor.commit();

        etPreferencesUno.setText("");
        etPreferencesDos.setText("");
    }

    public void leerPreferences() {
        String valor1 = sharedPreferences.getString("valor1", "");
        String valor2 = sharedPreferences.getString("valor2", "");
        etPreferencesUno.setText(valor1);
        etPreferencesDos.setText(valor2);
    }

    public void guardarInterno() {
        if (!etInterno.getText().toString().equals("")) {
            try {
                OutputStreamWriter output = new OutputStreamWriter(
                        openFileOutput(nombreArchivoInterno, Context.MODE_PRIVATE));
                output.write(etInterno.getText().toString());
                output.close();
                etInterno.setText("");
            } catch (Exception e) {
                System.out.println("Error: "+e.getMessage());
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Debe ingresar datos para guardar.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void leerInterno() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(openFileInput(nombreArchivoInterno)));
            String texto = br.readLine();
            br.close();
            etInterno.setText(texto);
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void guardarExterno() {
        if (!etExterno.getText().toString().equals("")) {
            boolean sdDisponible = false;
            boolean sdAccesoEscritura = false;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)){
                sdDisponible = true;
                sdAccesoEscritura = true;
            }else {
                if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                    sdDisponible = true;
                    sdAccesoEscritura = false;
                } else {
                    sdDisponible = false;
                    sdAccesoEscritura = false;
                }
            }
            if (sdDisponible && sdAccesoEscritura) {
                try {
                    File dir = new File(Environment.getExternalStorageDirectory()+"/StudyJam/");
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, nombreArchivoExterno);
                    try {
                        OutputStreamWriter osw = new OutputStreamWriter(
                                new FileOutputStream(file));
                        osw.write(etExterno.getText().toString());
                        osw.close();
                        etExterno.setText("");
                    }catch (Exception e) {
                        System.out.println("Error: "+e.getMessage());
                    }

                } catch (Exception e) {
                    System.out.println("Error: "+e.getMessage());
                }
            } else {
                System.out.println("No se puede escribir en su memoria");
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Debe ingresar datos para guardar",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void leerExterno() {
        try {
            File file = Environment.getExternalStorageDirectory();
            File f = new File(file.getAbsolutePath(),
                    "/StudyJam/"+nombreArchivoExterno);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(f)));
            String texto = br.readLine();
            br.close();
            etExterno.setText(texto);
        }catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }

    public void verificaPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            } else {
                // Continuar con el codigo
            }
        } else {
            // Continuar con el codigo
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permiso", "Concedido");
                } else {
                    Log.e("Permiso", "Denegado");
                }
                return;
            }
        }
    }

    public void initUI(){
        etPreferencesUno = (EditText) findViewById(R.id.etPreferencesUno);
        etPreferencesDos = (EditText) findViewById(R.id.etPreferencesDos);
        etInterno = (EditText) findViewById(R.id.etInterno);
        etExterno = (EditText) findViewById(R.id.etExterno);

        btnPreferencesGuardar = (Button) findViewById(R.id.btnPreferencesGuardar);
        btnPreferencesLeer = (Button) findViewById(R.id.btnPreferencesLeer);
        btnInternoGuardar = (Button) findViewById(R.id.btnInternoGuardar);
        btnInternoLeer = (Button) findViewById(R.id.btnInternoLeer);
        btnExternoGuardar = (Button) findViewById(R.id.btnExternoGuardar);
        btnExternoLeer = (Button) findViewById(R.id.btnExternoLeer);
    }
}
