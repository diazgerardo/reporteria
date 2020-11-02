package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

public class Col {
    private String nombre;
    private String titulo;
    private Class clase;

    public Col(String nombre, String titulo, Class clase) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.clase = clase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Class getClase() {
        return clase;
    }

    public void setClase(Class clase) {
        this.clase = clase;
    }
}
