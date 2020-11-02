package ar.com.tecnoaccion.reporteria.core.dinamico;

import ar.com.tecnoaccion.reporteria.exception.ReportException;
import net.sf.jasperreports.engine.JasperPrint;

public interface Report {
    public JasperPrint getReport() throws ReportException;
}


