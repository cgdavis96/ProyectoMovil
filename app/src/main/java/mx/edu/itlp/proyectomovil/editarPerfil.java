package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import Objetos.Comprador;
import Objetos.Vendedor;
import mx.edu.itlp.proyectomovil.validaciones.cifrarContrasena;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class editarPerfil extends AppCompatActivity {
    Boolean existe;
    EditText txtCorreo, txtContraseña, txtConfirmaContraseña, txtNombre, txtApellidoP, txtApellidom, txtTelefono;
    RadioButton rbtnVendedor, rbtnComprador;
    Button btnRegistrar;
    int rol,idUsuario;
    String roltexto;
    Intent intent;

    RadioGroup radioGroup1;
    RadioButton deals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        roltexto = preferences.getString("rol","");

        radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        //deals = (RadioButton)findViewById(R.id.deals);

        RadioButton radioButton =(RadioButton)findViewById(R.id.verProductos);
        RadioButton radioButton1 =(RadioButton)findViewById(R.id.nProducto);
        if(roltexto.equals("cliente")) {
            radioButton.setText("Vendedores");
            radioButton1.setVisibility(View.GONE);
        }
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

                        if(roltexto.equals("vendedor")) {
                            in = new Intent(getBaseContext(),Producto.class);
                            startActivity(in);
                            finish();
                            overridePendingTransition(0, 0);
                        }
                        break;
                    default:
                        break;
                }
            }
        });





        //deals = (RadioButton)findViewById(R.id.deals);

        existe=false;

        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtContraseña = (EditText) findViewById(R.id.txtContraseña);
        txtConfirmaContraseña = (EditText) findViewById(R.id.txtConfirmaContraseña);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellidoP = (EditText) findViewById(R.id.txtApellidoPaterno);
        txtApellidom = (EditText) findViewById(R.id.txtApellidoMaterno);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        rbtnVendedor = (RadioButton) findViewById(R.id.rbtnVendedor);
        rbtnComprador = (RadioButton) findViewById(R.id.rbtnCliente);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        intent = new Intent();



        if(roltexto.equals("cliente")) {
            idUsuario = preferences.getInt("idCli", 0);
            rol =2;
            ClienteWebService.obtenerDCliente(idUsuario, new ListenerWebService() {
                @Override
                public void onResultado(Object resultado) {
                    if(resultado!=null){
                        String json = (String)resultado;
                        Gson gson = new Gson();
                        Comprador comprador[]= gson.fromJson((String)resultado, Comprador[].class);
                        txtCorreo.setText(comprador[0].getCorreo());
                        txtNombre.setText(comprador[0].getNombre());
                        txtApellidom.setText(comprador[0].getApellidoM());
                        txtApellidoP.setText(comprador[0].getApellidoP());
                        txtTelefono.setText(comprador[0].getTelefono());
                        acciones();
                    }
                    else{
                        Toast.makeText(editarPerfil.this, "Error al cargar su información", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
        if(roltexto.equals("vendedor")){
            idUsuario = preferences.getInt("idVen", 0);
            rol=1;
            ClienteWebService.obtenerDVendedor(idUsuario, new ListenerWebService() {
                @Override
                public void onResultado(Object resultado) {
                    if(resultado!=null){
                        String json = (String)resultado;
                        Gson gson = new Gson();
                        Vendedor vendedor[]= gson.fromJson((String)resultado, Vendedor[].class);
                        txtCorreo.setText(vendedor[0].getCorreo());
                        txtNombre.setText(vendedor[0].getNombre());
                        txtApellidom.setText(vendedor[0].getApellidoM());
                        txtApellidoP.setText(vendedor[0].getApellidoP());
                        txtTelefono.setText(vendedor[0].getNumero());
                        acciones();
                    }
                    else{
                        Toast.makeText(editarPerfil.this, "Error al cargar su información", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onError(String error) {

                }
            });
        }
    }

    public void acciones(){


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String correo = txtCorreo.getText().toString();
                final String Contraseña = txtContraseña.getText().toString();
                final String ConfirmarContraseña = txtConfirmaContraseña.getText().toString();
                final String Nombre = txtNombre.getText().toString();
                final String ApellidoP = txtApellidoP.getText().toString();
                final String ApellidoM = txtApellidom.getText().toString();
                final String Telefono = txtTelefono.getText().toString();
                if (Contraseña.equals(ConfirmarContraseña) && !Contraseña.equals("")) {
                    ClienteWebService.actualizarUsuario(idUsuario, Nombre, ApellidoP, ApellidoM, Telefono, rol, correo,  cifrarContrasena.encrypt(Contraseña, "qwdsdrtyfdesxcfr"), new ListenerWebService() {
                        @Override
                        public void onResultado(Object resultado) {
                            if(resultado!=null){
                                if (resultado.equals("Datos actualizos exitosamente")) {

                                    final Toast toast = Toast.makeText(editarPerfil.this, resultado.toString(), Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (resultado.equals("Error al actualizar su información")) {
                                    final Toast toast = Toast.makeText(editarPerfil.this, resultado.toString(), Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (resultado.equals("El correo ya se encuentra registrado")) {
                                    final Toast toast = Toast.makeText(editarPerfil.this, resultado.toString(), Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (resultado.equals("false")) {
                                    final Toast toast = Toast.makeText(editarPerfil.this, "No tiene permiso para acceder a los servicios", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }
                        }

                        @Override
                        public void onError(String error) {

                        }
                    });
                }
                else{
                    Toast.makeText(editarPerfil.this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
