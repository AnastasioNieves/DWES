package Laboral;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase contenedora del codigo Main del Punto 5 de la Parte 2
 */
public class Menu {

	/**
	 * Llamada al codigo Main del Punto 5 de la Parte 2
	 * 
	 * @throws Exception
	 */
	public static void ejecucion() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		Scanner sc = new Scanner(System.in);
		int numMenu;

		try {
			do {

				System.out.println("--------------------------------------------");
				System.out.println("0 - SALIR");
				System.out.println("1 - Mostrar todos los empleados.");
				System.out.println("2 - Mostrar la nomina de un empleado segun su DNI.");
				System.out.println("3 - Editar un empleado.");
				System.out.println("4 - Recalcular el sueldo de un empleado.");
				System.out.println("5 - Realizar una copia de seguridad de la base de datos en un archivo.");
				numMenu = sc.nextInt();

				switch (numMenu) {
				case 0:
					System.out.println("Saliendo...");
					break;

				case 1:
					conn = ConBD.connection();
					st = conn.createStatement();

					rs = st.executeQuery("SELECT * FROM empleado");
					while (rs.next()) {
						String dni = rs.getString(2);
						String nombre = rs.getString(1);
						char sexo = rs.getString(3).charAt(0);
						int categoria = rs.getInt(4);
						double anyos = rs.getDouble(5);
						System.out.println("DNI: " + dni + ", Nombre: " + nombre + ", Sexo: " + sexo + ", Categoria: "
								+ categoria + ", Anyos: " + anyos);
					}
					break;

				case 2:
					conn = ConBD.connection();
					st = conn.createStatement();

					System.out.println("DNI a buscar: ");
					String dniNomina = sc.next();

					rs = st.executeQuery("SELECT sueldo FROM nomina WHERE empleado_dni = '" + dniNomina + "'");
					if (rs.next()) {
						System.out.println("Nomina: " + rs.getDouble(1));
					}
					break;

				case 3:
					conn = ConBD.connection();
					st = conn.createStatement();

					sc.nextLine(); // Para limpiar el buffer del Scanner.

					System.out.println("DNI del empleado a editar: ");
					String dniEmpleado = sc.nextLine();

					System.out.println("Nuevo nombre: ");
					String nombreNuevo = sc.nextLine();
					if (nombreNuevo instanceof String) {
						rs = st.executeQuery("UPDATE empleados SET nombre = '" + nombreNuevo + "' WHERE dni = '"
								+ dniEmpleado + "'");
						System.out.println("Nombre cambiado con exito.");
					} else if (nombreNuevo == "" || nombreNuevo == null) {
						System.out.println("ERROR: entrada incorrecta, nada que cambiar.");
					} else {
						System.out.println("ERROR: entrada incorrecta, nada que cambiar.");
					}

					System.out.println("Nuevo sexo (M/F): ");
					char sexoNuevo = sc.next().charAt(0);
					if (sexoNuevo == 'M' || sexoNuevo == 'F') {
						rs = st.executeQuery(
								"UPDATE empleados SET sexo = '" + sexoNuevo + "' WHERE dni = '" + dniEmpleado + "'");
						System.out.println("Sexo cambiado con exito.");
					} else {
						System.out.println("ERROR: entrada incorrecta, nada que cambiar.");
					}

					System.out.println("Nueva categoria: ");
					int categoriaNueva = sc.nextInt();
					if (categoriaNueva >= 1 && categoriaNueva <= 10) {
						rs = st.executeQuery("UPDATE empleado SET categoria = " + categoriaNueva + " WHERE dni = '"
								+ dniEmpleado + "'");
						System.out.println("Categoria cambiada con exito.");
					} else {
						System.out.println("ERROR: entrada incorrecta, nada que cambiar.");
					}

					System.out.println("Nuevos anyos: ");
					double anyosNuevo = sc.nextDouble();
					if (anyosNuevo >= 0) {
						rs = st.executeQuery(
								"UPDATE empleados SET anyos = " + anyosNuevo + " WHERE dni = '" + dniEmpleado + "'");
						System.out.println("Anyos cambiados con exito.");
					} else {
						System.out.println("ERROR: entrada incorrecta, nada que cambiar.");
					}

					Empleado e;
					try {
						e = new Empleado(dniEmpleado, nombreNuevo, sexoNuevo, categoriaNueva, anyosNuevo);
						Nomina n = new Nomina();
						double sueldoNuevo = n.sueldo(e);
						rs = st.executeQuery("UPDATE nomina SET sueldo = " + sueldoNuevo + " WHERE empleado_dni = '"
								+ dniEmpleado + "'");
						System.out.println("Nomina cambiada con exito.");

						System.out.println("Empleado actualizado con exito.");
					} catch (DatosNoCorrectosException e1) {
						// TODO Auto-generated catch block
						e1.getMessage();
					}

					break;

				case 4:
					conn = ConBD.connection();
					st = conn.createStatement();

					List<Empleado> empleadosNominas = new ArrayList<>();

					rs = st.executeQuery("SELECT * FROM empleado");
					while (rs.next()) {
						String dni = rs.getString(2);
						String nombre = rs.getString(1);
						char sexo = rs.getString(3).charAt(0);
						int categoria = rs.getInt(4);
						double anyos = rs.getDouble(5);
						try {
							empleadosNominas.add(new Empleado(dni, nombre, sexo, categoria, anyos));
						} catch (DatosNoCorrectosException e1) {
							// TODO Auto-generated catch block
							e1.getMessage();
						}
					}
					System.out.println("Empleados leidos con exito.");

					for (int i = 0; i < empleadosNominas.size(); i++) {
						Empleado empleadoNominas = empleadosNominas.get(i);
						Nomina nominaUpdate = new Nomina();
						double sueldoUpdate = nominaUpdate.sueldo(empleadoNominas);

						rs = st.executeQuery("UPDATE nomina SET sueldo = " + sueldoUpdate + " WHERE empleado_dni = '"
								+ empleadoNominas.dni + "'");
					}
					System.out.println("Nominas actualizadas con exito.");
					break;

				case 5:
					conn = ConBD.connection();
					st = conn.createStatement();

					List<Empleado> empleadosBackup = new ArrayList<>();

					PrintWriter salidaEmpleado = null, salidaNomina = null;
					salidaEmpleado = new PrintWriter(
							"C:\\Users\\PC\\Documents\\workspace\\PracticaNomina\\media\\backupEmpleado2.dat");
					salidaNomina = new PrintWriter(
							"C:\\Users\\PC\\Documents\\workspace\\PracticaNomina\\media\\backupNomina2.dat");

					rs = st.executeQuery("SELECT * FROM empleado");
					while (rs.next()) {
						String dni = rs.getString(2);
						String nombre = rs.getString(1);
						char sexo = rs.getString(3).charAt(0);
						int categoria = rs.getInt(4);
						double anyos = rs.getDouble(5);
						empleadosBackup.add(new Empleado(dni, nombre, sexo, categoria, anyos));
					}
					System.out.println("Empleados leidos con exito.");

					for (int i = 0; i < empleadosBackup.size(); i++) {
						Empleado empleadoBackup = empleadosBackup.get(i);
						Nomina nominaBackup = new Nomina();
						double sueldoBackup = nominaBackup.sueldo(empleadoBackup);

						salidaEmpleado.println("DNI: " + empleadoBackup.dni + ", Nombre: " + empleadoBackup.nombre
								+ ", Sexo: " + empleadoBackup.sexo + ", Categoria: " + empleadoBackup.getCategoria()
								+ ", Anyos: " + empleadoBackup.anyos);
						salidaNomina.println("DNI: " + empleadoBackup.dni + ", Sueldo: " + sueldoBackup);
					}
					System.out.println("Backups creados con exito.");
					salidaEmpleado.close();
					salidaNomina.close();
					break;

				default:
					System.out.println("Salida incorrecta, pruebe otra vez.");
				}

			} while (numMenu != 0);
		} catch (SQLException e) {
			System.out.println(e);
		} catch (DatosNoCorrectosException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
				System.out.println("Terminado con exito.");
			} catch (SQLException ex) {
				System.out.println("Ocurrió una excepción al cerrar la BD");
			}
		}

	}

}
