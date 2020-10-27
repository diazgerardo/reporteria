package ar.com.tecnoaccion.reporteria.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.com.tecnoaccion.reporteria.beans.TotalesDiarios;
import ar.com.tecnoaccion.reporteria.rowmappers.TotalesDiariosRowMapper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@RestController
@Deprecated
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
		//rs.add("esquema 2");
		//rs.add(db2JdbcTemplate.queryForList("SELECT * FROM ROLES;").toString());
		//rs.add("esquema 3");
		//rs.add(db3JdbcTemplate.queryForList("SELECT * FROM ORGANIZACIONES;").toString());
		return rs;
	}
	
	@GetMapping("/totalesDiarios")
	String runTotalesDiariosReport() {		
		JasperReport report;
		String outputFilename = "C:/tmp/totalesDiarios_"+new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) +".pdf";
		//TODO directorio de salida ?.
		//TODO tener en cuenta parámetro provincia - contemplar el sql completo.
		try {
			List<TotalesDiarios> rs = mainJdbcTemplate.query("SELECT SUM(importe) FROM movimientos_billetera;", new TotalesDiariosRowMapper());
			if(null!=rs && !rs.isEmpty()) {	
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rs);				
				File file = new ClassPathResource("totalesDiarios.jrxml").getFile();				
				report = JasperCompileManager.compileReport(file.getAbsolutePath());				
				Map<String, Object> params = new HashMap<String,Object>();
				params.put( "sqlDate", new Date());
				JasperPrint printedReport = JasperFillManager.fillReport(report, params, ds);	
				JasperExportManager.exportReportToPdfFile( printedReport, outputFilename );	
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, ds);			
		    }
		} catch(JRException | IOException e) {
			e.printStackTrace();
			return "ERROR!!!";
		}
		return outputFilename;
	}

}
