package ar.com.tecnoaccion.reporteria.core.dinamico;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.core.layout.HorizontalBandAlignment;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ImageBanner.Alignment;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.ColumnaEncabezado;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.DatoReporte;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.Salida;
import ar.com.tecnoaccion.reporteria.exception.ReportException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Pojo used to hold report state and generate JasperReport object.
 * @author davidkey
 */
@SuppressWarnings("deprecation")
/*
 * SuppressWarnings for HorizontalAlign, VertialAlign constants
 * as they have been deprecated by DynamicJasper with no alternative or replacement mentioned.
 */

public class GenericReport implements Report {
	private DatoReporte datoReporte;
	private Salida salidaReporte;
	private List<Map<String,Object>> resultados;
	
	public GenericReport(DatoReporte datoReporte, Salida salidaReporte,List<Map<String, Object>> resultados) {
		this.datoReporte = datoReporte;
		this.salidaReporte = salidaReporte;
		this.resultados = resultados;
	}

	public JasperPrint getReport() throws ReportException {
		final JasperPrint jp;
		try {
			Style headerStyle = createHeaderStyle();
			Style detailTextStyle = createDetailTextStyle();
			Style detailNumberStyle = createDetailNumberStyle();
			DynamicReport dynaReport = getReport(headerStyle, detailTextStyle, detailNumberStyle);
			jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(),
					new JRBeanCollectionDataSource(resultados));
		} catch (JRException | ColumnBuilderException | ClassNotFoundException e) {
			throw new ReportException(e);
		}

		return jp;
	}

	private Style createHeaderStyle() {		
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM_BOLD)
				.setBorder(Border.THIN())
				.setBorderBottom(Border.PEN_2_POINT())
				.setBorderColor(Color.BLACK)
				.setBackgroundColor(Color.LIGHT_GRAY)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.CENTER)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setTransparency(Transparency.OPAQUE)
				.build();
	}

	private Style createDetailTextStyle() {
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM)
				.setBorder(Border.DOTTED())
				.setBorderColor(Color.BLACK)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.LEFT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingLeft(5)
				.build();
	}

	private Style createDetailNumberStyle() {
		return new StyleBuilder(true)
				.setFont(Font.VERDANA_MEDIUM)
				.setBorder(Border.DOTTED())
				.setBorderColor(Color.BLACK)
				.setTextColor(Color.BLACK)
				.setHorizontalAlign(HorizontalAlign.RIGHT)
				.setVerticalAlign(VerticalAlign.MIDDLE)
				.setPaddingRight(5)
				.setPattern("#,##0.00")
				.build();
	}

	private AbstractColumn createColumn(String property, Class<?> type, String title, int width, Style headerStyle, Style detailStyle)
			throws ColumnBuilderException {
		return ColumnBuilder.getNew()
				.setColumnProperty(property, type.getName())
				.setTitle(title)
				.setWidth(Integer.valueOf(width))
				.setStyle(detailStyle)
				.setHeaderStyle(headerStyle)
				.build();
	}


	private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle)
			throws ColumnBuilderException, ClassNotFoundException {

		DynamicReportBuilder report = new DynamicReportBuilder();
		report.addImageBanner(datoReporte.getReporteLogoPath(), datoReporte.getReporteLogoWidth(), datoReporte.getReporteLogoHeight(), Alignment.Left);
		salidaReporte.getColumnas().stream().forEach(
				k -> report.addColumn(
						createColumn(
								k.getNombre(),
								String.class,
								k.getTitulo(),
								k.getTamanio().multiply(new BigDecimal(100)).intValue(),
								headerStyle,
								detailTextStyle)));
		
		StyleBuilder titleStyle = new StyleBuilder(true);
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		titleStyle.setFont(new Font(20, null, true));
		
		StyleBuilder subTitleStyle = new StyleBuilder(true);
		subTitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		subTitleStyle.setFont(new Font(Font.MEDIUM, null, true));

		report.setTitle(salidaReporte.getTitulo());
		report.setTitleStyle(titleStyle.build());
		String argumentos=datoReporte.getArgumentos().stream()
				.filter(e -> Objects.nonNull(e.getNombre()))
				.map(e -> e.getNombre()+"="+e.getValor())
				.collect(Collectors.joining(", "));
		if(Objects.nonNull(argumentos) && !argumentos.isEmpty()) {
			report.setSubtitle("Parámetros: " + argumentos);
			report.setSubtitleStyle(subTitleStyle.build());
		}
		report.setUseFullPageWidth(true);
		
		String texto=salidaReporte.getFooter();
		AutoText hdr=new AutoText((texto),AutoText.POSITION_FOOTER,HorizontalBandAlignment.LEFT);
		hdr.setWidth(600);
		report.addAutoText(hdr);
		//report.addFirstPageImageBanner("/home/marinor/sc.png",120,15, ImageBanner.Alignment.Center);
		report.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y,AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		texto="Creado: " + sdf.format(new Date())+" - Registros: " + resultados.size();
		hdr=new AutoText((texto), AutoText.POSITION_HEADER,HorizontalBandAlignment.LEFT);
		hdr.setWidth(new Integer(500));
		report.addAutoText(hdr);
		return report.build();
	}
}