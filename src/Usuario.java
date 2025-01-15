public class Usuario {
    private String dni;

    // Constructor
    public Usuario(String dni) {
        this.dni = dni;
    }

    // Getter para el DNI
    public String getDni() {
        return dni;
    }

    // Setter para el DNI (si fuera necesario modificarlo)
    public void setDni(String dni) {
        this.dni = dni;
    }

    // Metodo para mostrar información del usuario
    @Override
    public String toString() {
        return "Usuario{" +
                "dni='" + dni + '\'' +
                '}';
    }
}
