package config.Model;

/**
 * Clase Persona
 */
public class Persona {

    /**
     * Declaración de variables
     */
    protected String nombre;
    private String dni;
    protected char sexo;

    /**
     * Constructor Persona completo
     * 
     * @param nombre
     * @param dni
     * @param sexo
     */
    public Persona(String dni, String nombre, char sexo) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.sexo = sexo;
    }

    /**
     * Constructor Persona sin dni
     * 
     * @param nombre
     * @param sexo
     */
    public Persona(String nombre, char sexo) {
        super();
        this.nombre = nombre;
        this.sexo = sexo;
    }

    public Persona() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    /**
     * Setter de dni
     * 
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Salida por consola de nombre y dni de la persona
     */
    public void imprime() {
        System.out.println("Nombre: " + nombre);
        System.out.println("DNI: " + dni);
    }
}
