package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

public class CampoEspecificado {
	
	private String nombre;
	private int tipoSql;
	private String valor;
	
	public CampoEspecificado(String nombre, int tipoSql) {
		this.nombre=nombre;
		this.tipoSql=tipoSql;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTipoSql() {
		return tipoSql;
	}

	public void setTipoSql(int tipoSql) {
		this.tipoSql = tipoSql;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
}
