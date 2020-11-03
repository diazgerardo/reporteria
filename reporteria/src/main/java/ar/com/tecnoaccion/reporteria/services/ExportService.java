package ar.com.tecnoaccion.reporteria.services;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;
import ar.com.tecnoaccion.reporteria.exception.ReportException;
import net.sf.jasperreports.engine.JasperPrint;

public interface ExportService {
	public Boolean exportAs(final JasperPrint jasperPrint, final CType type, final String outputFile) throws ReportException;
}
