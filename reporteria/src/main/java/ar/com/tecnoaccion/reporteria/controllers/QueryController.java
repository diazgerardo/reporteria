package ar.com.tecnoaccion.reporteria.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.beans.Sample;
import ar.com.tecnoaccion.reporteria.rowmappers.SampleRowMapper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
class QueryController {

	@Autowired
	@Qualifier("main")
	private JdbcTemplate mainJdbcTemplate;

	@Autowired
	@Qualifier("db2")
	private JdbcTemplate db2JdbcTemplate;

	@Autowired
	@Qualifier("db3")
	private JdbcTemplate db3JdbcTemplate;

	@GetMapping("/runQuery")
	List<String> runQuery() {

		List<String> rs = new ArrayList<String>();
		rs.add("esquema 1");
		rs.add(mainJdbcTemplate.queryForList("SELECT * FROM OPERADORES;").toString());
		rs.add("esquema 2");
		rs.add(db2JdbcTemplate.queryForList("SELECT * FROM ROLES;").toString());
		rs.add("esquema 3");
		rs.add(db3JdbcTemplate.queryForList("SELECT * FROM ORGANIZACIONES;").toString());
		return rs;
	}

	@GetMapping("/runReport")
	String runReport() {
		
		JasperReport report;
		try {
			List<Sample> rs = mainJdbcTemplate.query("SELECT * FROM USUARIOS;", new SampleRowMapper());
			
			// TODO el row mapper eventualmente puede tener un constructor q permita pasarle la lista de objetos
			// para que se vaya populando con data proveniente de los otros jdbc templates
			
			if(null!=rs && !rs.isEmpty()) {
				
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rs);
				report = JasperCompileManager.compileReport( "C:/drive_d/tmp/SampleReport.jrxml" );
				Map<String, Object> params = new HashMap<String,Object>();
				params.put( "sqlDate", new Date());
				JasperPrint printedReport = JasperFillManager.fillReport(report, params, ds);
	
				String outputFilename = "C:/drive_d/tmp/SampleReport.pdf";
				JasperExportManager.exportReportToPdfFile( printedReport, outputFilename );
	
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, ds);
			
				// continua la logica q haga falta, si acaso
				//
			}

		} catch (JRException e) {
			e.printStackTrace();
			return "ERROR!!!";
		}
		return "Reporte generado Ok.";
	}

}
