public class Gastos {
    private double totalGastos = 0;

    public boolean registrarGasto(String concepto, double cantidad, double saldoDisponible) {
        if (cantidad < 0) {
            System.out.println("El gasto no puede ser negativo.");
            return false;
        }

        if (cantidad > saldoDisponible) {
            System.out.println("No se puede realizar la operación por falta de saldo.");
            return false;
        }

        totalGastos += cantidad;
        System.out.println("Gasto registrado: " + concepto + " por un valor de " + cantidad);
        return true;
    }

    public double getTotalGastos() {
        return totalGastos;
    }

    public void setTotalGastos(double totalGastos) {
        this.totalGastos = totalGastos;
    }

    public void mostrarGastos() {
        System.out.println("Gastos totales: " + totalGastos);
    }
}
