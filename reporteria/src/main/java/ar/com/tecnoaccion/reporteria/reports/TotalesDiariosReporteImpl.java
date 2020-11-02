package ar.com.tecnoaccion.reporteria.reports;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ar.com.tecnoaccion.reporteria.beans.TotalesDiarios;
import ar.com.tecnoaccion.reporteria.rowmappers.TotalesDiariosRowMapper;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Qualifier("totalesDiarios")
public class TotalesDiariosReporteImpl implements ReporteService {

	@Value("${reporte.output.path}")
	private String reporteOutputPath;

	@Value("${reporte.url}")
	private String reporteUrl;

	@Autowired
	@Qualifier("main")
	private JdbcTemplate mainJdbcTemplate;
	
	public TotalesDiariosReporteImpl() {
		System.out.println();
	} 
	@Override
	public String reportar() {	
		JasperReport report;
		String filename       = "TotalesDiarios_" + new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) + ".pdf";
		String outputFilename = reporteOutputPath + filename;
		String outputUrl      = reporteUrl + filename;	
		try {
			List<TotalesDiarios> rs = mainJdbcTemplate.query("SELECT SUM(importe) FROM movimientos_billetera;",
					new TotalesDiariosRowMapper());
			if (null != rs && !rs.isEmpty()) {
				JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(rs);
				File file = new ClassPathResource("totalesDiarios.jrxml").getFile();
				report = JasperCompileManager.compileReport(file.getAbsolutePath());
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("sqlDate", new Date());
				JasperPrint printedReport = JasperFillManager.fillReport(report, params, ds);
				JasperExportManager.exportReportToPdfFile(printedReport, outputFilename);
			}
		} catch (JRException | IOException e) {
			e.printStackTrace();
			return "ERROR!!!";
		}
		return outputUrl;
	}
}
