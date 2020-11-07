package ar.com.tecnoaccion.reporteria.repositorios;

import ar.com.tecnoaccion.reporteria.core.dinamico.datos.JasperReportInput;
import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;

public interface ReportesRepository {
	JasperReportInput runDynQuery(RepositoryInput dto);
}
