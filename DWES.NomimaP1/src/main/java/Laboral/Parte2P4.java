package Laboral;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora del codigo Main del Punto 4 de la Parte 2
 */
public class Parte2P4 {
	
	/**
	 * Llamada al codigo Main del Punto 4 de la Parte 2
	 * @throws Exception 
	 */
	public static void ejecucion() throws Exception {
		List<Empleado> empleados = new ArrayList<>();

		PrintWriter salidaEmpleado = null, salidaNomina = null;
		
		Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        try {
			salidaEmpleado = new PrintWriter("C:\\Users\\PC\\Documents\\workspace\\PracticaNomina\\media\\backupEmpleado.dat");
			salidaNomina = new PrintWriter("C:\\Users\\PC\\Documents\\workspace\\PracticaNomina\\media\\backupNomina.dat");
			
        	conn = ConBD.connection();
        	st = conn.createStatement();
        	
        	rs = st.executeQuery("SELECT * FROM empleados");
			while(rs.next()) {
        		String dni = rs.getString(2);
        		String nombre = rs.getString(1);
        		char sexo = rs.getString(3).charAt(0);
        		int categoria = rs.getInt(4);
        		double anyos = rs.getDouble(5);
        		try {
					empleados.add(new Empleado(dni, nombre, sexo, categoria, anyos));
				} catch (DatosNoCorrectosException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println("Empleado leido con exito.");
        	}
        	
        	for (int i = 0; i < empleados.size(); i++) {
        		Empleado e = empleados.get(i);
        		Nomina n = new Nomina();
        		double sueldo_final = n.sueldo(e);
        		
        		salidaEmpleado.println("DNI: " + e.dni + ", Nombre: " + e.nombre + ", Sexo: " + e.sexo + ", Categoria: " + e.getCategoria() + ", Anyos: " + e.anyos);
        		System.out.println("Backup de Empleados creado con exito.");
        		
        		salidaNomina.println("DNI: " + e.dni + ", Sueldo: " + sueldo_final);
        		System.out.println("Backup de Nominas creado con exito.");
			}
        	
        } catch(SQLException e){
            System.out.println("Ocurrió una excepción al conectar a la BD");
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                salidaEmpleado.close();
                salidaNomina.close();
                System.out.println("Terminado con exito.");
            } catch (SQLException ex) {
                System.out.println("Ocurrió una excepción al cerrar la BD");
            }
        }
        
	}

}
