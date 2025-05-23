package entidades;

/**
 * La clase curso construye todos los campos de los que está compuesto un curso.
 * 
 * @author manuel.garfia
 */
public class Curso {
	/**
	 * Atributo id autoincrementable en la base de datos.
	 */
	private int id;
	/**
	 * Atributo nombre del curso.
	 */
	private String nombre;
	/**
	 * Atributo descripción del curso.
	 */
	private String descripcion;
	/**
	 * Atributo año escolar del curso.
	 */
	private int añoEscolar;

	/**
	 * Constructor de Curso con todos sus atributos menos id que se autoincrementa.
	 * 
	 * @param nombre      nombre del curso.
	 * @param descripcion descripción del curso.
	 * @param añoEscolar  año escolar del curso.
	 */
	public Curso(String nombre, String descripcion, int añoEscolar) {
		if (nombre != null && !nombre.isEmpty()) {
			this.nombre = nombre;
		}
		if (descripcion != null && !descripcion.isEmpty()) {
			this.descripcion = descripcion;
		}
		if (añoEscolar > 0) {
			this.añoEscolar = añoEscolar;
		}

	}

	/**
	 * Obtiene el id del curso.
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el id del curso.
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del curso.
	 * 
	 * @return curso
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del curso.
	 * 
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la descripción del curso.
	 * 
	 * @return descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Modifica la descripcion del curso.
	 * 
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Obtiene el año escolar del curso.
	 * 
	 * @return añoEscolar
	 */
	public int getAñoEscolar() {
		return añoEscolar;
	}

	/**
	 * Modifica el año escolar del curso.
	 * 
	 * @param añoEscolar
	 */
	public void setAñoEscolar(int añoEscolar) {
		this.añoEscolar = añoEscolar;
	}

	/**
	 * Devuelve una representación en cadena del curso.
	 * 
	 * @return String con la información del curso
	 */
	@Override
	public String toString() {
		return "ID: " + id + " | Nombre: " + nombre + " | Descripción: " + descripcion + " | Año Escolar: "
				+ añoEscolar;
	}
}