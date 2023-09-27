package Laboral;

/*Lectura por texto plano*/
//import java.io.*;
//import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class calculaNominas {

	public static void main(String[] args) throws SQLException {
		/* Lectura por texto plano */

//		try {
//			ArrayList<Empleado> empleados = new ArrayList<Empleado>();
//			@SuppressWarnings("resource")
//			BufferedReader entradaArchivo = new BufferedReader(new FileReader(
//					"C:\\Users\\usuario23\\Desktop\\DWES\\DWES.NomimaP1\\src\\main\\java\\Laboral\\Empleados.txt"));
//			String linea;
//
//			while ((linea = entradaArchivo.readLine()) != null) {
//				String[] datos = linea.split(",");
//				if (datos.length == 5) {
//					String nombre = datos[0];
//					String dni = datos[1];
//					char sexo = datos[2].charAt(0);
//					int categoria = Integer.parseInt(datos[3]);
//					double salarioBase = Double.parseDouble(datos[4]);
//
//					Empleado empleado = new Empleado(nombre, dni, sexo, categoria, salarioBase);
//					empleados.add(empleado);
//				} else if (datos.length == 3) {
//					String nombre = datos[0];
//					String dni = datos[1];
//					char sexo = datos[2].charAt(0);
//
//					Empleado empleado = new Empleado(nombre, dni, sexo);
//					empleados.add(empleado);
//				} else {
//					throw new DatosNoCorrectosException("Datos Erroneos");
//				}
//
//			}
//			entradaArchivo.close();
//
//			DataOutputStream archivoSalida = new DataOutputStream(new FileOutputStream("sueldos.dat"));
//
//			for (Empleado empleado : empleados) {
//				Nomina nomina = new Nomina();
//				double salario = nomina.sueldo(empleado);
//				archivoSalida.writeUTF(empleado.dni);
//				archivoSalida.writeDouble(salario);
//			}
//
//			archivoSalida.close();
//
//			for (Empleado empleado : empleados) {
//				escribe(empleado);
//			}
//
//			Empleado primerEmpleado = empleados.get(0);
//			primerEmpleado.incrAnyo();
//			primerEmpleado.setCategoria(9);
//			escribe(primerEmpleado);
//
//		} catch (DatosNoCorrectosException e) {
//			System.out.println("Algun dato es inválido");
//		} catch (IOException e) {
//			System.out.println("Error de E/S: " + e.getMessage());
//		} catch (NumberFormatException e) {
//			System.out.println("Error al convertir un número: " + e.getMessage());
//		} catch (Exception e) {
//			System.out.println("Error inesperado: " + e.getMessage());
//		}

		Connection conn = null;
		try {
			// URL de conexión a la base de datos
			String url = "jdbc:mariadb://localhost:3306/Empleados";

			// Credenciales de acceso a la base de datos (usuario y contraseña)
			String user = "root";
			String password = "12345";

			// Establecer la conexión
			conn = DriverManager.getConnection(url, user, password);

			// Crear una consulta SQL para obtener los empleados y sus datos
			String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleado";

			// Crear una declaración
			Statement st = conn.createStatement();

			// inserta los empleados
			insertarEmpleado(conn, "James Cosling", "32000032G", 'M', 4, 7.0);
			insertarEmpleado(conn, "Ada Lovelace", "32000031F", 'F');
			insertarEmpleado(conn, "Anastasio ", "30202020F", 'M', 5, 10.0);
			// Ejecutar la consulta y obtener un conjunto de resultados
			ResultSet resultSet = st.executeQuery(query);

			ArrayList<Empleado> empleados = new ArrayList<Empleado>();

			while (resultSet.next()) {
				String nombre = resultSet.getString("nombre");
				String dni = resultSet.getString("dni");
				char sexo = resultSet.getString("sexo").charAt(0);
				int categoria = resultSet.getInt("categoria");
				double anyos = resultSet.getDouble("anyos");

				Empleado empleado;
				try {
					empleado = new Empleado(nombre, dni, sexo, categoria, anyos);
					empleados.add(empleado);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}

			updateCategoriaEmpleado(conn, "32000032G", 9);

			// Crear una consulta SQL para actualizar el sueldo en la tabla Nominas
			String updateQuery = "UPDATE nominas SET sueldo = ? WHERE dni = ?";
			PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

			for (Empleado empleado : empleados) {
				Nomina nomina = new Nomina();
				double salario = nomina.sueldo(empleado);

				// Actualizar la tabla Nominas con el sueldo calculado
				updateStatement.setDouble(1, salario);
				updateStatement.setString(2, empleado.dni);
				updateStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void insertarEmpleado(Connection conn, String nombre, String dni, char sexo) {
		String insertQuery = "INSERT INTO Empleado (nombre, dni, sexo, categoria, anyos) VALUES (?, ?, ?)";
		PreparedStatement insertStatement;
		try {
			insertStatement = conn.prepareStatement(insertQuery);
			insertStatement.setString(1, nombre);
			insertStatement.setString(2, dni);
			insertStatement.setString(3, String.valueOf(sexo));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void insertarEmpleado(Connection conn, String nombre, String dni, char sexo, int categoria, double d)
			throws SQLException {
		String insertQuery = "INSERT INTO Empleado (nombre, dni, sexo, categoria, anyos) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = conn.prepareStatement(insertQuery);

		insertStatement.setString(1, nombre);
		insertStatement.setString(2, dni);
		insertStatement.setString(3, String.valueOf(sexo));
		insertStatement.setInt(4, categoria);
		insertStatement.setDouble(5, d);

		insertStatement.executeUpdate();
	}

	private static void updateCategoriaEmpleado(Connection conn, String dni, int nuevaCategoria) throws SQLException {
		String updateQuery = "UPDATE Empleado SET categoria = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setInt(1, nuevaCategoria);
		updateStatement.setString(2, dni);

		updateStatement.executeUpdate();
	}

	private static void updateAnyosEmpleado(Connection conn, Empleado empleado) throws SQLException {
		String updateQuery = "UPDATE Empleado SET anyo = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setDouble(1, empleado.incrAnyo());

		updateStatement.executeUpdate();
	}
}

//	private static void escribe(Empleado e) {
//		Nomina n = new Nomina();
//		e.imprime();
//		System.out.println(n.sueldo(e));
//	}
