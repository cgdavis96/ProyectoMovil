package mx.edu.itlp.proyectomovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.validaciones.MinMaxFilter;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class editarProducto extends AppCompatActivity {
    EditText txtNomPro, txtPrePro, txtDesPro, txtCant;
    Button btnGuarPro, btnAtrasPro, btnBoPro;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(editarProducto.this,list_pro_ven.class);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        txtNomPro = (EditText) findViewById(R.id.nomPro);
        txtPrePro = (EditText) findViewById(R.id.prePro);
        txtDesPro = (EditText) findViewById(R.id.desPro);
        txtCant = (EditText) findViewById(R.id.cantPro);
        btnGuarPro = (Button) findViewById(R.id.btnGuarPro);
        btnAtrasPro = (Button) findViewById(R.id.btnAtrasPro);
        btnBoPro = (Button) findViewById(R.id.btnBoPro);
        txtCant.setFilters(new InputFilter[]{ new MinMaxFilter(0, 99)});
        ClienteWebService.obtenerDProducto(Integer.valueOf(getIntent().getExtras().getString("idPro")), new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                if(resultado!=null){
                    String json = (String)resultado;
                    Gson gson = new Gson();
                    OProducto[] oProducto= gson.fromJson((String)resultado, OProducto[].class);
                    txtNomPro.setText(oProducto[0].getNombre());
                    txtPrePro.setText(oProducto[0].getPrecio());
                    txtDesPro.setText(oProducto[0].getDescripcion());
                    txtCant.setText(String.valueOf(oProducto[0].getStock()));
                    acciones();

                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void acciones(){
        btnGuarPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nombre = txtNomPro.getText().toString();
                final String precio = txtPrePro.getText().toString();
                final String descripcion = txtDesPro.getText().toString();
                final int stock = Integer.valueOf(txtCant.getText().toString());
                OProducto oProducto = new OProducto(getIntent().getExtras().getString("idPro"),nombre,precio,stock,descripcion);
                ClienteWebService.actualizarProducto(oProducto, new ListenerWebService() {
                    @Override
                    public void onResultado(Object resultado) {
                        if(resultado!=null){
                            if(resultado.equals("Producto actualizado exitosamente")){
                                Toast.makeText(editarProducto.this, resultado.toString(), Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(editarProducto.this, resultado.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });
        btnBoPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClienteWebService.eliminarProducto(getIntent().getExtras().getString("idPro"), new ListenerWebService() {
                    @Override
                    public void onResultado(Object resultado) {
                        if(resultado!=null){
                            if(resultado.equals("Producto eliminado exitosamente")){
                                Toast.makeText(editarProducto.this, resultado.toString(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(editarProducto.this,list_pro_ven.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(editarProducto.this, resultado.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });


    }
}
