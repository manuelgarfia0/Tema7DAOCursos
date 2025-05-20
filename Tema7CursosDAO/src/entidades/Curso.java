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
	private String añoEscolar;

	/**
	 * Constructor de Curso con todos sus atributos menos id que se autoincrementa.
	 * 
	 * @param nombre      nombre del curso.
	 * @param descripcion descripción del curso.
	 * @param añoEscolar  año escolar del curso.
	 */
	public Curso(String nombre, String descripcion, String añoEscolar) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.añoEscolar = añoEscolar;
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
	public String getAñoEscolar() {
		return añoEscolar;
	}

	/**
	 * Modifica el año escolar del curso.
	 * 
	 * @param añoEscolar
	 */
	public void setAñoEscolar(String añoEscolar) {
		this.añoEscolar = añoEscolar;
	}

}
