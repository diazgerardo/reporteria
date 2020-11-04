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

import ar.com.tecnoaccion.reporteria.core.CampoDetalle;
import ar.com.tecnoaccion.reporteria.core.dinamico.GenericReport;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Arg;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.ColumnaSalida;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.DatoReporte;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Salida;
import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.ReporteDTO;
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
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	ExportService exportService;
	
	@Autowired
	OrganizacionService organizacionService;

    public String getReport(ReporteDTO dto) {
   
    	String filename       = dto.getKey() + new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) + "." + dto.getOut().getName();
		String outputFilename = reporteOutputPath + filename;
		String outputUrl      = reporteUrl + filename;	
        OrganizacionDTO orga = organizacionService.getByCodigo(dto.getCodigoOrganizacion());
        MapSqlParameterSource parameters = createSqlParameters(dto.getCamposDetallados());
		DatoReporte datoReporte = createDatoReporte(dto, orga,parameters);
        Salida salidaReporte = mapearConfiguracionSalida(dto, orga);
        List<Map<String, Object>> datos = namedParameterJdbcTemplate.queryForList(dto.getConsultaSql(),parameters); 
		GenericReport report = new GenericReport(datoReporte, salidaReporte, datos);
		Boolean result = Boolean.FALSE;
		try {
			JasperPrint jp = report.getReport();
			result = exportService.exportAs(jp, dto.getOut().pdf, outputFilename);
		} catch (Throwable t) {
			logger.error(t);
		}
		if(result)
			return outputUrl;
		else 
			return "error creando reporte "+outputFilename;
    }

	private MapSqlParameterSource createSqlParameters(Map<String, CampoDetalle> camposDetallados) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        for(String campo : camposDetallados.keySet()) {
        	CampoDetalle campoDetalle = camposDetallados.get(campo);
        	parameters.addValue(campoDetalle.getNombre(), new SqlParameterValue(campoDetalle.getTipoSql(), campoDetalle.getValor()));
        }
		return parameters;
	}

	private Salida mapearConfiguracionSalida(ReporteDTO dto, OrganizacionDTO orga) {
		List<ColumnaSalida> columnas=new ArrayList<>();
        for(Map<String, Object> map : dto.getNombreEtiquetaTamanio()) {
        	columnas.add(new ColumnaSalida(map));
        }
        Salida salidaReporte=new Salida(dto.getTitulo(),"Lotline - " + orga.getNombre(),columnas);
		return salidaReporte;
	}

	private DatoReporte createDatoReporte(ReporteDTO dto, OrganizacionDTO orga, MapSqlParameterSource parameters) {
		DatoReporte datoReporte=new DatoReporte();
        datoReporte.setNombreOrga(orga.getNombre());
        datoReporte.setKey("testReport");
        List<Arg> args=new ArrayList<>();
        args.add(new Arg("desde","Fecha desde",String.class.getName(), Types.VARCHAR,((SqlParameterValue)parameters.getValues().get("desde")).getValue().toString(),false));
        args.add(new Arg("hasta","Fecha hasta",String.class.getName(),Types.VARCHAR,((SqlParameterValue)parameters.getValues().get("hasta")).getValue().toString() ,false));
        datoReporte.setCodigoOrganizacion(dto.getCodigoOrganizacion());
        datoReporte.setOut(dto.getOut());
        datoReporte.setArgumentos(args);
		return datoReporte;
	}
}
