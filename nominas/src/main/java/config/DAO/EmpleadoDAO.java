package config.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import config.Model.Empleado;
import config.Model.Nomina;

@Repository
public class EmpleadoDAO {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;

    public List<Empleado> obtenerEmpleados() {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE deleted = 'N'");
             ResultSet resultSet = statement.executeQuery()) {

            List<Empleado> lista = new ArrayList<>();

            while (resultSet.next()) {
                String dni = resultSet.getString(1);
                String nombre = resultSet.getString(2);
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

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empleados", e);
        }
    }

    public Empleado obtenerEmpleado(String dniEmpleado) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE deleted = 'N' AND dni = ?");
        ) {
            statement.setString(1, dniEmpleado);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Empleado e = new Empleado();
                    e.setDni(resultSet.getString(1));
                    e.setNombre(resultSet.getString(2));
                    e.setSexo(resultSet.getString(3).charAt(0));
                    try {
						e.setCategoria(resultSet.getInt(4));
					} catch (DatosNoCorrectosException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    e.setAnyos(resultSet.getDouble(5));
                    return e;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener empleado por DNI", e);
        }

        return null;
    }

    public boolean eliminar(String dniEmpleado) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("UPDATE empleados SET deleted='Y' WHERE dni=?");
        ) {
            statement.setString(1, dniEmpleado);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar empleado", e);
        }
    }

    public boolean editar(Empleado empleado) {
        try (Connection connection = obtenerConexion();
             PreparedStatement updateStatement = connection.prepareStatement("UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos=? WHERE dni=?");
             PreparedStatement nominasUpdateStatement = connection.prepareStatement("UPDATE nominas SET salario=? WHERE dni=?");
        ) {
            connection.setAutoCommit(false);

            updateStatement.setString(1, empleado.getNombre());
            updateStatement.setString(2, String.valueOf(empleado.getSexo()));
            updateStatement.setInt(3, empleado.getCategoria());
            updateStatement.setDouble(4, empleado.getAnyos());
            updateStatement.setString(5, empleado.getDni());

            boolean empleadoUpdate = updateStatement.executeUpdate() > 0;

            nominasUpdateStatement.setDouble(1, new Nomina().sueldo(empleado));
            nominasUpdateStatement.setString(2, empleado.getDni());

            boolean nominasUpdate = nominasUpdateStatement.executeUpdate() > 0;

            connection.commit();

            return empleadoUpdate && nominasUpdate;

        } catch (SQLException e) {
            throw new RuntimeException("Error al editar empleado", e);
        }
    }

    public List<Empleado> buscarPorDNI(String dniBusqueda) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE dni = ?");
        ) {
            statement.setString(1, dniBusqueda);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Empleado> lista = new ArrayList<>();

                while (resultSet.next()) {
                    String dni = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
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

                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleado por DNI", e);
        }
    }

    public List<Empleado> buscarPorNombre(String nombreBusqueda) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE nombre LIKE ?");
        ) {
            statement.setString(1, "%" + nombreBusqueda + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Empleado> lista = new ArrayList<>();

                while (resultSet.next()) {
                    String dni = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
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

                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados por nombre", e);
        }
    }

    public List<Empleado> buscarPorSexo(String sexoBusqueda) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE sexo = ?");
        ) {
            statement.setString(1, String.valueOf(sexoBusqueda));

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Empleado> lista = new ArrayList<>();

                while (resultSet.next()) {
                    String dni = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
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

                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados por sexo", e);
        }
    }

    public List<Empleado> buscarPorCategoria(String categoriaBusqueda) {
        try (Connection connection = obtenerConexion();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE categoria = ?");
        ) {
            statement.setInt(1, Integer.parseInt(categoriaBusqueda));

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Empleado> lista = new ArrayList<>();

                while (resultSet.next()) {
                    String dni = resultSet.getString(1);
                    String nombre = resultSet.getString(2);
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

                return lista;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar empleados por categoría", e);
        }
    }

    public List<Empleado> buscarPorAnyosTrabajados(String anyosBusqueda) {
        List<Empleado> lista = new ArrayList<>();

        // Validar si la cadena de años es un número válido
        if (anyosBusqueda != null && !anyosBusqueda.trim().isEmpty()) {
            try {
                double anyos = Double.parseDouble(anyosBusqueda);

                try (Connection connection = obtenerConexion();
                     PreparedStatement statement = connection.prepareStatement("SELECT * FROM empleados WHERE anyos = ?");
                ) {
                    statement.setDouble(1, anyos);

                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            String dni = resultSet.getString(1);
                            String nombre = resultSet.getString(2);
                            char sexo = resultSet.getString(3).charAt(0);
                            int categoria = resultSet.getInt(4);

                            try {
								lista.add(new Empleado(dni, nombre, sexo, categoria, anyos));
							} catch (DatosNoCorrectosException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        }
                    }
                }

            } catch (SQLException | NumberFormatException e) {
                throw new RuntimeException("Error al buscar empleados por años trabajados", e);
            }
        }

        return lista;
    }
    
    


private Connection obtenerConexion() throws SQLException {
    try (// Configura el DataSource con las propiedades del archivo
	BasicDataSource basicDataSource = new BasicDataSource()) {
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(dbUsername);
		basicDataSource.setPassword(dbPassword);
		basicDataSource.setDriverClassName(dbDriverClassName);

		return basicDataSource.getConnection();
	}
}
}