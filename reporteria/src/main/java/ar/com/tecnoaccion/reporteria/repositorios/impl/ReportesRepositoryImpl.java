package ar.com.tecnoaccion.reporteria.repositorios.impl;

import static ar.com.tecnoaccion.reporteria.utils.SqlUtils.transform;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ar.com.tecnoaccion.reporteria.core.dinamico.datos.CampoEspecificado;
import ar.com.tecnoaccion.reporteria.core.dinamico.datos.JasperReportInput;
import ar.com.tecnoaccion.reporteria.dto.RepositoryInput;
import ar.com.tecnoaccion.reporteria.repositorios.ReportesRepository;

@Repository("reportesRepository")
public class ReportesRepositoryImpl extends BaseRepository implements ReportesRepository {

    public ReportesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

	@Override
	public JasperReportInput runDynQuery(RepositoryInput dto) {
		if(!dto.validate())
			throw new RuntimeException("no se pudo validar el DTO");
		MapSqlParameterSource params = createSqlParameters(dto.getCamposEspecificados());
        List<Map<String, Object>> columnasDatos = jdbcTemplate.queryForList(dto.getConsultaSql(),params);
        return new JasperReportInput(dto, columnasDatos);
	}

	
	private MapSqlParameterSource createSqlParameters(Map<String, CampoEspecificado> camposEspecificados) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
        for(String campo : camposEspecificados.keySet()) {
        	CampoEspecificado campoEspecificado = camposEspecificados.get(campo);
        	parameters.addValue(campoEspecificado.getNombre(), new SqlParameterValue(campoEspecificado.getTipoSql(), transform(campoEspecificado)));
        }
		return parameters;
	}

}
