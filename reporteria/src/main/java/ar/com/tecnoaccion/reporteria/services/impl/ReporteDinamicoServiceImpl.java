package ar.com.tecnoaccion.reporteria.services.impl;


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
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.CampoDetalle;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.ColumnaEncabezado;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.DatoReporte;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Salida;
import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.ReporteDTO;
import ar.com.tecnoaccion.reporteria.exception.ReportException;
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
		DatoReporte datoReporte = createDatoReporte(dto, orga,dto.getCamposDetallados());
        Salida salidaReporte = mapearConfiguracionSalida(dto, orga);
        List<Map<String, Object>> columnnasDatos = namedParameterJdbcTemplate.queryForList(dto.getConsultaSql(),parameters); 
        comprobarCorrespondenciaColumnaEncabezado(salidaReporte.getColumnas(), columnnasDatos);
		GenericReport report = new GenericReport(datoReporte, salidaReporte, columnnasDatos);
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

	private void comprobarCorrespondenciaColumnaEncabezado(List<ColumnaEncabezado> encabezados, List<Map<String, Object>> datos) {
		for(Map<String,Object>fila : datos) {
			if(fila.size()!=encabezados.size())
				throw new RuntimeException("no coincide el numero de columnas("+fila.size()+") con el numero de encabezados("+encabezados.size()+")!");
		}
		//ok
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
		List<ColumnaEncabezado> columnas=new ArrayList<>();
        for(Map<String, Object> map : dto.getNombreEtiquetaTamanio()) {
        	columnas.add(new ColumnaEncabezado(map));
        }
        Salida salidaReporte=new Salida(dto.getTitulo(),"Lotline - " + orga.getNombre(),columnas);
		return salidaReporte;
	}

	private DatoReporte createDatoReporte(ReporteDTO dto, OrganizacionDTO orga, Map<String, CampoDetalle> camposDetallados) {
		DatoReporte datoReporte=new DatoReporte();
        datoReporte.setKey(dto.getKey());
        List<CampoDetalle> args=new ArrayList<>();
        args.addAll(camposDetallados.values());
        datoReporte.setCodigoOrganizacion(dto.getCodigoOrganizacion());
        datoReporte.setOut(dto.getOut());
        datoReporte.setArgumentos(args);
        datoReporte.setReporteLogoHeight( reporteLogoHeight );
        datoReporte.setReporteLogoPath ( reporteLogoPath );
        datoReporte.setReporteLogoWidth( reporteLogoWidth );
        		
		return datoReporte;
	}
}
