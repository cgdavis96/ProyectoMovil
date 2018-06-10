package mx.edu.itlp.proyectomovil.webservice;

/**
 * Created by Jhosef Davis on 18/05/2018.
 */

public interface ListenerWebService {
    void onResultado(Object resultado);
    void onError(String error);
}
