import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDatos {
    private Connection conexion;

    public void conectar() {
        try {
            // Cargar el controlador de SQLite
            Class.forName("org.sqlite.JDBC");
            conexion = DriverManager.getConnection("jdbc:sqlite:usuarios.db");
            System.out.println("Conexión con la base de datos establecida.");
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el controlador JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión con la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public void crearTablaUsuarios() {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dni TEXT NOT NULL UNIQUE)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.executeUpdate();
            System.out.println("Tabla 'usuarios' verificada o creada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear la tabla 'usuarios': " + e.getMessage());
        }
    }

    public boolean autenticarUsuario(String dni) {
        if (conexion == null) {
            System.out.println("La conexión a la base de datos no está inicializada.");
            return false;
        }

        String sql = "SELECT COUNT(*) FROM usuarios WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar el usuario: " + e.getMessage());
        }
        return false;
    }

    public boolean registrarUsuario(String dni) {
        if (conexion == null) {
            System.out.println("La conexión a la base de datos no está inicializada.");
            return false;
        }

        String sql = "INSERT INTO usuarios (dni) VALUES (?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }
        return false;
    }
}
