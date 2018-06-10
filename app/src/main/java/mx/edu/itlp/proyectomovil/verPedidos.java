package mx.edu.itlp.proyectomovil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class verPedidos extends AppCompatActivity implements ListenerWebService {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pedidos);
        ListView lvCursos = findViewById(R.id.lvPed);
        lvCursos.setEmptyView(findViewById(R.id.barraCargando));
    }

    @Override
    public void onResultado(Object resultado) {

    }

    @Override
    public void onError(String error) {

    }
}
