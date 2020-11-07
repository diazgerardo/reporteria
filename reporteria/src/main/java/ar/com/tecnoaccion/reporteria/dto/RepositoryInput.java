package ar.com.tecnoaccion.reporteria.dto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.com.tecnoaccion.reporteria.core.dinamico.datos.CampoEspecificado;
import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public class RepositoryInput {

	private Integer reporteId;
	private String titulo;
	private String consultaSql;
	private String key;
	private int codigoOrganizacion;
	private Map<String, String> filters;
	private List<Map<String, Object>> nombreEtiquetaTamanio;
	private CType cType;
	private OrganizacionDTO orga;
	private List<Map<String, Object>> requestParams;
	private Map<String, CampoEspecificado> camposEspecificados;
	
	
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

	public CType getCType() {
		return cType;
	}

	public void setCType(CType cType) {
		this.cType = cType;
	}


	public OrganizacionDTO getOrga() {
		return orga;
	}

	public void setOrga(OrganizacionDTO orga) {
		this.orga = orga;
	}

	public void setRequestParams(List<Map<String, Object>> requestParams) {
		this.requestParams = requestParams;
	}
	public List<Map<String, Object>> getRequestParams() {
		return this.requestParams;
	}

	public void setCamposEspecificados(Map<String, CampoEspecificado> camposEspecificados) {
		this.camposEspecificados=camposEspecificados;
	}
	
	public Map<String, CampoEspecificado> getCamposEspecificados() {
		return this.camposEspecificados;
	}

	public boolean validate() {
		buildCamposEspecificados();
		return true;
	}
	/**
	 * los campos especificados se usan para construir los SQL Parameters
	 * basados en tres atributos: nombre del campo, tipo sql y valor
	 * 
	 * para eso se mergean dos mapas 1) viene de los parametros 
	 * recibidos en el request y de ahí se obtiene el valor de filtro
	 * 2) de la base de datos se obtiene el tipo sql a utilizar para
	 * ese campo
	 * 
	 */
	private void buildCamposEspecificados() {
		this.camposEspecificados = new HashMap<String, CampoEspecificado>();
		Iterator<Map.Entry<String, String>> it = this.getFilters().entrySet().iterator();
		while(it.hasNext()) {
		
			for(Map<String,Object>innMap : this.getRequestParams()) {
				if(innMap.containsKey("nombre")&&innMap.containsKey("tipo")) {
					//setea nombre y tipo sql
					CampoEspecificado campoEspecificado = new CampoEspecificado((String)innMap.get("nombre"), (Integer)innMap.get("tipo"));
					//TODO aunque en definitiva si la clave existe el valor se pisa,
					// aún hay que ver por qué se repite el campo varias veces (!?)
					camposEspecificados.put(campoEspecificado.getNombre(),campoEspecificado);
				}
			}
			it.next();
		}
		for(CampoEspecificado campoEspecificado : camposEspecificados.values()) {
			// setea el valor de filtrado
			campoEspecificado.setValor(this.getFilters().get(campoEspecificado.getNombre()));
		}
		
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
				.append(this.cType)
				.toString();
	}
}

