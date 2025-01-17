import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BaseDatos baseDatos = new BaseDatos();
        baseDatos.conectar();
        baseDatos.crearTablas();

        Scanner scanner = new Scanner(System.in);

        // Validación del DNI
        String dni;
        while (true) {
            System.out.println("Usuario, introduzca su DNI:");
            dni = scanner.nextLine().toUpperCase();

            if (dni.matches("\\d{8}[A-Za-z]")) {
                break;
            } else {
                System.out.println("DNI no válido. Asegúrese de que tiene 8 dígitos seguidos de una letra.");
            }
        }

        // Autenticar o registrar usuario
        if (!baseDatos.autenticarUsuario(dni)) {
            System.out.println("Usuario no registrado. Registrando...");
            if (!baseDatos.registrarUsuario(dni)) {
                System.out.println("Error al registrar usuario. Terminando programa.");
                baseDatos.cerrarConexion();
                return;
            }
            System.out.println("Usuario registrado exitosamente.");
        }

        Ingresos ingresos = new Ingresos();
        Gastos gastos = new Gastos();
        ingresos.setTotalIngresos(baseDatos.cargarIngresos(dni));
        gastos.setTotalGastos(baseDatos.cargarGastos(dni));

        boolean continuar = true;
        while (continuar) {
            System.out.println("¿Qué desea realizar? (gasto/ingreso/salir)");
            String opcion = scanner.nextLine().toLowerCase();

            switch (opcion) {
                case "gasto":
                    System.out.println("Introduzca el concepto del gasto (vacaciones/alquiler/vicios):");
                    String conceptoGasto = scanner.nextLine();
                    System.out.println("Introduzca el valor del gasto:");
                    double cantidadGasto = scanner.nextDouble();
                    scanner.nextLine();

                    if (gastos.registrarGasto(conceptoGasto, cantidadGasto, ingresos.getTotalIngresos())) {
                        baseDatos.registrarGasto(dni, conceptoGasto, cantidadGasto);
                        System.out.println("Gasto registrado correctamente.");
                    }
                    break;

                case "ingreso":
                    System.out.println("Introduzca el concepto del ingreso(nomina/ventas):");
                    String conceptoIngreso = scanner.nextLine();
                    System.out.println("Introduzca el valor del ingreso:");
                    double cantidadIngreso = scanner.nextDouble();
                    scanner.nextLine();

                    if (ingresos.registrarIngreso(conceptoIngreso, cantidadIngreso)) {
                        baseDatos.registrarIngreso(dni, conceptoIngreso, cantidadIngreso);
                        System.out.println("Ingreso registrado correctamente.");
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
            double saldoTotal = ingresos.getTotalIngresos() - gastos.getTotalGastos();
            System.out.println("Saldo total: " + saldoTotal);
        }

        System.out.println("Programa terminado. Saldo final: " +
                (ingresos.getTotalIngresos() - gastos.getTotalGastos()));

        baseDatos.cerrarConexion();
    }
}
