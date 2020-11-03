package ar.com.tecnoaccion.reporteria.services;

import java.util.Map;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

public interface ReporteDinamicoService {
    String getReport(String reportKey, CType out,  int codigoOrganizacion, String consultaSQL, Map<String, String> filters);
}
