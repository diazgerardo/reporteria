package ar.com.tecnoaccion.reporteria.services;

import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;

public interface ReporteDinamicoService {
    String ejecutarReporte(RepositoryInput dto);
}
