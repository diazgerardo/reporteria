package ar.com.tecnoaccion.reporteria.services.impl;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;
import ar.com.tecnoaccion.reporteria.exception.ReportException;
import ar.com.tecnoaccion.reporteria.services.ExportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.ExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
@Service("exportService")
public class ExportServiceImpl implements ExportService {

	private static Logger logger = Logger.getLogger(ExportServiceImpl.class);
	public static final String FORMAT_HTML = "html";
	public static final String FORMAT_PDF = "pdf";
	public static final String FORMAT_XLSX = "xlsx";
	@SuppressWarnings("rawtypes")
	final private static Map<String, Exporter> EXPORTERS = new HashMap<String, Exporter>();
	static {
		EXPORTERS.put(FORMAT_HTML, new HtmlExporter());
		EXPORTERS.put(FORMAT_PDF, new JRPdfExporter());
		EXPORTERS.put(FORMAT_HTML, new JRXlsxExporter());
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Boolean exportAs(final JasperPrint jasperPrint, final CType type, final String outputFile) throws ReportException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		final Exporter exporter = EXPORTERS.get(type.getName());
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(getOutput(type, out));
		try {
			exporter.exportReport();
			File file = new File(outputFile);
			FileOutputStream stream = new FileOutputStream(file);
			out.writeTo(stream);
			out.close();
			stream.close();
			if(logger.isDebugEnabled())
				logger.debug("archivo "+outputFile+" creado.");
			return Boolean.TRUE;
		} catch ( JRException | IOException e ) {
			logger.error(e);
		}
		if(logger.isDebugEnabled())
			logger.debug("no se pudo crear el archivo "+outputFile);
		return Boolean.FALSE;
	}

	private ExporterOutput getOutput(CType type, ByteArrayOutputStream out) {
		return 
			FORMAT_HTML.equals(type.getName()) 
				? new SimpleHtmlExporterOutput(out)
				: new SimpleOutputStreamExporterOutput(out);
	}
}
