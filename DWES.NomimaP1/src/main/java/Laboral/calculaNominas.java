package Laboral;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase principal
 * 
 * @author Anastasio
 *
 */
public class calculaNominas {

    /**
     * 
     * Metodo principal para calcular las nominas
     * 
     * @throws Exception
     * @throws IOException
     * @throws NumberFormatException
     * 
     */
    public static void main(String[] args) throws NumberFormatException, IOException, Exception {
        try {
            // Array para el almacenamiento de empleados
            ArrayList<Empleado> empleados = new ArrayList<Empleado>();

            // Lectura de datos desde Empleados.txt utilizando java.io
            BufferedReader entradaArchivo = new BufferedReader(new FileReader("empleados.txt"));
            String linea;

            while ((linea = entradaArchivo.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String nombre = datos[0];
                    String dni = datos[1];
                    char sexo = datos[2].charAt(0);
                    int categoria = Integer.parseInt(datos[3]);
                    double salarioBase = Double.parseDouble(datos[4]);

                    Empleado empleado = new Empleado(nombre, dni, sexo, categoria, salarioBase);
                    empleados.add(empleado);
                } else if (datos.length == 3) {
                    String nombre = datos[0];
                    String dni = datos[1];
                    char sexo = datos[2].charAt(0);

                    Empleado empleado = new Empleado(nombre, dni, sexo);
                    empleados.add(empleado);
                } else {
                    throw new DatosNoCorrectosException("Datos Erroneos");
                }
                
                entradaArchivo.close();
            }

            

            // Escribir DNIs y salarios calculados en el archivo binario "sueldos.dat"
            DataOutputStream archivoSalida = new DataOutputStream(new FileOutputStream("sueldos.dat"));

            for (Empleado empleado : empleados) {
                Nomina nomina = new Nomina();

                // Calcular el sueldo
                double salario = nomina.sueldo(empleado);

                // Escribir el DNI y el sueldo en sueldos.dat
                archivoSalida.writeUTF(empleado.dni);
                archivoSalida.writeDouble(salario);
            }

            archivoSalida.close();

            // Mostrar información de los empleados y sus sueldos
            for (Empleado empleado : empleados) {
                escribe(empleado);
            }

            // Realizar cualquier otra operación o modificación de empleados si es necesario
            Empleado primerEmpleado = empleados.get(0);
            primerEmpleado.incrAnyo();
            primerEmpleado.setCategoria(9);
            escribe(primerEmpleado);

        } catch (DatosNoCorrectosException e) {
            System.out.println("Algun dato es invalido");
        }
    }

    /**
     * 
     * Metodo para escribir en consola los empleados y sus nominas
     * 
     * 
     **/
    private static void escribe(Empleado e) {
        Nomina n = new Nomina();
        e.imprime();
        System.out.println(n.sueldo(e));
    }
}
