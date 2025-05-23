package interfaz;

import java.util.ArrayList;
import java.util.Scanner;

import dao.CursoDAO;
import entidades.Curso;

/**
 * Clase principal que gestiona la interfaz de usuario para el sistema de
 * gestión de cursos.
 */
public class Main {

	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		Curso cur;
		int idCurso, idProfesor;
		CursoDAO curDAO = new CursoDAO();
		int opcion = 0;

		do {

			System.out.println("\n-- GESTOR DE CURSOS --");
			menu();
			try {
				opcion = scanner.nextInt();
				scanner.nextLine(); // Limpiar buffer

				switch (opcion) {
				case 1 -> {
					// Crear curso
					cur = datosCurso();
					if (cur != null) {
						curDAO.crearCurso(cur);
					}
				}
				case 2 -> {
					// Listar todos los cursos
					listarTodosCursos(curDAO);
				}
				case 3 -> {
					// Asignar profesor a curso
					asignarProfesorACurso(curDAO);
				}
				case 4 -> {
					// Listar estudiantes de un curso
					listarEstudiantesDeCurso(curDAO);
				}
				case 5 -> {
					// Eliminar curso
					idCurso = pedirIdCurso();
					curDAO.eliminarCurso(idCurso);
				}
				case 6 -> {
					System.out.println("Saliendo...");
				}
				default -> {
					System.out.println("Opción no válida.");
				}
				}
			} catch (Exception e) {
				System.out.println("Error: Debe introducir un número válido.");
				scanner.nextLine(); // Limpiar buffer en caso de error
			}

		} while (opcion != 6);

	}

	/**
	 * Solicita al usuario los datos para crear un nuevo curso.
	 * 
	 * @return objeto Curso con los datos introducidos
	 */
	private static Curso datosCurso() {
		Curso cur = null;

		try {
			String nombre;
			String descripcion;
			int añoEscolar;

			System.out.print("Introduce el nombre del curso: ");
			nombre = scanner.nextLine().trim();

			if (nombre.isEmpty()) {
				System.out.println("Error: El nombre del curso no puede estar vacío.");
				return null;
			}

			System.out.print("Introduce la descripción del curso: ");
			descripcion = scanner.nextLine().trim();

			System.out.print("Introduce el año escolar del curso: ");
			añoEscolar = scanner.nextInt();
			scanner.nextLine(); // Limpiar buffer

			if (añoEscolar < 2020 || añoEscolar > 2030) {
				System.out.println("Error: El año escolar debe estar entre 2020 y 2030.");
				return null;
			}

			cur = new Curso(nombre, descripcion, añoEscolar);

		} catch (Exception e) {
			System.out.println("Error al introducir los datos del curso: " + e.getMessage());
			scanner.nextLine(); // Limpiar buffer
		}

		return cur;
	}

	/**
	 * Solicita al usuario el ID de un curso.
	 * 
	 * @return ID del curso introducido
	 */
	public static int pedirIdCurso() {

		int idCurso = 0;

		try {
			System.out.print("Introduce el ID del curso: ");
			idCurso = scanner.nextInt();
			scanner.nextLine(); // Limpiar buffer
		} catch (Exception e) {
			System.out.println("Error: Debe introducir un número válido.");
			scanner.nextLine(); // Limpiar buffer
		}

		return idCurso;
	}

	/**
	 * Solicita al usuario el ID de un profesor.
	 * 
	 * @return ID del profesor introducido
	 */
	public static int pedirIdProfesor() {

		int idProfesor = 0;

		try {
			System.out.print("Introduce el ID del profesor: ");
			idProfesor = scanner.nextInt();
			scanner.nextLine(); // Limpiar buffer
		} catch (Exception e) {
			System.out.println("Error: Debe introducir un número válido.");
			scanner.nextLine(); // Limpiar buffer
		}

		return idProfesor;
	}

	/**
	 * Muestra el menú de opciones disponibles.
	 */
	public static void menu() {
		System.out.println("1. Crear curso");
		System.out.println("2. Listar todos los cursos");
		System.out.println("3. Asignar un profesor a un curso");
		System.out.println("4. Listar estudiantes de un curso");
		System.out.println("5. Eliminar curso");
		System.out.println("6. Salir");
		System.out.print("Seleccione una opción: ");
	}

	/**
	 * Lista todos los cursos disponibles en el sistema.
	 * 
	 * @param curDAO objeto DAO para acceder a la base de datos
	 */
	private static void listarTodosCursos(CursoDAO curDAO) {
		System.out.println("\n-- LISTA DE CURSOS --");

		ArrayList<Curso> cursos = curDAO.listarCursos();

		if (cursos.isEmpty()) {
			System.out.println("No hay cursos registrados en el sistema.");
		} else {
			for (Curso curso : cursos) {
				System.out.println(curso.toString());
			}
			System.out.println("\nTotal de cursos: " + cursos.size());
		}
	}

	/**
	 * Gestiona la asignación de un profesor a un curso.
	 * 
	 * @param curDAO objeto DAO para acceder a la base de datos
	 */
	private static void asignarProfesorACurso(CursoDAO curDAO) {
		System.out.println("\n-- ASIGNAR PROFESOR A CURSO --");

		int idProfesor = pedirIdProfesor();
		int idCurso = pedirIdCurso();

		if (idProfesor > 0 && idCurso > 0) {
			curDAO.asignarProfesorACurso(idProfesor, idCurso);
		} else {
			System.out.println("Error: Los IDs deben ser números positivos.");
		}
	}

	/**
	 * Lista todos los estudiantes matriculados en un curso específico.
	 * 
	 * @param curDAO objeto DAO para acceder a la base de datos
	 */
	private static void listarEstudiantesDeCurso(CursoDAO curDAO) {
		System.out.println("\n-- ESTUDIANTES DE UN CURSO --");

		int idCurso = pedirIdCurso();

		if (idCurso > 0) {
			ArrayList<String> estudiantes = curDAO.listarEstudiantesCurso(idCurso);

			if (estudiantes.isEmpty()) {
				System.out.println("No hay estudiantes matriculados en este curso o el curso no existe.");
			} else {
				System.out.println("\nEstudiantes matriculados en el curso con ID " + idCurso + ":");
				for (String estudiante : estudiantes) {
					System.out.println("- " + estudiante);
				}
				System.out.println("\nTotal de estudiantes: " + estudiantes.size());
			}
		} else {
			System.out.println("Error: El ID del curso debe ser un número positivo.");
		}
	}
}