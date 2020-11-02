package ar.com.tecnoaccion.reporteria.controllers;

import static ar.com.tecnoaccion.reporteria.utils.JSONUtils.toJSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.dto.enums.CType;
import ar.com.tecnoaccion.reporteria.services.ReporteDinamicoService;

@RestController
@RequestMapping("/reportes")
class ReportesController {
	
	private ReporteDinamicoService reporteDinamicoService;
	
	public ReportesController(ReporteDinamicoService reporteDinamicoService) {        
        this.reporteDinamicoService = reporteDinamicoService;
    }

	@Autowired
	private JdbcTemplate jdbcReportes;
	
	@GetMapping(path = "/parametros", produces = MediaType.APPLICATION_JSON_VALUE)
	String listParametros() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM REPORTE_PARAMETROS;")).toString();
	}

	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	String listGrupos() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM GRUPOS;")).toString();
	}
	 
	@GetMapping(path = "/reportes", produces=MediaType.APPLICATION_JSON_VALUE)
	@Validated
	public String listReportes(@RequestParam("grupo") @NumberFormat(style=Style.NUMBER) Optional<Integer> grupo) {
		 String sql = "SELECT * FROM REPORTES ";
		 if(grupo.isPresent()) {
			 sql = sql + " where grupo_id = '" + grupo.get() + "'";
		 }
		 return toJSONArray(jdbcReportes.queryForList(sql)).toString();
	}

	@GetMapping(path = "/salidas", produces = MediaType.APPLICATION_JSON_VALUE)
	String listSalidas() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM SALIDA;")).toString();
	}	

	@GetMapping("/dinamicos")
	String reporteDinamico(
			@RequestParam(value="key", required=true) String key,
			@RequestParam(value="codigoOrganizacion", required=true) int codigoOrganizacion,
			@RequestParam(value = "out",defaultValue = "pdf",required = false) CType out,
			@RequestParam HashMap<String, String> filters
		) {
		
		Integer reporteId;
		List<Integer> rId = jdbcReportes.queryForList("SELECT rep.id FROM reportes.REPORTES rep WHERE rep.codigo = '" + key + "'", Integer.class); 
		if (rId.isEmpty()) {
			return "Error: reporte desconocido " + key;
		} else {
			reporteId = rId.get(0);
		}
		System.out.println("Reporte c�digo: " + reporteId);
				
		List<String> paramRequeridos = jdbcReportes.queryForList("SELECT rp.nombre FROM reportes.REPORTE_PARAMETROS rp WHERE rp.opcional = FALSE and rp.reporte_id = '" + reporteId + "'", String.class); 
		Boolean filtrosConParamRequeridos = paramRequeridos.stream().allMatch(
		   param -> filters.containsKey(param)
		);
		if(!filtrosConParamRequeridos) {
			return "Error: par�metro requerido no presente";
		}
	
		List<String> params = jdbcReportes.queryForList("SELECT rp.nombre FROM REPORTE_PARAMETROS rp WHERE rp.reporte_id = '" + reporteId + "'", String.class); 
		params.add("key");
		params.add("codigoOrganizacion");
		Boolean paramValidos = filters.keySet().stream().allMatch(
		   param -> params.contains(param)
		);
		filters.keySet().stream().forEach(
				   k -> System.out.println(k)
				);
		if(!paramValidos) {
			return "Error: par�metro incorrecto";
		}		
		return reporteDinamicoService.getReport(key,out,codigoOrganizacion,filters);		
	}
}
