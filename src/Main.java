import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BaseDatos baseDatos = new BaseDatos();
        baseDatos.conectar(); // Conecta a la base de datos
        baseDatos.crearTablaUsuarios(); // Crea la tabla 'usuarios' si no existe

        Scanner scanner = new Scanner(System.in);

        System.out.println("Usuario, introduzca su DNI:");
        String dni = scanner.nextLine();

        // Autenticar usuario
        if (!baseDatos.autenticarUsuario(dni)) {
            System.out.println("Usuario no registrado. Registrando...");
            if (!baseDatos.registrarUsuario(dni)) {
                System.out.println("Error al registrar usuario. Terminando programa.");
                baseDatos.cerrarConexion();
                return;
            }
            System.out.println("Usuario registrado exitosamente.");
        }

        boolean continuar = true;
        Ingresos ingresos = new Ingresos();
        Gastos gastos = new Gastos();

        while (continuar) {
            System.out.println("¿Qué desea realizar? (gasto/ingreso/salir)");
            String opcion = scanner.nextLine().toLowerCase();

            switch (opcion) {
                case "gasto":
                    boolean gestionandoGasto = true;
                    while (gestionandoGasto) {
                        System.out.println("Introduzca el concepto del gasto (vacaciones/alquiler/vicios) o 'volver' para regresar al menú principal:");
                        String conceptoGasto = scanner.nextLine().toLowerCase();

                        if (conceptoGasto.equals("volver")) {
                            gestionandoGasto = false; // Sale del bucle y regresa al menú principal
                        } else if (conceptoGasto.equals("vacaciones") || conceptoGasto.equals("alquiler") || conceptoGasto.equals("vicios")) {
                            System.out.println("Introduzca el valor del gasto:");
                            double operacionGasto = scanner.nextDouble();
                            scanner.nextLine(); // Limpiar buffer
                            if (gastos.registrarGasto(conceptoGasto, operacionGasto, ingresos.getTotalIngresos())) {
                                System.out.println("Gasto registrado correctamente.");
                            }
                        } else {
                            System.out.println("Concepto no válido. Por favor, elija entre 'vacaciones', 'alquiler', 'vicios' o 'volver'.");
                        }
                    }
                    break;

                case "ingreso":
                    boolean gestionandoIngreso = true;
                    while (gestionandoIngreso) {
                        System.out.println("Introduzca el concepto de ingreso (nomina/ventas) o 'volver' para regresar al menú principal:");
                        String conceptoIngreso = scanner.nextLine().toLowerCase();

                        if (conceptoIngreso.equals("volver")) {
                            gestionandoIngreso = false; // Sale del bucle y regresa al menú principal
                        } else if (conceptoIngreso.equals("nomina") || conceptoIngreso.equals("ventas")) {
                            System.out.println("Introduzca el valor del ingreso:");
                            double operacionIngreso = scanner.nextDouble();
                            scanner.nextLine(); // Limpiar buffer
                            if (ingresos.registrarIngreso(conceptoIngreso, operacionIngreso)) {
                                System.out.println("Ingreso registrado correctamente.");
                            }
                        } else {
                            System.out.println("Concepto no válido. Por favor, elija entre 'nomina', 'ventas' o 'volver'.");
                        }
                    }
                    break;

                case "salir":
                    continuar = false;
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println("Estado actual:");
            ingresos.mostrarIngresos();
            gastos.mostrarGastos();
        }

        System.out.println("Programa terminado. Saldo final: " +
                (ingresos.getTotalIngresos() - gastos.getTotalGastos()));

        baseDatos.cerrarConexion(); // Cierra la conexión al final
    }
}
