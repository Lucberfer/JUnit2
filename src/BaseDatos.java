import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDatos {
    private Connection conexion;

    public void conectar() {
        try {
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

    public void crearTablas() {
        String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dni TEXT NOT NULL UNIQUE)";
        String sqlIngresos = "CREATE TABLE IF NOT EXISTS ingresos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dni TEXT NOT NULL, " +
                "concepto TEXT NOT NULL, " +
                "cantidad REAL NOT NULL, " +
                "FOREIGN KEY (dni) REFERENCES usuarios(dni))";
        String sqlGastos = "CREATE TABLE IF NOT EXISTS gastos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "dni TEXT NOT NULL, " +
                "concepto TEXT NOT NULL, " +
                "cantidad REAL NOT NULL, " +
                "FOREIGN KEY (dni) REFERENCES usuarios(dni))";

        try {
            try (PreparedStatement stmtUsuarios = conexion.prepareStatement(sqlUsuarios)) {
                stmtUsuarios.executeUpdate();
            }
            try (PreparedStatement stmtIngresos = conexion.prepareStatement(sqlIngresos)) {
                stmtIngresos.executeUpdate();
            }
            try (PreparedStatement stmtGastos = conexion.prepareStatement(sqlGastos)) {
                stmtGastos.executeUpdate();
            }
            System.out.println("Tablas verificadas o creadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al crear las tablas: " + e.getMessage());
        }
    }

    public boolean autenticarUsuario(String dni) {
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

    public void registrarIngreso(String dni, String concepto, double cantidad) {
        String sql = "INSERT INTO ingresos (dni, concepto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, concepto);
            stmt.setDouble(3, cantidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar el ingreso: " + e.getMessage());
        }
    }

    public void registrarGasto(String dni, String concepto, double cantidad) {
        String sql = "INSERT INTO gastos (dni, concepto, cantidad) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, concepto);
            stmt.setDouble(3, cantidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al registrar el gasto: " + e.getMessage());
        }
    }

    public double cargarIngresos(String dni) {
        String sql = "SELECT SUM(cantidad) FROM ingresos WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los ingresos: " + e.getMessage());
        }
        return 0;
    }

    public double cargarGastos(String dni) {
        String sql = "SELECT SUM(cantidad) FROM gastos WHERE dni = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los gastos: " + e.getMessage());
        }
        return 0;
    }
}
