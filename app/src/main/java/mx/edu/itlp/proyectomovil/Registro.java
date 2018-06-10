package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;


import Objetos.Comprador;
import Objetos.Vendedor;
import mx.edu.itlp.proyectomovil.validaciones.cifrarContrasena;

import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class Registro extends AppCompatActivity {

    Boolean existe;
    EditText txtCorreo, txtContraseña, txtConfirmaContraseña, txtNombre, txtApellidoP, txtApellidom, txtTelefono;
    RadioButton rbtnVendedor, rbtnComprador;
    Button btnRegistrar;
    int rol;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
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


            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String correo = txtCorreo.getText().toString();
                    final String Contraseña = txtContraseña.getText().toString();
                    final String ConfirmarContraseña = txtConfirmaContraseña.getText().toString();
                    final String Nombre = txtNombre.getText().toString();
                    final String ApellidoP = txtApellidoP.getText().toString();
                    final String ApellidoM = txtApellidom.getText().toString();
                    final String Telefono = txtTelefono.getText().toString();

                    if (Contraseña.equals(ConfirmarContraseña)) {

                        if (rbtnComprador.isChecked()) {
                            rol = 2;
                        } else if (rbtnVendedor.isChecked()) {
                            rol = 1;
                        }
                        ClienteWebService.registrar(Nombre, ApellidoP, ApellidoM, Telefono, rol, correo, cifrarContrasena.encrypt(Contraseña, "qwdsdrtyfdesxcfr"), new ListenerWebService() {
                            @Override
                            public void onResultado(Object resultado) {
                                if (resultado.equals("OK")) {
                                    SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                                            Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();

                                    final Toast toast = Toast.makeText(Registro.this, "Usuario registrado con exito", Toast.LENGTH_LONG);
                                    toast.show();
                                    editor.putString("correoLogin", correo);
                                    editor.commit();
                                } else if (resultado.equals("Error")) {
                                    final Toast toast = Toast.makeText(Registro.this, "Error al registrar usuario", Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (resultado.equals("El correo ya se encuentra registrado")) {
                                    final Toast toast = Toast.makeText(Registro.this, resultado.toString(), Toast.LENGTH_LONG);
                                    toast.show();
                                } else if (resultado.equals("false")) {
                                    final Toast toast = Toast.makeText(Registro.this, "No tiene permiso para acceder a los servicios", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            }

                            @Override
                            public void onError(String error) {
                                final Toast toast = Toast.makeText(Registro.this, "Error al ejecutar", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        });


                    } else
                        Toast.makeText(Registro.this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();

                }
            });



    }
}
