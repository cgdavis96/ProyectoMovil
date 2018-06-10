package Objetos;

/**
 * Created by Jhosef Davis on 02/06/2018.
 */

public class Comprador {
    private String correo;
    private String contraseña;
    private String nombre;
    private String apellidoP;
    private String apellidoM;
    private String telefono;
    private String rol;
    private int idCli;

    public Comprador(){
    }

    public Comprador(String correo, String contraseña, String nombre, String apellidoP, String apellidoM, String telefono){
        this.correo = correo;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.telefono = telefono;
    }


    public Comprador(String rol, int idCli) {
        this.rol = rol;
        this.idCli = idCli;
    }

    public int getIdVen() {
        return idCli;
    }

    public void setIdVen(int idVen) {
        this.idCli = idVen;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}

