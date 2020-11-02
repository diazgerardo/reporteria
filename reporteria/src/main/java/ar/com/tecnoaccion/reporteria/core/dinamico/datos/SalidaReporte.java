package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

import java.util.List;

public class SalidaReporte {
    String titulo;
    String footer;
    List<Col> columnas;

    public SalidaReporte(String titulo, String footer, List<Col> columnas) {
        this.titulo = titulo;
        this.footer = footer;
        this.columnas = columnas;
    }

    public SalidaReporte() {
    }

    public List<Col> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<Col> columnas) {
        this.columnas = columnas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}
