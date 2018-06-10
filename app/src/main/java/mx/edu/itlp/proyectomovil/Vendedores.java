package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


import Objetos.Vendedor;
import mx.edu.itlp.proyectomovil.adaptadores.vendedoresAdaptador;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;


public class Vendedores extends AppCompatActivity implements ListenerWebService{

    private TextView mTextMessage;
    private Vendedor[] listaVendedores;
    private vendedoresAdaptador vendedoresAdaptador;
    private ListView listView;

    private Vendedor[] vendedores;
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
        setContentView(R.layout.activity_vendedores);
        Vendedor.tablaVendedores(this);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

    @Override
    public void onResultado(Object resultado) {
        vendedores = (Vendedor[]) resultado;
        vendedoresAdaptador adaptador = new vendedoresAdaptador(vendedores, this);
        listView = (ListView) findViewById(R.id.lvVendedores);
        listView.setAdapter(adaptador);
        listView.invalidate();
    }

    @Override
    public void onError(String error) {

    }
}
