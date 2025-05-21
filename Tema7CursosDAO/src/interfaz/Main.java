package interfaz;

import java.util.Scanner;

import dao.CursoDAO;
import entidades.Curso;

public class Main {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		Curso cur;
		int idCurso;
		CursoDAO curDAO = new CursoDAO();
		int opcion = 0;

		do {

			System.out.println("-- GESTOR DE CURSOS --");
			menu();
			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1 -> {
				cur = datosCurso();
				curDAO.crearCurso(cur);
			}
			case 2 -> {

			}
			case 3 -> {

			}
			case 4 -> {

			}
			case 5 -> {
				idCurso = pedirIdCurso();
				curDAO.eliminarCurso(idCurso);
			}
			case 6 -> {
				System.out.println("Saliendo...");
			}

			}

		} while (opcion != 6);

	}

	private static Curso datosCurso() {
		Curso cur = null;

		String nombre;
		String descripcion;
		int añoEscolar;

		System.out.println("Introduce el nombre del curso: ");
		nombre = scanner.nextLine();

		System.out.println("Introduce la descripción del curso: ");
		descripcion = scanner.nextLine();

		System.out.println("Introduce el año escolar del curso: ");
		añoEscolar = scanner.nextInt();

		cur = new Curso(nombre, descripcion, añoEscolar);

		return cur;
	}

	public static int pedirIdCurso() {

		int idCurso;

		System.out.println("Introduce el id del curso: ");
		idCurso = scanner.nextInt();

		return idCurso;
	}

	public static void menu() {
		System.out.println("1. Crear curso");
		System.out.println("2. Listar todos los cursos");
		System.out.println("3. Asignar un profesor a todos los cursos");
		System.out.println("4. Listar un estudiante de un curso");
		System.out.println("5. Eliminar curso");
		System.out.println("6. Salir");
	}

}
