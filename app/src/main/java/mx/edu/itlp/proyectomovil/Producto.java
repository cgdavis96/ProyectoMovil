package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class Producto extends AppCompatActivity {

    private TextView mTextMessage;
    EditText txtNomPro, txtPrePro, txtDesPro, txtCant;

    Button btnGuarPro, btnAtrasPro;
    //Insertar insertar;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txtNomPro = (EditText) findViewById(R.id.nomPro);
        txtPrePro = (EditText) findViewById(R.id.prePro);
        txtDesPro = (EditText) findViewById(R.id.desPro);
        txtCant = (EditText) findViewById(R.id.cantPro);
        btnGuarPro = (Button) findViewById(R.id.btnGuarPro);
        btnAtrasPro = (Button) findViewById(R.id.btnAtrasPro);
        btnGuarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = txtNomPro.getText().toString();
                final String precio = txtPrePro.getText().toString();
                final String descripcion = txtDesPro.getText().toString();
                final int stock = Integer.valueOf(txtCant.getText().toString());
                SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();


                final OProducto oProducto = new OProducto(preferences.getInt("idVen",0),nombre,precio,stock,descripcion);
                ClienteWebService.registrarProducto(oProducto, new ListenerWebService() {
                    @Override
                    public void onResultado(Object resultado) {
                        if(resultado.equals("Producto registrado exitosamente")){
                            Intent intent = new Intent(getApplicationContext(),Vendedores.class);
                            startActivity(intent);
                        }else if(resultado.equals("Error al registrar el producto")){
                            final Toast toast = Toast.makeText(Producto.this, resultado.toString(), Toast.LENGTH_LONG);
                            toast.show();
                        }else if (resultado.equals("false")) {
                            final Toast toast = Toast.makeText(Producto.this, "No tiene permiso para acceder a los servicios", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });

        btnAtrasPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                Toast.makeText(Producto.this,preferences.getString("Uid",""),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
