package ar.com.tecnoaccion.reporteria.services.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.com.tecnoaccion.reporteria.core.dinamico.GenericReport;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.JasperReportInput;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Logo;
import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;
import ar.com.tecnoaccion.reporteria.repositorios.ReportesRepository;
import ar.com.tecnoaccion.reporteria.services.ExportService;
import ar.com.tecnoaccion.reporteria.services.OrganizacionService;
import ar.com.tecnoaccion.reporteria.services.ReporteDinamicoService;
import net.sf.jasperreports.engine.JasperPrint;
@Service("reporteDinamicoService")
public class ReporteDinamicoServiceImpl implements ReporteDinamicoService {
	
	private final static Log logger = LogFactory.getLog(ReporteDinamicoServiceImpl.class);
	
	@Value("${reporte.output.path}")
	private String reporteOutputPath;
	@Value("${reporte.url}")
	private String reporteUrl;
	@Value("${reporte.logo.path}")
	private String reporteLogoPath;
	@Value("${reporte.logo.width}")
	private int reporteLogoWidth;
	@Value("${reporte.logo.height}")
	private int reporteLogoHeight; 
	@Value("${reporte.logo.align}")
	private String reporteLogoAlign; 
	
	@Autowired
	ExportService exportService;
	
	@Autowired
	OrganizacionService organizacionService;
	
	@Autowired
	ReportesRepository reportesRepository;

    public String ejecutarReporte(RepositoryInput dto) {
    	
		try {
	    	String filename       = dto.getKey() + new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) + "." + dto.getCType().getName();
			String outputFilename = reporteOutputPath + filename;
			String outputUrl      = reporteUrl + filename;	
	        OrganizacionDTO orga = organizacionService.getByCodigo(dto.getCodigoOrganizacion());
	        dto.setOrga(orga);
			JasperReportInput jasperInput = reportesRepository.runDynQuery(dto);
			jasperInput.setLogo( new Logo(reporteLogoPath,reporteLogoHeight, reporteLogoWidth, reporteLogoAlign) );
			GenericReport report = new GenericReport(jasperInput);
			JasperPrint jp = report.getReport();
			if(exportService.exportAs(jp, dto.getCType(), outputFilename))
				return outputUrl;
			else
				return "error creando reporte "+outputFilename;
		} catch (Throwable t) {
			logger.error(t);
			return "error inesperado creando reporte ";
		}
    }
    
}
