package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONArray;

import Objetos.Usuario;
import mx.edu.itlp.proyectomovil.validaciones.cifrarContrasena;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class Login extends AppCompatActivity {

    private Button registar;
    private Button loginV, loginC;
    private TextView lblRegistrar;
    private EditText txtCorreo, txtPassword;

    FirebaseInstanceIDService firebaseInstanceIDService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final String token = FirebaseInstanceId.getInstance().getToken();

        loginV = (Button)findViewById(R.id.btnLogear);
        registar = (Button) findViewById(R.id.btnRegistro);
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtContraseña);
        loginV.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg){
                final String Correo = txtCorreo.getText().toString();
                final String Contrasena = txtPassword.getText().toString();
                ClienteWebService.login(Correo, cifrarContrasena.encrypt(Contrasena,"qwdsdrtyfdesxcfr"), new ListenerWebService() {
                    @Override
                    public void onResultado(Object resultado) {
                        if(resultado.equals("Contraseña o usuario incorrecto")){
                            Toast.makeText(Login.this,resultado.toString(),Toast.LENGTH_SHORT).show();

                        }else {
                            String json = (String)resultado;
                            Gson gson = new Gson();
                            Usuario usuario= gson.fromJson((String)resultado, Usuario.class);
                            if (usuario.getRol().equals("cliente")) {
                                //abrir pantalla cliente
                                SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("rol", usuario.getRol());
                                //editor.putString("correoCliente", txtCorreo.getText().toString());
                                editor.putInt("idCli", usuario.getId());
                                editor.commit();

                                Intent intent = new Intent(getApplicationContext(), editarPerfil.class);
                                startActivity(intent);

                            } else if (usuario.getRol().equals("vendedor")) {
                                //abrir pantalla principal vendedor
                                SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                                        Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("rol", usuario.getRol());
                                //editor.putString("correoVendedor", txtCorreo.getText().toString());
                                editor.putInt("idVen", usuario.getId());
                                editor.commit();
                                ClienteWebService.actualizarTokenVendedor(usuario.getId(),token,new ListenerWebService() {
                                    @Override
                                    public void onResultado(Object resultado) {
                                        if(resultado!=null){
                                            if(resultado.equals("Token actulizado")){

                                                //Intent intent = new Intent(getApplicationContext(), editarPerfil.class);
                                                Intent intent = new Intent(getApplicationContext(), editarPerfil.class);
                                                startActivity(intent);
                                            }else if(resultado.equals("Error al actualizar el token")){
                                                final Toast toast = Toast.makeText(Login.this, "Error al iniciar sesión, vuelve a intentarlo", Toast.LENGTH_LONG);
                                                toast.show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onError(String error) {

                                    }
                                });
                            }
                        }

                    }

                    @Override
                    public void onError(String error) {
                        final Toast toast = Toast.makeText(Login.this, "Error al ejecutar", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }

        });


        registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });
    }


}
