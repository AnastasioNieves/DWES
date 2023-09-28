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

			// Crear una consulta SQL para obtener los empleados y sus datos
			String query = "SELECT nombre, dni, sexo, categoria, anyos FROM Empleado";

			// Crear una declaración
			Statement st = conn.createStatement();

			// inserta los empleados
			insertarEmpleado(conn, "James Cosling", "32000032G", 'M', 4, 7.0);
			insertarEmpleado(conn, "Ada Lovelace", "32000031F", 'F',0,0);
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
					escribe(empleado);

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			
			for (Empleado empleado : empleados) {
				Nomina nomina = new Nomina();
				double sueldo = nomina.sueldo(empleado);

			// inserta los sueldos
			String insertQuery = "INSERT INTO Nomina (sueldo) SET sueldo = 0 WHERE dni =?";
			PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
			
				insertStatement = conn.prepareStatement(insertQuery);
				insertStatement.setDouble( 0, sueldo);
			}

			// Crear una consulta SQL para actualizar el sueldo en la tabla Nominas
			String updateQuery = "UPDATE nomina SET sueldo = ? WHERE dni = ?";
			PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

			for (Empleado empleado : empleados) {
				Nomina nomina = new Nomina();
				double sueldo = nomina.sueldo(empleado);

				

				// Actualizar la tabla Nominas con el sueldo calculado

			
				updateStatement.setDouble(2, sueldo);
				updateStatement.executeUpdate();
			}

			updateCategoriaEmpleado(conn, empleados.get(0), 1);
			updateAnyosEmpleado(conn, empleados.get(0));

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

	private static void updateCategoriaEmpleado(Connection conn,  Empleado empleado,int nuevaCategoria)
			throws SQLException {
		String updateQuery = "UPDATE Empleado SET categoria = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setInt(1, nuevaCategoria);
		updateStatement.setString(2, empleado.dni);

		updateStatement.executeUpdate();
	}

	private static void updateAnyosEmpleado(Connection conn, Empleado empleado) throws SQLException {
		String updateQuery = "UPDATE Empleado SET anyo = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		updateStatement.setDouble(1, empleado.incrAnyo());

		updateStatement.executeUpdate();
	}
}
