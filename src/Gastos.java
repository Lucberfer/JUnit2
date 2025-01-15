import java.util.HashMap;

public class Gastos {
    private HashMap<String, Double> gastos;

    public Gastos() {
        gastos = new HashMap<>();
    }

    public boolean registrarGasto(String concepto, double cantidad, double totalIngresos) {
        if (cantidad > totalIngresos) {
            System.out.println("Error: No puede gastar más de lo que tiene ingresado.");
            return false;
        }
        gastos.put(concepto, gastos.getOrDefault(concepto, 0.0) + cantidad);
        return true;
    }

    public void mostrarGastos() {
        System.out.println("Gastos registrados:");
        gastos.forEach((concepto, cantidad) ->
                System.out.println(" - " + concepto + ": " + cantidad));
    }

    public double getTotalGastos() {
        return gastos.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
