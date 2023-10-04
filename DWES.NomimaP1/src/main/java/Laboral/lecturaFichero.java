package Laboral;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LecturaFichero {

	public static void lecFichero() throws SQLException {
		/* Lectura por texto plano */

		try {
			ArrayList<Empleado> empleados = new ArrayList<Empleado>();
			@SuppressWarnings("resource")
			BufferedReader entradaArchivo = new BufferedReader(new FileReader(
					"C:\\Users\\usuario23\\Desktop\\DWES\\DWES.NomimaP1\\src\\main\\java\\Laboral\\Empleados.txt"));
			String linea;

			while ((linea = entradaArchivo.readLine()) != null) {
				String[] datos = linea.split(",");
				if (datos.length == 5) {
					String nombre = datos[0];
					String dni = datos[1];
					char sexo = datos[2].charAt(0);
					int categoria = Integer.parseInt(datos[3]);
					double salarioBase = Double.parseDouble(datos[4]);

					Empleado empleado = new Empleado(nombre, dni, sexo, categoria, salarioBase);
					empleados.add(empleado);
				} else if (datos.length == 3) {
					String nombre = datos[0];
					String dni = datos[1];
					char sexo = datos[2].charAt(0);

					Empleado empleado = new Empleado(nombre, dni, sexo);
					empleados.add(empleado);
				} else {
					throw new DatosNoCorrectosException("Datos Erroneos");
				}

			}
			entradaArchivo.close();

			DataOutputStream archivoSalida = new DataOutputStream(new FileOutputStream("sueldos.dat"));

			for (Empleado empleado : empleados) {
				Nomina nomina = new Nomina();
				double salario = nomina.sueldo(empleado);
				archivoSalida.writeUTF(empleado.dni);
				archivoSalida.writeDouble(salario);
			}

			archivoSalida.close();

			for (Empleado empleado : empleados) {
				escribe(empleado);
			}

			Empleado primerEmpleado = empleados.get(0);
			primerEmpleado.incrAnyo();
			primerEmpleado.setCategoria(9);
			escribe(primerEmpleado);

		} catch (DatosNoCorrectosException e) {
			System.out.println("Algun dato es inválido");
		} catch (IOException e) {
			System.out.println("Error de E/S: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.out.println("Error al convertir un número: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error inesperado: " + e.getMessage());
		}
	}

	private static void escribe(Empleado e) {
		Nomina n = new Nomina();
		e.imprime();
		System.out.println(n.sueldo(e));
	}

}
