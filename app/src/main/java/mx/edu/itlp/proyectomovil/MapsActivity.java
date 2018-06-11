package mx.edu.itlp.proyectomovil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    Button btnRutear;
    EditText txtDestino;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private LocationManager locationManager;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5;
    String origen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnRutear = (Button) findViewById(R.id.btnRutear);
        txtDestino = (EditText) findViewById(R.id.txtDestino);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            }
            else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
        else
        {
            origen = usarGPS();
        }
        btnRutear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sendRequest(origen);
            }
        });
    }


    public String usarGPS() {
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return onLocationChanged(locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER));
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, this);
    }


    public String onLocationChanged(Location location) {
        String str="";
        try {
            str = location.getLatitude() + ", " + location.getLongitude();
        }catch (Exception e){}
        Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
        return str;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    usarGPS();
                } else {

                    //txtLocalizacion = (TextView) findViewById(R.id.txtLocalizacion);
                    //txtLocalizacion.setText("NO SE HA DADO PERMISO PARA USAR EL GPS");
                }
                return;
            }
        }
    }


    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned off ", Toast.LENGTH_LONG).show();
    }


    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "Gps turned on ", Toast.LENGTH_LONG).show();
    }

    private void sendRequest(String origen){
        final String[] destino = {""};
        SharedPreferences preferences = getSharedPreferences("MisPreferencias",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        ClienteWebService.ubicacionPedido(preferences.getInt("idVen", 0), new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                String json = (String)resultado;
                Gson gson = new Gson();
                String[] coodenadas= gson.fromJson((String)resultado, String[].class);
                destino[0] = coodenadas[0]+","+coodenadas[1];

            }

            @Override
            public void onError(String error) {

            }
        });

        //String destino = txtDestino.getText().toString();
        if(origen.isEmpty()){
            Toast.makeText(this, "Por favor ingrese el origen", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destino[0].isEmpty()){
            Toast.makeText(this, "Por favor ingrese el destino", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            new DirectionFinder(this, origen, destino[0]).execute();
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes){
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        for(Route route : routes){
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.lblDistancia)).setText(route.distance.text);
            ((TextView) findViewById(R.id.lblDuracion)).setText(route.duration.text);
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .title("Punto de partida")
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title("Punto de destino")
                    .position(route.endLocation)));
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).color(Color.BLUE).width(10);
            polylineOptions.add(route.startLocation);
            for(int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));
            polylineOptions.add(route.endLocation);
            polylinePaths.add(mMap.addPolyline(polylineOptions));

        }
    }
    @Override
    public void onDirectionFinderStart(){
        progressDialog = ProgressDialog.show(this, "Espere por favor", "Obteniendo ruta", true);
        if(originMarkers != null){
            for(Marker marker : originMarkers){
                marker.remove();
            }
        }
        if(destinationMarkers != null){
            for(Marker marker : destinationMarkers) {
                marker.remove();
            }
        }
        if(polylinePaths != null){
            for(Polyline polyline : polylinePaths){
                polyline.remove();
            }
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Tipo de mapa
        //UiSettings uiSettings = mMap.getUiSettings();
        //uiSettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        String[] latlong =  usarGPS().split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        LatLng origen = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(origen).title("Tecnológico Nacional de México").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        float zoomlevel = 17.5f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(origen, zoomlevel));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        /*LatLng destino = new LatLng(24.119240, -110.308263 );
        mMap.addPolyline(new PolylineOptions().add(
                origen,
                new LatLng(24.119666, -110.310065),
                destino
            )
            .width(10)
            .color(Color.RED)
        );*/
    }



}
