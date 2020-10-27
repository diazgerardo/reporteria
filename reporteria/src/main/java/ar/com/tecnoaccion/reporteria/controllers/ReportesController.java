package ar.com.tecnoaccion.reporteria.controllers;

import static ar.com.tecnoaccion.reporteria.utils.JSONUtils.toJSONArray;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.reports.ReporteService;
import ar.com.tecnoaccion.reporteria.reports.TotalesDiariosReporteImpl;

@RestController
@RequestMapping("/api")
class ReportesController implements ApplicationContextAware {

	private static Map<String, Class> servicios = new HashMap<String, Class>();
	static {
		servicios.put("totalesDiarios", TotalesDiariosReporteImpl.class);
	}
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
	

	@GetMapping("/ejecutar")
	String reportar(@RequestParam(value="reporte", required=true) String reporte ) {
		if(servicios.containsKey(reporte)) {
			//TODO revisar por que no obtiene el bean directamente por el nombre
			// y hay q andar haciendo esta manganeta...
			Class clazz = servicios.get(reporte);
			ReporteService service = (ReporteService) ctx.getBean(clazz);
			return service.reportar();
		}
		return "Error: reporte desconocido "+reporte;
	}

	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.ctx = ctx;
	}

}
