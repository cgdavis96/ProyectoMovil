package Objetos;

import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;

import java.util.List;

import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

/**
 * Created by Jhosef Davis on 02/06/2018.
 */

public class Vendedor {

    private int idVen;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String numero;
    private String rol;
    private String correo;
    private String contraseña;
    private String latitud;
    private String longitud;

    private String estado;

    public Vendedor(String correo) {
        this.correo = correo;
    }

    public Vendedor(){

    }

    public Vendedor(int idVen, String nombre, String apellidoP, String apellidoM, String numero, String correo) {
        this.idVen = idVen;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.numero = numero;
        this.correo = correo;
    }

    public Vendedor(int idVen, String rol) {
        this.idVen = idVen;
        this.rol = rol;
    }

    public int getIdVen() {
        return idVen;
    }

    public void setIdVen(int idVen) {
        this.idVen = idVen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public static void tablaVendedores(final ListenerWebService listenerActivity)
    {
        ListenerWebService listener = new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                String json = (String)resultado;
                Gson gson = new Gson();
                Vendedor[] vendedors= gson.fromJson((String)resultado, Vendedor[].class);
                listenerActivity.onResultado(vendedors);
            }

            @Override
            public void onError(String error) {
                listenerActivity.onResultado(null);
            }
        };
        ClienteWebService.obtenerVendedores(listener);
    }
}
