package Objetos;

import com.google.gson.Gson;

import mx.edu.itlp.proyectomovil.webservice.ClienteWebService;
import mx.edu.itlp.proyectomovil.webservice.ListenerWebService;

/**
 * Created by Jhosef Davis on 03/06/2018.
 */

public class OProducto {

    private String idPro;
    private int idVen;
    private String nombre;
    private String precio;
    private int stock;
    private String descripcion;




    public OProducto(String idPro, int stock) {
        this.idPro = idPro;
        this.stock = stock;
    }

    public OProducto() {
    }

    public OProducto(int idVen, String nombre, String precio, int stock, String descripcion) {
        this.idVen = idVen;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public OProducto(String idPro, String nombre, String precio, int stock, String descripcion) {
        this.idPro = idPro;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public OProducto(String idPro) {
        this.idPro = idPro;
    }

    public String getIdPro() {
        return idPro;
    }

    public void setIdPro(String idPro) {
        this.idPro = idPro;
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

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public static void tablaProductos(int idVen, final ListenerWebService listenerActivity)
    {
        ListenerWebService listener = new ListenerWebService() {
            @Override
            public void onResultado(Object resultado) {
                String json = (String)resultado;
                Gson gson = new Gson();
                OProducto[] oProductos= gson.fromJson((String)resultado, OProducto[].class);
                listenerActivity.onResultado(oProductos);
            }

            @Override
            public void onError(String error) {
                listenerActivity.onResultado(null);
            }
        };
        ClienteWebService.obtenerProductos(idVen,listener);
    }
}
