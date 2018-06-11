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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class Producto extends AppCompatActivity {

    private TextView mTextMessage;
    EditText txtNomPro, txtPrePro, txtDesPro, txtCant;
    RadioGroup radioGroup1;
    Button btnGuarPro, btnAtrasPro;
    //Insertar insertar;
    String roltexto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        roltexto = preferences.getString("rol","");

        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        //deals = (RadioButton)findViewById(R.id.deals);
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                Intent in;
                switch (checkedId)
                {
                    case R.id.miPerfil:
                        in=new Intent(getBaseContext(),editarPerfil.class);
                        startActivity(in);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    case R.id.verProductos:
                        RadioButton radioButton =(RadioButton)findViewById(R.id.verProductos);
                        if(roltexto.equals("vendedor")) {
                            in = new Intent(getBaseContext(), list_pro_ven.class);
                            startActivity(in);
                            finish();
                        }else if(roltexto.equals("cliente")){
                            in = new Intent(getBaseContext(), Vendedores.class);
                            startActivity(in);
                            finish();
                        }
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.nProducto:
                        RadioButton radioButton1 =(RadioButton)findViewById(R.id.verProductos);
                        if(roltexto.equals("vendedor")) {
                            in = new Intent(getBaseContext(),Producto.class);
                            startActivity(in);
                            finish();
                            overridePendingTransition(0, 0);
                        }else if(roltexto.equals("cliente")){
                            radioButton1.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
            }
        });


        mTextMessage = (TextView) findViewById(R.id.message);

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
                            final Toast toast = Toast.makeText(Producto.this, resultado.toString(), Toast.LENGTH_LONG);
                            toast.show();
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
