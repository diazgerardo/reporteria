package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

import java.util.List;

public class Salida {
    String titulo;
    String footer;
    List<ColumnaSalida> columnas;

    public Salida(String titulo, String footer, List<ColumnaSalida> columnas) {
        this.titulo = titulo;
        this.footer = footer;
        this.columnas = columnas;
    }

    public Salida() {
    }

    public List<ColumnaSalida> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<ColumnaSalida> columnas) {
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
