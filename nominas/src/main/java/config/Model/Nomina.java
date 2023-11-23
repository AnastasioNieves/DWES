package config.Model;

/**
 * Clase Nomina de cálculo de sueldos
 */
public class Nomina {

    /**
     * Declaración de biblioteca de sueldos base
     */
    private static final int SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000 };

    /**
     * Método de cálculo de sueldo
     * 
     * @param e
     * @return sueldo total calculado
     */
    public double sueldo(Empleado e) {
        int sueldoBase = SUELDO_BASE[e.getCategoria() - 1];
        double sueldo = sueldoBase + 5000 * e.anyos;

        return sueldo;
    }
}
