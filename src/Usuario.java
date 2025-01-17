public class Usuario {
    private String dni;
    private String nombre; // Nueva propiedad opcional
    private String correo; // Nueva propiedad opcional

    // Constructor básico
    public Usuario(String dni) {
        this.dni = dni;
    }

    // Constructor extendido
    public Usuario(String dni, String nombre, String correo) {
        this.dni = dni;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                '}';
    }
}
