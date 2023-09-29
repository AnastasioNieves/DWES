package Laboral;

import javax.management.InvalidAttributeValueException;

/** Clase para definir un empleado que extiende de persona */
public class Empleado extends Persona {

	/*
	 * Variable para establecer categoria
	 * 
	 */
	private int categoria;
	public double anyos;

	/**
	 * Constructor para establecer un empleado completo
	 * 
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @param categoria
	 * @param anyos
	 * @throws InvalidAttributeValueException error lanzado a la entrada de un dato
	 *                                        erroneo
	 * 
	 * 
	 */
	public Empleado(String nombre, String dni, char sexo, int categoria, double anyos) throws java.lang.Exception {
		super(nombre, dni, sexo);

		if (categoria >= 1 && categoria <= 10) {

			this.categoria = categoria;

		} else {

			this.categoria = 1;
		}

		if (anyos >= 0) {
			this.anyos = anyos;
		} else {

			throw new DatosNoCorrectosException("Dato Erroneo");

		}

	}

	/**
	 * 
	 * Constructor para establecer un empleado a partir de la clase persona
	 * 
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * 
	 */

	public Empleado(String nombre, String dni, char sexo) {
		super(nombre, dni, sexo);

		this.categoria = 1;
		this.anyos = 0;
		
	}

	/**
	 * @return the categoria
	 */
	public int getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(int categoria) {
		this.categoria = categoria;
	}
	
	

	/**
	 * 
	 * Metodo para incrementar los años
	 * @return 
	 * 
	 */
	public double incrAnyo() {

		return this.anyos = anyos + 1;

	}

	/**
	 * 
	 * Metodo para la impresion del empleado
	 * 
	 */
	public void imprime() {

		System.out.println("Nombre : " + nombre);
		System.out.println("Dni : " + dni);
		System.out.println("Sexo : " + sexo);
		System.out.println("Categoria : " + categoria);
		System.out.println("Anyos trabajados : " + anyos);

	}

	

	

}
