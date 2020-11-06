package ar.com.tecnoaccion.reporteria.utils;

import java.text.SimpleDateFormat;

import ar.com.tecnoaccion.reporteria.core.dinamico.datos.CampoEspecificado;

public final class SqlUtils {

	private SqlUtils() {
	}

	public static Object transform(CampoEspecificado campoEspecificado) {
		Object result = null;
		try {
			switch(campoEspecificado.getTipoSql()) {
			case 2:
			case 4:
			case 5:
				result = Integer.valueOf(campoEspecificado.getValor());
				break;
			case 12:
				result = campoEspecificado.getValor();
				break;
			case 91:
				result = new SimpleDateFormat("YYYYMMDD").parse(campoEspecificado.getValor());
			default:
				// varchar
				result = campoEspecificado.getValor();
		}
			return result;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new RuntimeException("conversion no soportada para valor="+campoEspecificado.getValor()+" tipoSql="+campoEspecificado.getTipoSql());
		}
	}
}
