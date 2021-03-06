package ar.com.tecnoaccion.reporteria.core.dinamico.datos;



import java.util.List;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public class DatoReporte {
    private String key;
    private CType out;
    private int codigoOrganizacion;
    private List<CampoEspecificado> camposEspecificados;
    private Logo logo;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public List<CampoEspecificado> getCamposEspecificados() {
        return camposEspecificados;
    }

    public void setCamposEspecificados(List<CampoEspecificado> camposEspecificados) {
        this.camposEspecificados = camposEspecificados;
    }

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}


}
