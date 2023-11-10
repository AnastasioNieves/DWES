package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Model.Empleado;
import Model.Nomina;

public class EmpleadoDAO {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }

    public List<Empleado> obtenerEmpleados() throws SQLException {
        ResultSet resultSet = null;
        List<Empleado> lista = new ArrayList<>();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            sql = "SELECT * FROM empleados WHERE deleted = 'N'";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
               
                lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
            }

            statement.close();
            connection.close();
        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return lista;
    }


    public Empleado obtenerEmpleado(String dniEmpleado) throws SQLException {
        ResultSet resultSet = null;
        Empleado e = new Empleado();

        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
        	 sql = "SELECT * FROM empleados WHERE deleted = 'N'";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dniEmpleado);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                e.setNombre(resultSet.getString(1));
                e.setDni(resultSet.getString(2));
                e.setSexo(resultSet.getString(3).charAt(0));
                e.setCategoria(resultSet.getInt(4));
                e.setAnyos(resultSet.getDouble(5));
            }
            
            statement.close();
            connection.close();

        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return e;
    }

    public boolean eliminar(String dniEmpleado) throws SQLException {
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
        	
        	
            sql = "UPDATE empleados SET deleted='Y' WHERE dni=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dniEmpleado);

            estadoOperacion = statement.executeUpdate() > 0;

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    public boolean editar(Empleado empleado) throws SQLException {
        Nomina nomina = new Nomina();
        String sql = null;
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            connection.setAutoCommit(false);
            sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?";
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, empleado.getNombre());
            statement.setString(2, String.valueOf(empleado.getSexo()));
            statement.setInt(3, empleado.getCategoria());
            statement.setDouble(4, empleado.getAnyos());
            statement.setString(6, empleado.getDni());
           

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();

            sql = "UPDATE nominas SET salario=? WHERE dni=?";
            statement = connection.prepareStatement(sql);

            statement.setDouble(1, nomina.sueldo(empleado));
            statement.setString(2, empleado.getDni());

            estadoOperacion = statement.executeUpdate() > 0;
            connection.commit();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadoOperacion;
    }

    public List<Empleado> buscarPorDNI(String dniBusqueda) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "SELECT * FROM empleados WHERE dni = ?";
        estadoOperacion = false;

        try {
            connection = obtenerConexion();
            statement = connection.prepareStatement(sql);
            statement.setString(1, dniBusqueda);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
              
                lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
            }

            statement.close();
            connection.close();

        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return lista;
    }

    public List<Empleado> buscarPorNombre(String nombreBusqueda) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "SELECT * FROM empleados WHERE nombre LIKE ?";
        estadoOperacion = false;

        try {
            connection = obtenerConexion();
            statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + nombreBusqueda + "%");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
                
                lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
            }

            statement.close();
            connection.close();

        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return lista;
    }

    public List<Empleado> buscarPorSexo(String sexoBusqueda) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "SELECT * FROM empleados WHERE sexo = ?";
        estadoOperacion = false;

        try {
            connection = obtenerConexion();
            statement = connection.prepareStatement(sql);
            statement.setString(1, String.valueOf(sexoBusqueda));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
                
                lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
            }

            statement.close();
            connection.close();

        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return lista;
    }

    public List<Empleado> buscarPorCategoria(String categoriaBusqueda) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "SELECT * FROM empleados WHERE categoria = ?";
        estadoOperacion = false;

        try {
            connection = obtenerConexion();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(categoriaBusqueda));
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
                char sexo = resultSet.getString(3).charAt(0);
                int categoria = resultSet.getInt(4);
                double anyos = resultSet.getDouble(5);
                
                lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
            }

            statement.close();
            connection.close();

        } catch (SQLException | DatosNoCorrectosException err) {
            err.printStackTrace();
        }

        return lista;
    }
    public List<Empleado> buscarPorAnyosTrabajados(String anyosBusqueda) throws SQLException {
        List<Empleado> lista = new ArrayList<>();
        ResultSet resultSet = null;

        // Validar si la cadena de años es un número válido
        if (anyosBusqueda != null && !anyosBusqueda.trim().isEmpty()) {
            try {
                double anyos = Double.parseDouble(anyosBusqueda);

                String sql = "SELECT * FROM empleados WHERE anyos = ?";
                estadoOperacion = false;

                try {
                    connection = obtenerConexion();
                    statement = connection.prepareStatement(sql);
                    statement.setDouble(1, anyos);
                    resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        String dni = resultSet.getString(1);
                        String nombre = resultSet.getString(2);
                        char sexo = resultSet.getString(3).charAt(0);
                        int categoria = resultSet.getInt(4);
                        
                        lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
                    }

                    statement.close();
                    connection.close();

                } catch (SQLException | DatosNoCorrectosException err) {
                    // Manejar la excepción aquí
                    err.printStackTrace();
                    // Puedes agregar el mensaje de error a una lista de errores si es necesario
                    // errores.add(err.getMessage());
                }
            } catch (NumberFormatException e) {
                // Manejar la excepción de formato numérico aquí
                e.printStackTrace();
                // Puedes agregar el mensaje de error a una lista de errores si es necesario
                // errores.add(e.getMessage());
            }
        }

        return lista;
    }


}
