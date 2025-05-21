package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import constantes.Constantes;
import entidades.Curso;

public class CursoDAO {

	private Connection conexion;

	public CursoDAO() {
		try {
			conexion = DriverManager.getConnection(Constantes.URL, Constantes.USUARIO, Constantes.CONTRASEÑA);
		} catch (SQLException e) {
			System.out.println("Error en la base de datos: " + e);
		}
	}

	public Connection getConexion() {
		return conexion;
	}

	public void crearCurso(Curso cur) {
		String sql = "INSERT INTO cursos (nombre, descripcion, año_escolar) VALUES (?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = conexion.prepareStatement(sql);

			ps.setString(1, cur.getNombre());
			ps.setString(2, cur.getDescripcion());
			ps.setInt(3, cur.getAñoEscolar());

			ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error al insertar el nuevo curso: " + e.getMessage());
		}
	}

	public ArrayList<Curso> listarCursos() {

		ArrayList<Curso> lista = new ArrayList<>();
		Curso curso;

		String sql = "SELECT nombre, descripcion, año_escolar FROM cursos";
		try {
			PreparedStatement ps = conexion.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				curso = new Curso(rs.getString(1), rs.getString(2), rs.getInt(3));
				lista.add(curso);
			}
		} catch (SQLException e) {
			System.out.println("Error con la consulta" + e.getMessage());
		}

		return lista;
	}

	public int eliminarCurso(int idCurso) {

		String sql = "DELETE FROM cursos WHERE id_curso = ?";
		int filasEliminadas = 0;

		try {
			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setInt(1, idCurso);

			filasEliminadas = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error con la consulta: " + e.getMessage());
		}
		return filasEliminadas;
	}

}
