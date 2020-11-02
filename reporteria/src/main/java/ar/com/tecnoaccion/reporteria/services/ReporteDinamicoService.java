package ar.com.tecnoaccion.reporteria.services;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;

import java.util.HashMap;

public interface ReporteDinamicoService {
    String getReport(String reportKey, CType out, int codigoOrganizacion, HashMap<String, String> filters);
}
