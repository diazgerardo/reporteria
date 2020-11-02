package ar.com.tecnoaccion.reporteria.dto;

public class OrganizacionDTO {
    private Integer codigoOrganizacion;
    private String nombre;

    public OrganizacionDTO(Integer codigoOrganizacion, String nombre) {
        this.codigoOrganizacion = codigoOrganizacion;
        this.nombre = nombre;
    }

    public Integer getCodigoOrganizacion() {
        return codigoOrganizacion;
    }

    public void setCodigoOrganizacion(Integer codigoOrganizacion) {
        this.codigoOrganizacion = codigoOrganizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
