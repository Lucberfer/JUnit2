import java.util.HashMap;

public class Ingresos {
    private HashMap<String, Double> ingresos;

    public Ingresos() {
        ingresos = new HashMap<>();
    }

    public boolean registrarIngreso(String concepto, double cantidad) {
        if (concepto.equalsIgnoreCase("nomina")) {
            cantidad -= cantidad * 0.15; // Aplicar IRPF del 15%
        }
        ingresos.put(concepto, ingresos.getOrDefault(concepto, 0.0) + cantidad);
        return true;
    }

    public void mostrarIngresos() {
        System.out.println("Ingresos registrados:");
        ingresos.forEach((concepto, cantidad) ->
                System.out.println(" - " + concepto + ": " + cantidad));
    }

    public double getTotalIngresos() {
        return ingresos.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
