package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.adaptadores.productosVendedorAdaptador;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class list_pro_ven extends AppCompatActivity {

    private OProducto[] oProductos;
    private productosVendedorAdaptador productosVendedorAdaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pro_ven);

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
