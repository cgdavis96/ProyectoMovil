package Objetos;

public class Usuario {
    String rol;
    int id;

    public Usuario(int id,String rol) {
        this.rol = rol;
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
