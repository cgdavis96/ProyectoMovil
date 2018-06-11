package mx.edu.itlp.proyectomovil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Nullable;

import Objetos.OProducto;

import Objetos.Vendedor;
import mx.edu.itlp.proyectomovil.adaptadores.productosAdaptador;
import mx.edu.itlp.proyectomovil.adaptadores.vendedoresAdaptador;
import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;
import mx.edu.itlp.proyectomovil.adaptadores.productosAdaptador.ViewHolder;

public class Productos extends AppCompatActivity implements LocationListener {

    private TextView mTextMessage;
    private productosAdaptador productosAdaptador;
    Intent intent;
    Button btnPP;
    private OProducto[] oProductos;
    private LocationManager locationManager;
    private TextView txtTotal;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5;
    private String[] str;
    private ViewHolder[] viewHolders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            usarGPS();
        }

        intent = new Intent(this, Producto.class);

        OProducto.tablaProductos(getSharedPreferences("MisPreferencias", MODE_PRIVATE).getInt("idVen", 0), new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                oProductos = (OProducto[]) resultado;
                productosAdaptador = new productosAdaptador(oProductos, getApplicationContext(), txtTotal);
                ListView lvPro = (ListView) findViewById(R.id.lvPro);
                lvPro.setAdapter(productosAdaptador);
                lvPro.invalidate();

            }

            @Override
            public void onError(String error) {

            }
        });
        btnPP = (Button) findViewById(R.id.btnCP);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        usarGPS();
        btnPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolders = productosAdaptador.getViewHolders();

                SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                String fecha = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
                String Total = String.valueOf(productosAdaptador.getTotalFinal());
                int IdCliente = preferences.getInt("idCli", 0);
                int idVend = getSharedPreferences("MisPreferencias", MODE_PRIVATE).getInt("idVen", 0);
                ArrayList<ViewHolder> t = new ArrayList<ViewHolder>();
                for (int i = 0; i < viewHolders.length; i++) {
                    if (viewHolders[i] != null)
                        if (viewHolders[i].getCantidad() > 0)
                            t.add(viewHolders[i]);
                }
                Object[] nueva = t.toArray();
                String JSON = new Gson().toJson(nueva);
                ClienteWebService.registrarPedido(Total, idVend, IdCliente, fecha, hora, str[0], str[1], JSON, new ListenerWebService() {
                    @Override
                    public void onResultado(Object resultado) {
                        String temp = (String)resultado;
                        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), "Clicked: ", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }


    public void usarGPS() {
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        onLocationChanged(locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER));
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                3000, 10, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        str = new String[2];
        try {
            str[0] = String.valueOf(location.getLatitude());
            str[1] = String.valueOf(location.getLongitude());
        } catch (Exception e) {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    usarGPS();
                } else {

                    Toast.makeText(getBaseContext(), "Es necesario dar permisos al GPS ", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps apagado ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps encendido ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

}
