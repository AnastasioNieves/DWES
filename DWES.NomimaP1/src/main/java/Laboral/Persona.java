
package Laboral;

/*
 * 
 * Clase publica persona para generar una persona
 * 
 * 
 */

public class Persona {

	public String nombre, dni;

	public char sexo;

	/**
	 * Constructor completo de persona
	 * 
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * 
	 */
	public Persona(String nombre, String dni, char sexo) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.sexo = sexo;
	}

	/**
	 * Constructor parcial de persona
	 * 
	 * @param nombre
	 * @param sexo
	 * 
	 */
	public Persona(String nombre, char sexo) {
		super();
		this.nombre = nombre;
		this.sexo = sexo;
	}

	/**
	 * @param dni estable el valor de dni
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * 
	 * Metodo para la impresion en pantalla de los datos del empleado
	 * 
	 * @param nombre
	 * @param dni
	 * 
	 * 
	 */

	public void Imprime() {

		System.out.printf("Nombre : %d /n", nombre);

		System.out.printf("Dni : %d /n", dni);

	}

}
