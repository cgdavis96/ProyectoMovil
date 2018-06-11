package mx.edu.itlp.proyectomovil.webservice;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import Objetos.OProducto;
import mx.edu.itlp.proyectomovil.adaptadores.productosAdaptador;

/**
 * Created by Jhosef Davis on 26/03/2018.
 */

public class ClienteWebService {
    private static final String PASSWS = "vendedores2018";


    public final static String IP_SERVIDOR = "vendedoresitlp.000webhostapp.com";
    public final static String PUERTO_SERVIDOR = "80";
    public final static String PROTOCOLO_SERVIDOR = "http";
    public final static String DIRECCION_SERVIDOR = PROTOCOLO_SERVIDOR + "://" + IP_SERVIDOR + ":" + PUERTO_SERVIDOR +"/cuesWeb/";

    public static final String OPERACION_login= "login";
    public static final String OPERACION_registrar= "registrar";
    public static final String OPERACION_actualizarUsuario= "actualizarUsuario";
    public static final String OPERACION_opDVendedor= "obtenerDVendedor";
    public static final String OPERACION_opDCliente= "obtenerDCliente";
    public static final String OPERACION_opDProducto= "obtenerDProducto";
    public static final String OPERACION_obtenerVendedores= "obtenerVendedores";
    public static final String OPERACION_registrarProducto= "registrarProducto";
    public static final String OPERACION_obtenerProductos= "obtenerProductos";
    public static final String OPERACION_registrarPedido= "registrarPedido";
    public static final String OPERACION_actualizarProducto= "actualizarProducto";
    public static final String OPERACION_eliminarProducto= "eliminarProducto";
    public static final String OPERACION_registrarProPed= "registrarProPed";
    public static final String OPERACION_actualizarTokenCliente= "actualizarTokenCliente";
    public static final String OPERACION_actualizarTokenVendedor= "actualizarTokenVendedor";
    public static final String OPERACION_notificacion= "notificacion";
    public static final String OPERACION_ubicacionPedido= "ubicacionPedido";
    public static final String NOMBRE_WEB_SERVICE = "serverCuesti.php";
    public static final String WSDL_TARGET_NAMESPACE = DIRECCION_SERVIDOR;
    public static final String DIRECCION_SOAP = WSDL_TARGET_NAMESPACE + "webservices/"+NOMBRE_WEB_SERVICE + "?WSDL";
    public static final String SOAP_ACTION = WSDL_TARGET_NAMESPACE ;


    public static void login(String correo, String contrasena, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[3];
        parametros[0] = parametro("correo", correo);
        parametros[1] = parametro("contrasena", contrasena);
        parametros[2] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_login,SOAP_ACTION+ OPERACION_login, parametros, listener);
    }

    public static void obtenerDCliente(int idCli, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idCli", idCli);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_opDCliente,SOAP_ACTION+ OPERACION_opDCliente, parametros, listener);
    }

    public static void obtenerDVendedor(int idVen, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idVen", idVen);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_opDVendedor,SOAP_ACTION+ OPERACION_opDVendedor, parametros, listener);
    }

    public static void obtenerDProducto(int idPro, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idPro", idPro);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_opDProducto,SOAP_ACTION+ OPERACION_opDProducto, parametros, listener);
    }



    public static void obtenerVendedores(final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[1];
        parametros[0] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_obtenerVendedores,SOAP_ACTION+ OPERACION_obtenerVendedores, parametros, listener);
    }

    public static void actualizarProducto(OProducto oProducto,final ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[6];
        parametros[0] = parametro("idPro", oProducto.getIdPro());
        parametros[1] = parametro("nombre", oProducto.getNombre());
        parametros[2] = parametro("precio", oProducto.getPrecio());
        parametros[3] = parametro("stock", oProducto.getStock());
        parametros[4] = parametro("descripcion", oProducto.getDescripcion());
        parametros[5] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_actualizarProducto,SOAP_ACTION+ OPERACION_actualizarProducto, parametros, listener);
    }


    public static void registrarProducto(OProducto oProducto, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[6];
        parametros[0] = parametro("idVen", oProducto.getIdVen());
        parametros[1] = parametro("nombre", oProducto.getNombre());
        parametros[2] = parametro("precio", oProducto.getPrecio());
        parametros[3] = parametro("stock", oProducto.getStock());
        parametros[4] = parametro("descripcion", oProducto.getDescripcion());
        parametros[5] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_registrarProducto,SOAP_ACTION+ OPERACION_registrarProducto, parametros, listener);
    }

    public static void obtenerProductos(int idVen, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idVen", idVen);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_obtenerProductos,SOAP_ACTION+ OPERACION_obtenerProductos, parametros, listener);
    }


    public static void eliminarProducto(String idPro, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idPro", idPro);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_eliminarProducto,SOAP_ACTION+ OPERACION_eliminarProducto, parametros, listener);
    }

    public static void registrar(String nombre, String apellidoP, String apellidoM, String numero, int rol, String correo, String contrasena, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[8];
        parametros[0] = parametro("nombre", nombre);
        parametros[1] = parametro("apellidoP", apellidoP);
        parametros[2] = parametro("apellidoM", apellidoM);
        parametros[3] = parametro("numero", numero);
        parametros[4] = parametro("rol", rol);
        parametros[5] = parametro("correo", correo);
        parametros[6] = parametro("contrasena", contrasena);
        parametros[7] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_registrar,SOAP_ACTION+OPERACION_registrar, parametros, listener);
    }

    public static void actualizarUsuario(int idUsuario, String nombre, String apellidoP, String apellidoM, String numero, int rol, String correo, String contrasena, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[9];
        parametros[0] = parametro("idUsuario", idUsuario);
        parametros[1] = parametro("nombre", nombre);
        parametros[2] = parametro("apellidoP", apellidoP);
        parametros[3] = parametro("apellidoM", apellidoM);
        parametros[4] = parametro("numero", numero);
        parametros[5] = parametro("rol", rol);
        parametros[6] = parametro("correo", correo);
        parametros[7] = parametro("contrasena", contrasena);
        parametros[8] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_actualizarUsuario,SOAP_ACTION+OPERACION_actualizarUsuario, parametros, listener);
    }


    public static void registrarPedido(String total, int idVen, int idCli, String fecha, String horaSolicitud, String latitud, String longitud, String Productos, final ListenerWebService listener) {
        PropertyInfo[] parametros = new PropertyInfo[9];
        parametros[0] = parametro("total", total);
        parametros[1] = parametro("idVen", idVen);
        parametros[2] = parametro("idCli", idCli);
        parametros[3] = parametro("fecha", fecha);
        parametros[4] = parametro("horaSolicitud", horaSolicitud);
        parametros[5] = parametro("latitud", latitud);
        parametros[6] = parametro("longitud", longitud);
        parametros[7] = parametro("productos", Productos);
        parametros[8] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_registrarPedido,SOAP_ACTION+OPERACION_registrarPedido, parametros, listener);
    }

    public static void registrarProPed(int idPro, int cantidad, String subtotal, ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[4];
        parametros[0] = parametro("idPro", idPro);
        parametros[1] = parametro("cantidad", cantidad);
        parametros[2] = parametro("subtotal", subtotal);
        parametros[3] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_registrarProPed,SOAP_ACTION+OPERACION_registrarProPed, parametros, listener);

    }

    public static void actualizarTokenCliente(int idCli, String token, ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idCli", idCli);
        parametros[1] = parametro("token", token);
        parametros[2] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_actualizarTokenCliente,SOAP_ACTION+OPERACION_actualizarTokenCliente, parametros, listener);
    }

    public static void actualizarTokenVendedor(int idVen, String token, ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[3];
        parametros[0] = parametro("idVen", idVen);
        parametros[1] = parametro("token", token);
        parametros[2] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_actualizarTokenVendedor,SOAP_ACTION+OPERACION_actualizarTokenVendedor, parametros, listener);
    }

    public static void notificacion(int idVen, ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idVen", idVen);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_notificacion,SOAP_ACTION+OPERACION_notificacion, parametros, listener);
    }

    public static void ubicacionPedido(int idVen, ListenerWebService listener){
        PropertyInfo[] parametros = new PropertyInfo[2];
        parametros[0] = parametro("idVen", idVen);
        parametros[1] = parametro("passWS", PASSWS);
        invocarWebService2(WSDL_TARGET_NAMESPACE,DIRECCION_SOAP, OPERACION_ubicacionPedido,SOAP_ACTION+OPERACION_ubicacionPedido, parametros, listener);
    }


    private static PropertyInfo parametro(String nombre, String valor) {
        PropertyInfo pi = new PropertyInfo();
        pi.setName(nombre);
        pi.setValue(valor);
        pi.setType(String.class);
        return pi;
    }

    private static PropertyInfo parametro(String nombre, int valor) {
        PropertyInfo pi = new PropertyInfo();
        pi.setName(nombre);
        pi.setValue(valor);
        pi.setType(Integer.class);
        return pi;
    }



    private static void invocarWebService(String nameSpace,
                                          String wsdlAdress,
                                          String operacion,
                                          final String soapAction,
                                          PropertyInfo[] parametros,
                                          final ListenerWebService listener) {
        SoapObject request = new SoapObject(nameSpace, operacion);
        for (int i=0; i<parametros.length; i++)
            request.addProperty(parametros[i]);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        final HttpTransportSE androidHttpTransport = new HttpTransportSE(wsdlAdress,90000);
        androidHttpTransport.debug = true;
        AsyncTask<Void, Object, Object> task = new AsyncTask<Void, Object, Object>(){
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    androidHttpTransport.call(soapAction, envelope);

                    SoapPrimitive soapRespuesta = (SoapPrimitive) envelope.getResponse();
                    if (soapRespuesta!= null)
                        return soapRespuesta.toString();

                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (XmlPullParserException e3) {
                    e3.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(final Object success) {
                if (success != null)
                    listener.onResultado(success);
                else
                    listener.onError("Error");
            }
        };
        task.execute((Void) null);
    }

    private static void invocarWebService2(String nameSpace,
                                          String wsdlAdress,
                                          String operacion,
                                          final String soapAction,
                                          PropertyInfo[] parametros,
                                          final ListenerWebService listener) {
        SoapObject request = new SoapObject(nameSpace, operacion);
        for (int i=0; i<parametros.length; i++)
            request.addProperty(parametros[i]);
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        final HttpTransportSE androidHttpTransport = new HttpTransportSE(wsdlAdress,90000);
        androidHttpTransport.debug = true;
        AsyncTask<Void, Object, Object> task = new AsyncTask<Void, Object, Object>(){
            @Override
            protected Object doInBackground(Void... params) {
                try {
                    androidHttpTransport.call(soapAction, envelope);

                    if (envelope.getResponse()!=null)
                        return envelope.getResponse().toString();
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (XmlPullParserException e3) {
                    e3.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(final Object success) {
                if (success != null)
                    listener.onResultado(success);
                else
                    listener.onError("Error");
            }
        };
        task.execute((Void) null);
    }


}
