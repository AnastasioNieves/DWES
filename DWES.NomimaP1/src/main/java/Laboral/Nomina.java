package Laboral;

/**
 * 
 * Clase nomina
 * 
 * @author Anastasio
 */

public class Nomina {

	/**
	 * Tabla para calcular el sueldo base
	 * 
	 */
	private static final int SUELDO_BASE[] = { 50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000,
			230000 };

	/**
	 * Metodo para calcular el sueldo de un empleado
	 * 
	 * @param empleado
	 * @param SUELDO_BASE
	 * @return sueldo
	 * 
	 */

	public double sueldo(Empleado e) {

		Empleado empleado = e;
		int sueldoBase;

		sueldoBase = SUELDO_BASE[empleado.getCategoria() - 1];

		return sueldoBase + 5000 * empleado.anyos;

	}

}
