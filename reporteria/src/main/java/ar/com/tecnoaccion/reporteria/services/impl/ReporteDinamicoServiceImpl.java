package ar.com.tecnoaccion.reporteria.services.impl;


import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import ar.com.tecnoaccion.reporteria.core.dinamico.GenericReport;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Arg;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Col;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.DatoReporte;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.SalidaReporte;
import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.enums.CType;
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
	
	@Value("${reporte.formato}")
	private String reporteFormato;

	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	ExportService exportService;
	
	@Autowired
	OrganizacionService organizacionService;

    public String getReport(String reportKey, CType out, int codigoOrganizacion, String consultaSql, Map<String, String> filters) {
   
    	String filename       = reportKey + new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) + "." + out.getName();
		String outputFilename = reporteOutputPath + filename;
		String outputUrl      = reporteUrl + filename;	
		
		
		//TODO FACTORIZAR INI 
		
        OrganizacionDTO orga = organizacionService.getByCodigo(codigoOrganizacion);
        DatoReporte datoReporte=new DatoReporte();
        datoReporte.setKey("testReport");
        List<Arg> args=new ArrayList<>();
        args.add(new Arg("desde","Fecha desde",String.class.getName(), Types.VARCHAR,null,false));
        args.add(new Arg("hasta","Fecha hasta",String.class.getName(),Types.VARCHAR, null,false));
        datoReporte.setCodigoOrganizacion(codigoOrganizacion);
        datoReporte.setSql(consultaSql);
        datoReporte.setOut(out);
        datoReporte.setArgumentos(args);

        List<Col> columnas=new ArrayList<>();
        columnas.add(new Col("apellido", "Apellido",String.class));
        columnas.add(new Col("nombre", "Nombre",String.class));
        columnas.add(new Col("documento", "Documento",String.class));
        SalidaReporte salidaReporte=new SalidaReporte("Reporte Fulanito","Lotline - " + orga.getNombre(),columnas);
       
		//TODO FACTORIZAR FIN
        
        
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        for(String campo : filters.keySet()) {
        	parameters.addValue(campo,new SqlParameterValue(Types.VARCHAR, filters.get(campo)));
        }
		List<Map<String, Object>> datos = namedParameterJdbcTemplate.queryForList(consultaSql,parameters); 
		GenericReport report = new GenericReport(datoReporte, salidaReporte, datos);
		Boolean result = Boolean.FALSE;
		try {
			JasperPrint jp = report.getReport();
			result = exportService.exportAs(jp, out.pdf, outputFilename);
		} catch (Throwable t) {
			logger.error(t);
		}
		if(result)
			return outputUrl;
		else 
			return "error creando reporte "+outputFilename;
    }
}
