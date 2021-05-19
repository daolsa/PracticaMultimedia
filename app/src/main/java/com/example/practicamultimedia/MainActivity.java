package com.example.practicamultimedia;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    FragmentPagerAdapter fragmentPagerAdapter;
    ViewPager viewPager;
    // En esta implementación no haría falta un requestPermissionLauncher.
    // Sin embargo, se añade para ver cómo se implementaría correctamente.
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),MainActivity.this);


        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(4);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //tabLayout.getTabAt(0).setIcon(R.mipmap.ic_launcher);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    // El permiso ha sido concedido
                    // Llamamos al método que utiliza dichos permisos
                    Log.d("Events","Estamos en onActivityResult true");
                }

                else{
                    // Explicar al usuario que la acción a realizar no está disponible
                    // porque requiere de un permiso que ha denegado. Hay que respetar la
                    // decisión del usuario, así que no le spamees hasta que lo acepte.

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle("Permiso denegado correctamente...");
                    alertDialogBuilder.setMessage("Sin este permiso no se podrá escuchar música ni ver videos");
                    alertDialogBuilder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        // dialog -> dialogo actual
                        // which -> botón pulsado: DialogInterface.BUTTON_POSITIVE
                        public void onClick(DialogInterface dialog, int which) {

                            Toast.makeText(MainActivity.this,"Descarga cancelada...",Toast.LENGTH_SHORT).show();

                        }
                    });

                    alertDialogBuilder.create().show();
                    Log.d("Events","Estamos en onActivityResult false");

                }
            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            // llamamos al método que utiliza este permiso


        }else{

            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

        }


    }

}