package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import java.util.List;

public class Salida {
    private String titulo;
    private String footer;
    private List<ColumnaEncabezado> columnas;

    public Salida(final String titulo, final String footer, final List<ColumnaEncabezado> columnas) {
        this.titulo = titulo;
        this.footer = footer;
        this.columnas = columnas;
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

}
