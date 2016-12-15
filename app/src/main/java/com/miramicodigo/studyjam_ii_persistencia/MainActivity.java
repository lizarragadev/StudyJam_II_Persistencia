package com.miramicodigo.studyjam_ii_persistencia;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
