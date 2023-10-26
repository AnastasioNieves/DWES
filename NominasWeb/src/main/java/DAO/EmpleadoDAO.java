package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Model.Empleado;

public class EmpleadoDAO {
    private static Connection connection;
    private static Statement statement;
    private static boolean estadoOperacion;

    // obtener conexion pool
    private static Connection obtenerConexion() throws SQLException {
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
            
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String dni = resultSet.getString(2);
                String nombre = resultSet.getString(1);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
                try {
					lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
					
					
					
					
				} catch (DatosNoCorrectosException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            
          

        } catch (SQLException err) {
            err.printStackTrace();
        }

        return lista;
    }

 // obtener empleado por dni
    public Empleado obtenerEmpleadoDNI(String dniEmpleado) throws SQLException, DatosNoCorrectosException {
        ResultSet resultSet = null;
        Empleado e = new Empleado();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM empleados WHERE dni = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dniEmpleado);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                e.setDni(resultSet.getString(2));
                e.setNombre(resultSet.getString(1));
                e.setSexo(resultSet.getString(3).charAt(0));
                e.setCategoria(resultSet.getInt(4));
                e.setAnyos(resultSet.getDouble(5));
            }

        } catch (SQLException err) {
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
            sql = "DELETE FROM empleados WHERE dni = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, dniEmpleado);

            estadoOperacion = preparedStatement.executeUpdate() > 0;
            connection.commit();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }

        return estadoOperacion;
    }

}
