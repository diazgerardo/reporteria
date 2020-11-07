package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;

public class JasperReportInput {
    private String titulo;
    private String footer;
    private List<ColumnaEncabezado> columnas;
	private List<Map<String, Object>> columnasDatos;
	private RepositoryInput dto;
	private Logo logo;

    public JasperReportInput(RepositoryInput dto, List<Map<String, Object>> datos) {
        this.titulo=dto.getTitulo();
        this.footer="Lotline - " + dto.getOrga().getNombre();
        this.columnasDatos=datos;
		this.columnas=new ArrayList<>();
		this.dto=dto;
        for(Map<String, Object> map : dto.getNombreEtiquetaTamanio()) {
        	this.columnas.add(new ColumnaEncabezado(map));
        }
	}

	public List<ColumnaEncabezado> getColumnas() {
        return columnas;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFooter() {
        return footer;
    }

	public void setColumnasDatos(List<Map<String, Object>> columnasDatos) {
		this.columnasDatos=columnasDatos;
	}

	public  List<Map<String, Object>>  getColumnasDatos() {
		// importante
		checkConsistentBody();
		return this.columnasDatos;
	}
	
    /**
     * si existen datos, valida que el numero de columnas coincidan con
     * la cantidad de encabezados
     * si no existen, incorpora una linea con tantos literales "sin datos"
     * como columnas existan para que salga un reporte del tipo esperado
     * indicando los parametros recibidos y que el resultado no tiene
     * datos para ese periodo
     */
	private void checkConsistentBody() {
		if(!columnasDatos.isEmpty()) {
			for(Map<String,Object>fila : columnasDatos) {
				if(fila.size()!=columnas.size())
					throw new RuntimeException("no coincide el numero de columnas("+fila.size()+") con el numero de encabezados("+columnas.size()+")!");
				for(String key: fila.keySet()) {
					Object o = fila.get(key);
					if(o.getClass()!=String.class) {
						fila.put(key, o.toString());
					}
				}
			}
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			for(ColumnaEncabezado ce : columnas) {
				map.put(ce.getNombre(), new String("sin datos"));
			}
			columnasDatos.add(map);
		}
	}

	public Map<String, CampoEspecificado> getCamposEspecificados() {
		return dto.getCamposEspecificados();
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}


}
