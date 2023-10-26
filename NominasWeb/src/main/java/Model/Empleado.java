package Model;

import DAO.DatosNoCorrectosException;

/**
 * Clase Empleado que extiende de Persona
 */
public class Empleado extends Persona
{
	
	/**
	 * Declaracion de variables
	 * Variable categoria solo admite numeros entre 1 y 10
	 * Variable anyos solo admite numeros mayores o iguales a 0
	 */
	
	private int categoria;
	public double anyos;
	
	
	/**
	 * Constructor Empleado completo
	 * @param nombre
	 * @param dni
	 * @param sexo
	 * @param categoria
	 * @param anyos
	 * @throws DatosNoCorrectosException categoria y/o anyos incorrecto
	 */
	public Empleado(String nombre, String dni, char sexo, int categoria, double anyos) throws DatosNoCorrectosException {
		super(nombre, dni, sexo);
		if (categoria < 1 || categoria > 10) {
			throw new DatosNoCorrectosException("ERROR: La categoria debe estar entre 1 y 10");
		} else {
			this.categoria = categoria;
		}
		if ((anyos < 0)) {
			throw new DatosNoCorrectosException("ERROR: La categoria debe ser mayor a 0");
		} else {
			this.anyos = anyos;
		}
	}

	/**
	 * COnstructor Empleado sin entrada de categoria
	 * Por defecto categoria = 1 y anyos = 0
	 * @param nombre
	 * @param dni
	 * @param sexo
	 */
	public Empleado(String nombre, String dni, char sexo) {
		super(nombre, dni, sexo);
		this.categoria = 1;
		this.anyos = 0;
	}
	

	

	public Empleado() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter de categoria
	 * @return
	 */
	public int getCategoria() {
		return categoria;
	}
	
	/**
	 * Setter de categoria
	 * @param categoria
	 * @throws DatosNoCorrectosException 
	 */
	public void setCategoria(int categoria) throws DatosNoCorrectosException {
		if (categoria < 1 || categoria > 10) {
			throw new DatosNoCorrectosException("ERROR: La categoria debe estar entre 1 y 10");
		} else {
			this.categoria = categoria;
		}
	}
	
	/**
	 * Incremento de anyos trabajados por el empleado
	 */
	public void incrAnyo() {
		anyos++;
	}
	
	/**
	 * Salida por consola de todos los datos del empleado
	 */
	public void imprime() {
		System.out.println("Nombre: " + nombre);
		System.out.println("DNI: " + dni);
		System.out.println("Sexo: " + sexo);
		System.out.println("Categoria: " + categoria);
		System.out.println("Anyos: " + anyos);
	}

}
