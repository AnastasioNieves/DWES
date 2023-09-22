package Laboral;

/**
 * Clase principal
 * 
 * @author Anastasio
 *
 */
public class calculaNominas {

	/**
	 * 
	 * Metodo principal para calcular las nominas
	 * 
	 */

	public static void main(String[] args) {
		try {

			Empleado e1 = new Empleado("James Cosling ", "32000032G", 'M', 4, 7.0);

			Empleado e2 = new Empleado("Ada Lovelace", "32000031F", 'F');

			escribe(e1);
			escribe(e2);
			
			 e2.incrAnyo();

	            e1.setCategoria(9);

	            escribe(e1);
				escribe(e2);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * 
	 * Metodo para escribir en consola los empleados y sus nominas
	 * 
	 * 
	 **/
	private static void escribe(Empleado e) {
		Nomina n = new Nomina();
		e.imprime();
		System.out.println(n.sueldo(e));

	}
}
