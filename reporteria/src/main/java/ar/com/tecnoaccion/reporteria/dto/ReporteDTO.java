package ar.com.tecnoaccion.reporteria.dto;

import java.util.List;
import java.util.Map;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public class ReporteDTO {

	private Integer reporteId;
	private String titulo;
	private String consultaSql;
	private String key;
	private int codigoOrganizacion;
	private Map<String, String> filters;
	private List<Map<String, Object>> nombreEtiquetaTamanio;
	private CType out;
	
	
	public Integer getReporteId() {
		return reporteId;
	}

	public void setReporteId(Integer reporteId) {
		this.reporteId = reporteId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getConsultaSql() {
		return consultaSql;
	}

	public void setConsultaSql(String consultaSql) {
		this.consultaSql = consultaSql;
	}


	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getCodigoOrganizacion() {
		return codigoOrganizacion;
	}

	public void setCodigoOrganizacion(int codigoOrganizacion) {
		this.codigoOrganizacion = codigoOrganizacion;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public List<Map<String, Object>> getNombreEtiquetaTamanio() {
		return nombreEtiquetaTamanio;
	}

	public void setNombreEtiquetaTamanio(List<Map<String, Object>> nombreEtiquetaTamanio) {
		this.nombreEtiquetaTamanio = nombreEtiquetaTamanio;
	}

	public CType getOut() {
		return out;
	}

	public void setOut(CType out) {
		this.out = out;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("reporteId=")
				.append(this.reporteId)
				.append("\nTitulo=")
				.append(this.titulo)
				.append("\nConsultaSql=")
				.append(this.consultaSql)
				.append("\nkey=")
				.append(this.key)
				.append("\ncodigoOrganizacion=")
				.append(this.codigoOrganizacion)
				.append("\nfilters=")
				.append(this.filters)
				.append("\nnombreEtiquetaTamanio=")
				.append(this.nombreEtiquetaTamanio)
				.append("\nout=")
				.append(this.out)
				.toString();
	}
}
