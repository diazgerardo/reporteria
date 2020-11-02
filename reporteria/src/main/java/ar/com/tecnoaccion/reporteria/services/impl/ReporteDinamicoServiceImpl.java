package ar.com.tecnoaccion.reporteria.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.dto.enums.CType;
import ar.com.tecnoaccion.reporteria.services.OrganizacionService;
import ar.com.tecnoaccion.reporteria.services.ReporteDinamicoService;

@Service("reporteDinamicoService")
public class ReporteDinamicoServiceImpl implements ReporteDinamicoService {
	
	@Value("${reporte.output.path}")
	private String reporteOutputPath;

	@Value("${reporte.url}")
	private String reporteUrl;
	
	private OrganizacionService organizacionService;
    
    public ReporteDinamicoServiceImpl(OrganizacionService organizacionService) {
    	this.organizacionService = organizacionService;
    }

    public String getReport(String reportKey, CType out, int codigoOrganizacion, HashMap<String, String> filters) {
    	//OrganizacionDTO orga = organizacionService.getByCodigo(codigoOrganizacion);
    	//System.out.println(orga.getNombre());
    	String filename       = reportKey + new SimpleDateFormat("YYYYMMddhhmmsss").format(new Date()) + "." + out.getName();
		String outputFilename = reporteOutputPath + filename;
		String outputUrl      = reporteUrl + filename;	
        return outputUrl;
    }
}
