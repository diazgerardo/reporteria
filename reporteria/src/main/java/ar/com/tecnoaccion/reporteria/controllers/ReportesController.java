package ar.com.tecnoaccion.reporteria.controllers;

import static ar.com.tecnoaccion.reporteria.utils.JSONUtils.toJSONArray;

import java.util.HashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.core.ReporteComponent;
import ar.com.tecnoaccion.reporteria.dto.enums.CType;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
class ReportesController implements ApplicationContextAware {

	private ApplicationContext ctx;
	
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
	@Validated
	public String listReportes(@RequestParam("grupo") @NumberFormat(style=Style.NUMBER) Integer grupo) {
		 return toJSONArray(jdbcReportes.queryForList("SELECT * FROM REPORTES where grupo_id = '"+grupo+"'")).toString();
	}

	@GetMapping(path = "/salidas", produces = MediaType.APPLICATION_JSON_VALUE)
	String listSalidas() {
		return toJSONArray(jdbcReportes.queryForList("SELECT * FROM GRUPOS;")).toString();
	}
	

	@GetMapping("/dinamicos")
	String reporteDinamico(
			@RequestParam("key") String reportKey,
            @RequestParam(value = "out", defaultValue = "pdf", required = false) CType out,
            @RequestParam(value = "codigoOrganizacion", required = true) int codigoOrganizacion,
            @RequestParam HashMap<String, String> filtros) {
		ReporteComponent service =ctx.getBean(reportKey, ReporteComponent.class);
		return service.reportar();
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
