package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Model.Empleado;
public class EmpleadoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;

	// obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}

	// obtener lista de empleados
	public List<Empleado> obtenerEmpleados() throws SQLException {
		ResultSet resultSet = null;
		List<Empleado> lista = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM empleados";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String dni = resultSet.getString(2);
				String nombre = resultSet.getString(1);
				char sexo = resultSet.getString(3).charAt(0);
				int categoria = resultSet.getInt(4);
				double anyos = resultSet.getDouble(5);
				lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
			}

		} catch (SQLException | DatosNoCorrectosException err) {
			 err.printStackTrace();
		}

		return lista;
	}

	// obtener empleado por dni
	public Empleado obtenerEmpleado(String dniEmpleado) throws SQLException {
		ResultSet resultSet = null;
		Empleado e = new Empleado();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM empleados WHERE dni =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, dniEmpleado);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				e.dni = resultSet.getString(2);
				e.nombre = resultSet.getString(1);
				e.sexo = resultSet.getString(3).charAt(0);
				e.setCategoria(resultSet.getInt(4));
				e.anyos = resultSet.getDouble(5);
			}

		} catch (SQLException | DatosNoCorrectosException err) {
			err.printStackTrace();
		}

		return e;
	}

	// eliminar empleado
	public boolean eliminar(int dniEmpleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM empleados WHERE dni=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, dniEmpleado);

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

}
