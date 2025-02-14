package modelos;

public class Cliente {
    private String id;
    private String nombre;
    private String edad;
    private String telefono;
    private String ciudad;

    public Cliente() {
    }

    public Cliente(String id, String nombre, String edad, String ciudad, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + ", edad=" + edad + ", telefono=" + telefono + ", ciudad="
                + ciudad + "]";
    }

}
