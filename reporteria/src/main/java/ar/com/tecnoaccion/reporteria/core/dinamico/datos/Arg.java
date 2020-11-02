package ar.com.tecnoaccion.reporteria.core.dinamico.datos;

public class Arg {
    private String nombre;
    private String descripcion;
    private String tipo;
    private int sqlType;
    private Object valor;
    private boolean obligatorio;

    public Arg(String nombre, String descripcion, String tipo, int sqlType, Object valor, boolean obligatorio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.sqlType = sqlType;
        this.valor = valor;
        this.obligatorio = obligatorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getSqlType() {
        return sqlType;
    }

    public void setSqlType(int sqlType) {
        this.sqlType = sqlType;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }
}
