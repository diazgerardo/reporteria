package ar.com.tecnoaccion.reporteria.repositorios.impl;

import ar.com.tecnoaccion.reporteria.dto.OrganizacionDTO;
import ar.com.tecnoaccion.reporteria.repositorios.OrganizacionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("organizacionRepository")
public class OrganizacionRepositoryImpl extends BaseRepository implements OrganizacionRepository {

    public OrganizacionRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    public Optional<OrganizacionDTO> findByCodigo(Integer codigo) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("codigo", codigo);

        try {
            return jdbcTemplate.queryForObject(
                    "select o.codigo, o.descripcion as nombre from " + schemaBilletera + ".organizaciones o where o.codigo = :codigo and o.active is true",
                    mapSqlParameterSource,
                    (rs, rowNum) ->
                            Optional.of(new OrganizacionDTO(
                                    rs.getInt("codigo"),
                                    rs.getString("nombre")
                            ))
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
