package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import constantes.Constantes;
import entidades.Curso;

/**
 * Clase DAO para gestionar las operaciones CRUD de cursos en la base de datos.
 * 
 * @author manuel.garfia
 */
public class CursoDAO {

	private Connection conexion;

	/**
	 * Constructor que establece la conexión con la base de datos.
	 */
	public CursoDAO() {
		try {
			conexion = DriverManager.getConnection(Constantes.URL, Constantes.USUARIO, Constantes.CONTRASEÑA);
		} catch (SQLException e) {
			System.out.println("Error en la base de datos: " + e.getMessage());
		}
	}

	/**
	 * Obtiene la conexión actual con la base de datos.
	 * 
	 * @return conexion objeto Connection
	 */
	public Connection getConexion() {
		return conexion;
	}

	/**
	 * Inserta un nuevo curso en la base de datos.
	 * 
	 * @param cur objeto Curso a insertar
	 */
	public void crearCurso(Curso cur) {
		String sql = "INSERT INTO cursos (nombre, descripcion, año_escolar) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setString(1, cur.getNombre());
			ps.setString(2, cur.getDescripcion());
			ps.setInt(3, cur.getAñoEscolar());

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Curso creado exitosamente.");
			}
		} catch (SQLException e) {
			System.out.println("Error al insertar el nuevo curso: " + e.getMessage());
		}
	}

	/**
	 * Obtiene todos los cursos de la base de datos.
	 * 
	 * @return ArrayList de objetos Curso
	 */
	public ArrayList<Curso> listarCursos() {

		ArrayList<Curso> lista = new ArrayList<>();
		Curso curso;

		String sql = "SELECT id_curso, nombre, descripcion, año_escolar FROM cursos";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				curso = new Curso(rs.getString("nombre"), rs.getString("descripcion"), rs.getInt("año_escolar"));
				curso.setId(rs.getInt("id_curso")); // Establecer el ID obtenido de la BD
				lista.add(curso);
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta: " + e.getMessage());
		}

		return lista;
	}

	/**
	 * Asigna un profesor a un curso específico.
	 * 
	 * @param idProfesor ID del profesor
	 * @param idCurso    ID del curso
	 * @return true si la asignación fue exitosa, false en caso contrario
	 */
	public boolean asignarProfesorACurso(int idProfesor, int idCurso) {

		// Verificar que el profesor existe
		if (!existeProfesor(idProfesor)) {
			System.out.println("Error: El profesor con ID " + idProfesor + " no existe.");
			return false;
		}

		// Verificar que el curso existe
		if (!existeCurso(idCurso)) {
			System.out.println("Error: El curso con ID " + idCurso + " no existe.");
			return false;
		}

		// Verificar si ya existe la asignación
		if (existeAsignacion(idProfesor, idCurso)) {
			System.out.println("Error: El profesor ya está asignado a este curso.");
			return false;
		}

		String sql = "INSERT INTO cursoprofesor (id_profesor, id_curso) VALUES (?, ?)";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idProfesor);
			ps.setInt(2, idCurso);

			int filasAfectadas = ps.executeUpdate();

			if (filasAfectadas > 0) {
				System.out.println("Profesor asignado al curso exitosamente.");
				return true;
			}

		} catch (SQLException e) {
			System.out.println("Error al asignar profesor al curso: " + e.getMessage());
		}

		return false;
	}

	/**
	 * Lista todos los estudiantes matriculados en un curso específico.
	 * 
	 * @param idCurso ID del curso
	 * @return ArrayList de Strings con información de estudiantes
	 */
	public ArrayList<String> listarEstudiantesCurso(int idCurso) {

		ArrayList<String> estudiantes = new ArrayList<>();

		// Verificar que el curso existe
		if (!existeCurso(idCurso)) {
			System.out.println("Error: El curso con ID " + idCurso + " no existe.");
			return estudiantes;
		}

		String sql = "SELECT e.id_estudiante, e.nombre, e.apellido, e.email " + "FROM estudiantes e "
				+ "INNER JOIN matriculas m ON e.id_estudiante = m.id_estudiante " + "WHERE m.id_curso = ?";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String infoEstudiante = "ID: " + rs.getInt("id_estudiante") + " - " + rs.getString("nombre") + " "
						+ rs.getString("apellido") + " - Email: " + rs.getString("email");
				estudiantes.add(infoEstudiante);
			}

		} catch (SQLException e) {
			System.out.println("Error al listar estudiantes del curso: " + e.getMessage());
		}

		return estudiantes;
	}

	/**
	 * Elimina un curso de la base de datos junto con todas sus relaciones. Elimina
	 * automáticamente calificaciones, matrículas y asignaciones de profesores.
	 * 
	 * @param idCurso ID del curso a eliminar
	 * @return número de filas eliminadas del curso (0 si no existía, 1 si se
	 *         eliminó)
	 */
	public int eliminarCurso(int idCurso) {

		int filasEliminadas = 0;

		try {
			// Verificar que el curso existe antes de intentar eliminar
			if (!existeCurso(idCurso)) {
				System.out.println("No se encontró ningún curso con el ID especificado.");
				return 0;
			}

			// Desactivar autocommit para hacer la operación transaccional
			conexion.setAutoCommit(false);

			// 1. Eliminar calificaciones relacionadas con el curso
			String sqlCalificaciones = "DELETE FROM calificaciones WHERE id_curso = ?";
			PreparedStatement psCalificaciones = conexion.prepareStatement(sqlCalificaciones);
			psCalificaciones.setInt(1, idCurso);
			int calificacionesEliminadas = psCalificaciones.executeUpdate();

			// 2. Eliminar matrículas del curso
			String sqlMatriculas = "DELETE FROM matriculas WHERE id_curso = ?";
			PreparedStatement psMatriculas = conexion.prepareStatement(sqlMatriculas);
			psMatriculas.setInt(1, idCurso);
			int matriculasEliminadas = psMatriculas.executeUpdate();

			// 3. Eliminar asignaciones de profesores al curso
			String sqlCursoProfesor = "DELETE FROM cursoprofesor WHERE id_curso = ?";
			PreparedStatement psCursoProfesor = conexion.prepareStatement(sqlCursoProfesor);
			psCursoProfesor.setInt(1, idCurso);
			int asignacionesEliminadas = psCursoProfesor.executeUpdate();

			// 4. Eliminar el curso
			String sqlCurso = "DELETE FROM cursos WHERE id_curso = ?";
			PreparedStatement psCurso = conexion.prepareStatement(sqlCurso);
			psCurso.setInt(1, idCurso);
			filasEliminadas = psCurso.executeUpdate();

			// Confirmar la transacción
			conexion.commit();

			// Mostrar información de lo que se eliminó
			System.out.println("Curso eliminado exitosamente.");
			if (calificacionesEliminadas > 0) {
				System.out.println("- Se eliminaron " + calificacionesEliminadas + " calificaciones asociadas.");
			}
			if (matriculasEliminadas > 0) {
				System.out.println("- Se eliminaron " + matriculasEliminadas + " matrículas asociadas.");
			}
			if (asignacionesEliminadas > 0) {
				System.out.println("- Se eliminaron " + asignacionesEliminadas + " asignaciones de profesores.");
			}

			// Reactivar autocommit
			conexion.setAutoCommit(true);

		} catch (SQLException e) {
			try {
				// En caso de error, hacer rollback
				conexion.rollback();
				conexion.setAutoCommit(true);
				System.out.println("Error al eliminar el curso. Se han revertido los cambios: " + e.getMessage());
			} catch (SQLException rollbackEx) {
				System.out.println("Error crítico al hacer rollback: " + rollbackEx.getMessage());
			}
		}

		return filasEliminadas;
	}

	/**
	 * Verifica si existe un profesor con el ID especificado.
	 * 
	 * @param idProfesor ID del profesor
	 * @return true si existe, false en caso contrario
	 */
	private boolean existeProfesor(int idProfesor) {
		String sql = "SELECT COUNT(*) FROM profesores WHERE id_profesor = ?";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idProfesor);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			System.out.println("Error al verificar profesor: " + e.getMessage());
		}

		return false;
	}

	/**
	 * Verifica si existe un curso con el ID especificado.
	 * 
	 * @param idCurso ID del curso
	 * @return true si existe, false en caso contrario
	 */
	private boolean existeCurso(int idCurso) {
		String sql = "SELECT COUNT(*) FROM cursos WHERE id_curso = ?";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idCurso);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			System.out.println("Error al verificar curso: " + e.getMessage());
		}

		return false;
	}

	/**
	 * Verifica si ya existe una asignación entre profesor y curso.
	 * 
	 * @param idProfesor ID del profesor
	 * @param idCurso    ID del curso
	 * @return true si existe la asignación, false en caso contrario
	 */
	private boolean existeAsignacion(int idProfesor, int idCurso) {
		String sql = "SELECT COUNT(*) FROM cursoprofesor WHERE id_profesor = ? AND id_curso = ?";

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.setInt(1, idProfesor);
			ps.setInt(2, idCurso);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1) > 0;
			}

		} catch (SQLException e) {
			System.out.println("Error al verificar asignación: " + e.getMessage());
		}

		return false;
	}

	/**
	 * Cierra la conexión con la base de datos.
	 */
	public void cerrarConexion() {
		try {
			if (conexion != null && !conexion.isClosed()) {
				conexion.close();
			}
		} catch (SQLException e) {
			System.out.println("Error al cerrar la conexión: " + e.getMessage());
		}
	}
}