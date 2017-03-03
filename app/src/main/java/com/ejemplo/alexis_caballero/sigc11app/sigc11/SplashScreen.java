package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.Usuarios;

public class SplashScreen extends AppCompatActivity {

    protected boolean active = true;
    protected int splashTime = 2500;
    public RelativeLayout rLn;

    SharedPreferences ConsultaDatosLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        rLn = (RelativeLayout) findViewById(R.id.activity_splash_screen);

        //Se asigna un segmento  de la interfaz  a la variable
        ConsultaDatosLogin = getSharedPreferences("DatosDelLogin", Context.MODE_PRIVATE);

        Thread hiloSplash = new Thread(){
            @Override
            public void run() {
                try {

                    int waited = 0;
                    while (active && (waited < splashTime)) {
                        sleep(100);
                        if (active) {
                            waited += 100;
                        }
                    }

                } catch (InterruptedException e) {

                } finally {
                    openApp();
                }
            }
        };

        hiloSplash.start();
    }


    public void openApp(){
        finish();
        String user = ConsultaDatosLogin.getString("USUARIO","");
        String pass = ConsultaDatosLogin.getString("PASSWORD","");

        if (user.equals("") && pass.equals("")){
            startActivity(new Intent(this, Login.class));

        }else {
            Usuarios myUser = new Usuarios();

            myUser.setName(user);
            myUser.setPass(pass);

            startActivity(new Intent(this,Menu.class).putExtra("myObject",myUser));
        }
    }
}
