package Laboral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase contenedora del codigo Main del Punto 3 de la Parte 2
 */
public class AltaEmpleado{
	
	/**
	 * Llamada al codigo Main del Punto 3 de la Parte 2
	 */
	public static void ejecucion() {
		
		Connection conn = null;
        Statement st = null;
		
		try {
			conn = ConBD.connection();
			st = conn.createStatement();
			
			File archivo = new File ("C:\\Users\\PC\\Documents\\workspace\\PracticaNomina\\media\\empleadosNuevos.txt");
			FileReader fr = new FileReader (archivo);
			BufferedReader br = new BufferedReader(fr);
			
			altaEmpleado(br, conn, st);
        	
        } catch(SQLException e){
            System.out.println("Ocurrió una excepción al conectar a la BD");
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatosNoCorrectosException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	public static void altaEmpleado(BufferedReader br, Connection conn, Statement st) throws NumberFormatException, IOException, DatosNoCorrectosException, SQLException {
		List<Empleado> empleados = new ArrayList<>();
		
		char sexoParse;
		int categoriaParse;
		double anyosParse;

		String linea;
		
		while ((linea=br.readLine())!=null) {
			String[] seccion = linea.split(",");
			
			sexoParse = seccion[2].charAt(0);
			categoriaParse = Integer.parseInt(seccion[3]);
			anyosParse = Double.parseDouble(seccion[4]);
			
			try {
				empleados.add(new Empleado(seccion[0], seccion[1], sexoParse, categoriaParse, anyosParse));
			} catch (Exception e) {
				
			}
			System.out.println("Empleado añadido con exito.");
		}
		br.close();
    	
    	for (int i = 0; i < empleados.size(); i++) {
    		Empleado e = empleados.get(i);
    		Nomina n = new Nomina();
    		double sueldo_final = n.sueldo(e);
    		
    		int numFilasEmpleado = st.executeUpdate("INSERT INTO empleados (dni, nombre, sexo, categoria, anyos) VALUES ('" + e.dni + "', '" + e.nombre + "', '" + e.sexo + "', " + e.getCategoria() + ", " + e.anyos + ")");
    		System.out.println("Empleado insertado ("+ numFilasEmpleado +" filas) con exito.");
    		
    		int numFilasNomina = st.executeUpdate("INSERT INTO nominas (empleado_dni, nomina) VALUES ('"+ e.dni +"', "+ sueldo_final +")");
    		System.out.println("Nomina insertada ("+ numFilasNomina +" filas) con exito.");
		}
	}

}
