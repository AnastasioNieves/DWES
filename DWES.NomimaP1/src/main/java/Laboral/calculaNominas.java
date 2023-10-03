package Laboral;

import java.util.Scanner;

public class calculaNominas {

	public static void main(String[] args) {

		try {
			Scanner sc = new Scanner(System.in);
			int option = 5;
			System.out.println("opcion 0 : Salir");
			System.out.println("opcion 1: Lectura y escritura BBDD");
			System.out.println("opcion 2 : Lectura desde fichero Empleados.txt y escritura en sueldos.dat");

			option = sc.nextInt();
			
			do {

				switch (option) {
				case 0:

					break;

				case 1:
					conBD.tryConect();
					
					break;

				case 2:
					lecturaFichero.lecFichero();
					break;

				default:

					System.out.println("Opcion invalida intentelo de nuevo");
					
					System.out.println("opcion 0 : Salir");
					System.out.println("opcion 1: Lectura y escritura BBDD");
					System.out.println("opcion 2 : Lectura desde fichero Empleados.txt y escritura en sueldos.dat");

					option = sc.nextInt();
					sc.close();
					break;
				}

			} while (option != 0);
			sc.close();

		} catch (Exception e) {
			e.getMessage();
		}

	}
}
