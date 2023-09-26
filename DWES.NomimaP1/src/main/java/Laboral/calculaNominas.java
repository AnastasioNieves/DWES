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
		/*Lectura por texto plano*/
		
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
		    
		    //  consultas SQL para obtener los datos de los empleados y nóminas.
		    
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

		
		// Crear una consulta SQL para obtener los empleados y sus datos
		String query = "SELECT nombre, dni, sexo, categoria, salario_base FROM Empleados";
		// Crear una declaración
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Ejecutar la consulta y obtener un conjunto de resultados
		ResultSet resultSet = stmt.executeQuery(query);

		// Iterar a través de los resultados y crear objetos Empleado
		
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();
		
		while (resultSet.next()) {
		    String nombre = resultSet.getString("nombre");
		    String dni = resultSet.getString("dni");
		    char sexo = resultSet.getString("sexo").charAt(0);
		    int categoria = resultSet.getInt("categoria");
		    double salarioBase = resultSet.getDouble("salario_base");
		    
		    Empleado empleado;
			try {
				empleado = new Empleado(nombre, dni, sexo, categoria, salarioBase);
				 empleados.add(empleado);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
		}
		
		// Crear una consulta SQL para actualizar el sueldo en la tabla Nominas
		String updateQuery = "UPDATE Nominas SET sueldo = ? WHERE dni = ?";
		PreparedStatement updateStatement = conn.prepareStatement(updateQuery);

		for (Empleado empleado : empleados) {
		    Nomina nomina = new Nomina();
		    double salario = nomina.sueldo(empleado);
		    
		    // Actualizar la tabla Nominas con el sueldo calculado
		    updateStatement.setDouble(1, salario);
		    updateStatement.setString(2, empleado.dni);
		    updateStatement.executeUpdate();
		}

		
		
		
		
		
		
		
		
		
	}

	private static void escribe(Empleado e) {
		Nomina n = new Nomina();
		e.imprime();
		System.out.println(n.sueldo(e));
	}
}
