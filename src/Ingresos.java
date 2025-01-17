public class Ingresos {
    private double totalIngresos = 0;

    public boolean registrarIngreso(String concepto, double cantidad) {
        if (cantidad < 0) {
            System.out.println("El ingreso no puede ser negativo.");
            return false;
        }

        if (concepto.equals("nomina")) {
            cantidad *= 0.85;  // Aplicar IRPF (15%)
        }

        totalIngresos += cantidad;
        return true;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public void mostrarIngresos() {
        System.out.println("Ingresos totales: " + totalIngresos);
    }
}
