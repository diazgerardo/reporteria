package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import java.math.BigDecimal;
import java.util.Map;

public class ColumnaSalida {
    private String nombre;
    private String titulo;
    private BigDecimal tamanio;

    public ColumnaSalida(Map<String, Object> map) {
    	this.nombre=(String) map.get("nombre");
    	this.titulo=(String) map.get("etiqueta");
    	this.tamanio=(BigDecimal) map.get("tam");
    }

	public String getNombre() {
        return nombre;
    }

    public String getTitulo() {
        return titulo;
    }


    public BigDecimal getTamanio() {
		return tamanio;
	}

}
