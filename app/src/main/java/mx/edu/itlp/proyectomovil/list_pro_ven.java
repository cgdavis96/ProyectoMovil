package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioGroup;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.adaptadores.productosVendedorAdaptador;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class list_pro_ven extends AppCompatActivity {

    RadioGroup radioGroup1;
    private OProducto[] oProductos;
    private productosVendedorAdaptador productosVendedorAdaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pro_ven);
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

                        in = new Intent(getBaseContext(), list_pro_ven.class);
                        startActivity(in);
                        finish();
                        overridePendingTransition(0, 0);

                        break;
                    case R.id.nProducto:

                        in = new Intent(getBaseContext(),Producto.class);
                        startActivity(in);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                    default:
                        break;
                }
            }
        });
        SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        OProducto.tablaProductos(preferences.getInt("idVen",0), new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                oProductos = (OProducto[]) resultado;
                productosVendedorAdaptador = new productosVendedorAdaptador(oProductos, getApplicationContext());
                ListView lvPro= (ListView) findViewById(R.id.lvProVende);
                lvPro.setAdapter(productosVendedorAdaptador);
                lvPro.invalidate();

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
