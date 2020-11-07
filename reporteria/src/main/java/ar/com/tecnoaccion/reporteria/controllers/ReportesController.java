package ar.com.tecnoaccion.reporteria.controllers;

import static ar.com.tecnoaccion.reporteria.utils.JSONUtils.toJSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;
import ar.com.tecnoaccion.reporteria.dto.enums.CType;
import ar.com.tecnoaccion.reporteria.services.OrganizacionService;
import ar.com.tecnoaccion.reporteria.services.ReporteDinamicoService;

@RestController
@RequestMapping("/reportes")
class ReportesController {
	
	@Autowired
	OrganizacionService organizacionService;
	
	private ReporteDinamicoService reporteDinamicoService;
	
	public ReportesController(ReporteDinamicoService reporteDinamicoService) {        
        this.reporteDinamicoService = reporteDinamicoService;
    }

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping(path = "/organizaciones/{codigo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public OrganizacionDTO porCodigo(@PathVariable Integer codigo) {
		OrganizacionDTO dto = organizacionService.getByCodigo(codigo);
		return dto;
	}
	
	@GetMapping(path = "/parametros", produces = MediaType.APPLICATION_JSON_VALUE)
	String listParametros() {
		return toJSONArray(jdbcTemplate.queryForList("SELECT * FROM reportes.REPORTE_PARAMETROS;")).toString();
	}

	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	String listGrupos() {
		return toJSONArray(jdbcTemplate.queryForList("SELECT * FROM reportes.GRUPOS;")).toString();
	}
	 
	@GetMapping(path = "/reportes", produces=MediaType.APPLICATION_JSON_VALUE)
	@Validated
	public String listReportes(@RequestParam("grupo") @NumberFormat(style=Style.NUMBER) Optional<Integer> grupo) {
		 String sql = "SELECT * FROM reportes.REPORTES ";
		 if(grupo.isPresent()) {
			 sql = sql + " where grupo_id = '" + grupo.get() + "'";
		 }
		 return toJSONArray(jdbcTemplate.queryForList(sql)).toString();
	}

	@GetMapping(path = "/salidas", produces = MediaType.APPLICATION_JSON_VALUE)
	String listSalidas() {
		return toJSONArray(jdbcTemplate.queryForList("SELECT * FROM reportes.SALIDA;")).toString();
	}	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping("/dinamicos")
	String reporteDinamico(
			@RequestParam(value="key", required=true) String key,
			@RequestParam(value="codigoOrganizacion", required=true) int codigoOrganizacion,
			@RequestParam(value = "out",defaultValue = "pdf",required = false) CType out,
			@RequestParam HashMap<String, String> filters
		) {
	
		RepositoryInput reporteDTO = null;
		List<Map<String, Object>> results = jdbcTemplate.queryForList("SELECT rep.id,rep.consulta_sql,rep.titulo FROM reportes.REPORTES rep WHERE rep.codigo = '" + key + "'"); 
		if (results.isEmpty()) {
			return "Error: reporte desconocido " + key;
		} else {
			reporteDTO = new RepositoryInput();
			reporteDTO.setReporteId((Integer) results.get(0).get("id"));
			reporteDTO.setConsultaSql((String) results.get(0).get("consulta_sql"));
			reporteDTO.setTitulo((String) results.get(0).get("titulo"));
		}
		System.out.println("Reporte codigo: " + reporteDTO.toString());
	
		List<String> paramRequeridos = jdbcTemplate.queryForList("SELECT rp.nombre FROM reportes.REPORTE_PARAMETROS rp WHERE rp.opcional = FALSE and rp.reporte_id = '" + reporteDTO.getReporteId() + "'", String.class); 
		Boolean filtrosConParamRequeridos = paramRequeridos.stream().allMatch(
		   param -> filters.containsKey(param)
		);
		if(!filtrosConParamRequeridos) {
			return "Error: par�metro requerido no presente";
		}
	
		List<Map<String,Object>> params = jdbcTemplate.queryForList("SELECT rp.nombre,rp.tipo FROM reportes.REPORTE_PARAMETROS rp WHERE rp.reporte_id = '" + reporteDTO.getReporteId() + "'"); 
		Map map = new LinkedCaseInsensitiveMap();
		map.put("key",key);
		params.add(map);
		map = new LinkedCaseInsensitiveMap();
		map.put("codigoOrganizacion",codigoOrganizacion);
		params.add(map);
		map = new LinkedCaseInsensitiveMap();
		map.put("out",out);
		params.add(map);
		List<Object>paramKeys=new ArrayList<Object>();
		for(Map aMap:params) {
			paramKeys.addAll(aMap.values());
			paramKeys.addAll(aMap.keySet());
		}
		
		
		List<Map<String, Object>> nombreEtiquetaTamanio = jdbcTemplate.queryForList("SELECT sal.nombre, sal.etiqueta,sal.tam FROM reportes.SALIDA sal WHERE sal.reporte_id = '" + reporteDTO.getReporteId() + "'"); 
		if (nombreEtiquetaTamanio.isEmpty()) {
			return "Error: salida desconocida para reporteId=" + reporteDTO.getReporteId();
		} 
		
		Boolean paramValidos = filters.keySet().stream().allMatch(
		   param -> paramKeys.contains(param)
		);

		if(!paramValidos) {
			return "Error: parametro incorrecto";
		}		
		
		// todo Ok a crer el reporte
		reporteDTO.setKey(key);
		reporteDTO.setCodigoOrganizacion(codigoOrganizacion);
		reporteDTO.setFilters(filters);
		reporteDTO.setNombreEtiquetaTamanio(nombreEtiquetaTamanio);
		reporteDTO.setCType(out);
		reporteDTO.setRequestParams(params);
		return reporteDinamicoService.ejecutarReporte(reporteDTO);		
	}


}
