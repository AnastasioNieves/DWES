package Laboral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class conBD {

	public static void tryConect() throws SQLException {
		Connection conn = null;
		try {
			// URL de conexión a la base de datos
			String url = "jdbc:mariadb://localhost:3306/Empleados";

			// Credenciales de acceso a la base de datos (usuario y contraseña)
			String user = "root";
			String password = "12345";

			// Establecer la conexión
			conn = DriverManager.getConnection(url, user, password);

			// Crear una declaración
			Statement st = conn.createStatement();

			// inserta los empleados
			insertarEmpleado(conn, "James Cosling", "32000032G", 'M', 4, 7.0);
			insertarEmpleado(conn, "Ada Lovelace", "32000031F", 'F', 1, 0);
			insertarEmpleado(conn, "Anastasio ", "30202020F", 'M', 5, 10.0);

			// Crear una consulta SQL para obtener los empleados y sus datos
			String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleado";

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

			}// Actualizar la categoría del empleado
			updateCategoriaEmpleado(conn, "32000032G", 9);

			// Actualizar los años del empleado
			updateAnyosEmpleado(conn, "32000032G");
			
			
			

			for (Empleado empleado : empleados) {

				Nomina nomina = new Nomina();
				double sueldo = nomina.sueldo(empleado);

				ResultSet num = st
						.executeQuery("Insert INTO nomina (dni,sueldo) VALUES ('" + empleado.dni + "'," + sueldo + ")");
				System.out.println("Nomina insertada (" + num + "filas) ");
			}

			

			// Crear una consulta SQL para actualizar el sueldo en la tabla Nominas
			String updateQuery = "UPDATE nomina SET sueldo = ? WHERE dni = ?";
			PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

			for (Empleado empleado : empleados) {

				Nomina nomina = new Nomina();
				double sueldo = nomina.sueldo(empleado);

				// Actualizar la tabla Nominas con el sueldo calculado

				
				updateStatement.setDouble(1, sueldo);
				updateStatement.setString(2, empleado.dni);
				updateStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private static void escribe(Empleado e) {
		Nomina n = new Nomina();
		e.imprime();
		System.out.println(n.sueldo(e));
	}

	private static void insertarEmpleado(Connection conn, String nombre, String dni, char sexo, int categoria,
			double anyos) throws SQLException {
		String insertQuery = "INSERT INTO Empleado (nombre, dni, sexo, categoria, anyos) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement insertStatement = conn.prepareStatement(insertQuery);

		Empleado empleado;
		try {

			empleado = new Empleado(nombre, dni, sexo, categoria, anyos);

			if (empleado.getCategoria() <= 1 && empleado.anyos <= 0) {

				empleado.setCategoria(1);
				empleado.anyos = 0;

				insertStatement.setString(1, nombre);
				insertStatement.setString(2, dni);
				insertStatement.setString(3, String.valueOf(sexo));
				insertStatement.setInt(4, categoria);
				insertStatement.setDouble(5, anyos);

				insertStatement.executeUpdate();
				escribe(empleado);

			} else {

				insertStatement.setString(1, nombre);
				insertStatement.setString(2, dni);
				insertStatement.setString(3, String.valueOf(sexo));
				insertStatement.setInt(4, categoria);
				insertStatement.setDouble(5, anyos);

				insertStatement.executeUpdate();
				escribe(empleado);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void updateCategoriaEmpleado(Connection conn, String dni, int nuevaCategoria) throws SQLException {
		String updateQuery = "UPDATE Empleado SET categoria = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setInt(1, nuevaCategoria);
		updateStatement.setString(2, dni);

		updateStatement.executeUpdate();
	}

	public static void updateAnyosEmpleado(Connection conn, String dni) throws SQLException {
		String updateQuery = "UPDATE Empleado SET anyos = anyos + 1 WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setString(1, dni);

		updateStatement.executeUpdate();
	}
}
