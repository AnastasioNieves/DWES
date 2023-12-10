package config.Model;

import config.DAO.DatosNoCorrectosException;

/**
 * Clase Empleado que extiende de Persona
 */
public class Empleado extends Persona {

    /**
     * Declaración de variables Variable categoria solo admite numeros entre 1 y 10 Variable anyos solo admite numeros mayores o iguales a 0
     */

    private int categoria;
    public double anyos;
    public boolean estadoOperacion;
    public char deleted;

    public char getDeleted() {
        return deleted;
    }

    public void setDeleted(char deleted) {
        this.deleted = deleted;
    }

    /**
     * Constructor Empleado completo
     * 
     * @param nombre
     * @param dni
     * @param sexo
     * @param categoria
     * @param anyos
     * @throws DatosNoCorrectosException categoria y/o anyos incorrecto
     */
    public Empleado(String dni, String nombre, char sexo, int categoria, double anyos) throws DatosNoCorrectosException {
        super(dni, nombre, sexo);
        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException("ERROR: La categoría debe estar entre 1 y 10");
        } else {
            this.categoria = categoria;
        }
        if (anyos < 0) {
            throw new DatosNoCorrectosException("ERROR: Los años deben ser mayores a 0");
        } else {
            this.anyos = anyos;
        }
    }

    /**
     * Constructor Empleado sin entrada de categoría Por defecto categoría = 1 y años = 0
     * 
     * @param nombre
     * @param dni
     * @param sexo
     */
    public Empleado(String dni, String nombre, char sexo) {
        super(dni, nombre, sexo);
        this.categoria = 1;
        this.anyos = 0;
    }

    public void setAnyos(double anyos) {
        this.anyos = anyos;
    }

    public Empleado() {
        // TODO Auto-generated constructor stub
    }

    /**
     * Getter de categoría
     * 
     * @return
     */
    public int getCategoria() {
        return categoria;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /**
     * Setter de categoría
     * 
     * @param categoria
     * @throws DatosNoCorrectosException
     */
    public void setCategoria(int categoria) throws DatosNoCorrectosException {
        if (categoria < 1 || categoria > 10) {
            throw new DatosNoCorrectosException("ERROR: La categoría debe estar entre 1 y 10");
        } else {
            this.categoria = categoria;
        }
    }

    /**
     * Incremento de años trabajados por el empleado
     */
    public void incrAnyo() {
        anyos++;
    }

    public double getAnyos() {
        return anyos;
    }

    /**
     * Salida por consola de todos los datos del empleado
     */
    public void imprime() {
        System.out.println("Nombre: " + nombre);
        System.out.println("DNI: " + getDni());
        System.out.println("Sexo: " + sexo);
        System.out.println("Categoría: " + categoria);
        System.out.println("Años: " + anyos);
    }
}
