package ar.com.tecnoaccion.reporteria.core.dinamico.datos;



import java.util.List;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public class DatoReporte {
    private String key;
    private String sql;
    private CType out;
    private int codigoOrganizacion;
    private List<Arg> argumentos;

    public DatoReporte() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public CType getOut() {
        return out;
    }

    public void setOut(CType out) {
        this.out = out;
    }

    public int getCodigoOrganizacion() {
        return codigoOrganizacion;
    }

    public void setCodigoOrganizacion(int codigoOrganizacion) {
        this.codigoOrganizacion = codigoOrganizacion;
    }

    public List<Arg> getArgumentos() {
        return argumentos;
    }

    public void setArgumentos(List<Arg> argumentos) {
        this.argumentos = argumentos;
    }

}
