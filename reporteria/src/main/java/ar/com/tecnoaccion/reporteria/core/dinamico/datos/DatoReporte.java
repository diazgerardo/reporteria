package ar.com.tecnoaccion.reporteria.core.dinamico.datos;



import java.util.List;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public class DatoReporte {
    private String key;
    private CType out;
    private int codigoOrganizacion;
    private List<CampoDetalle> camposDetallados;
	private String nombreOrga;
	private int reporteLogoHeight;
	private String reporteLogoPath;
	private int reporteLogoWidth;

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

    public List<CampoDetalle> getArgumentos() {
        return camposDetallados;
    }

    public void setArgumentos(List<CampoDetalle> camposDetallados) {
        this.camposDetallados = camposDetallados;
    }

	public void setReporteLogoPath(String reporteLogoPath) {
		this.reporteLogoPath=reporteLogoPath;
	}

	public void setReporteLogoWidth(int reporteLogoWidth) {
		this.reporteLogoWidth=reporteLogoWidth;
	}
	public void setReporteLogoHeight(int reporteLogoHeight) {
		this.reporteLogoHeight=reporteLogoHeight;
	}

	public int getReporteLogoHeight() {
		return reporteLogoHeight;
	}

	public String getReporteLogoPath() {
		return reporteLogoPath;
	}

	public int getReporteLogoWidth() {
		return reporteLogoWidth;
	}


}
