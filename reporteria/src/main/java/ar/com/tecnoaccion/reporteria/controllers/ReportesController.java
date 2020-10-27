package ar.com.tecnoaccion.reporteria.controllers;

import static ar.com.tecnoaccion.reporteria.utils.JSONUtils.toJSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class ReportesController {

	@Autowired
	@Qualifier("reportes")
	private JdbcTemplate jdbcReportes;

	@GetMapping(path = "/reportesParametros", produces = MediaType.APPLICATION_JSON_VALUE)
	String listParametros() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM REPORTE_PARAMETROS;")).toString();
	}

	@GetMapping(path = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
	String listGrupos() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM GRUPOS;")).toString();
	}
	
	@GetMapping(path = "/reportes", produces=MediaType.APPLICATION_JSON_VALUE)
	public String listReportes() {
		 return toJSONArray(jdbcReportes.queryForList("SELECT * FROM REPORTES;")).toString();
	}

	@GetMapping(path = "/salidas", produces = MediaType.APPLICATION_JSON_VALUE)
	String listSalidas() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM GRUPOS;")).toString();
	}
	
}
