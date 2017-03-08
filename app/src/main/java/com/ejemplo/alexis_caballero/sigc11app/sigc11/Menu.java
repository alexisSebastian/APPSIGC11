package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DaoEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.items.Usuarios;

public class Menu extends AppCompatActivity {

    public ItemEmpresas myEmpresa; //quitar despues

    public Usuarios titleUser;
    String myUser;
    TextView lblTitle;

    EditText edtNewEmp;

    private Button btnE, btnM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        Usuarios users = (Usuarios)getIntent().getSerializableExtra("myObject");
        myUser = getIntent().getStringExtra("KeyUser");

        //titleUser = (Usuarios)getIntent().getExtras().getSerializable("myObject");
        lblTitle = (TextView)findViewById(R.id.menuTitle);
        lblTitle.setText(getString(R.string.titleBienvenido));

        btnE = (Button)findViewById(R.id.btnEstadistica);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, Concesionarios.class);
                startActivity(i);
            }
        });

        btnM = (Button)findViewById(R.id.btnMapa);
        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, MapsActivity.class);
                startActivity(i);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_inicio, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ContextMenuLogout:

                Intent i = new Intent(Menu.this, Login.class);
                SharedPreferences preferences = getSharedPreferences("DatosDelLogin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                startActivity(i);
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
