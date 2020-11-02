package ar.com.tecnoaccion.reporteria.repositorios.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

abstract class BaseRepository {
    NamedParameterJdbcTemplate jdbcTemplate;
    @Value("${spring.application.schema.cupon}")
    String schemaCupon;
    @Value("${spring.application.schema.billetera}")
    String schemaBilletera;
    @Value("${spring.application.schema.quiniela}")
    String schemaQuiniela;

    BaseRepository(NamedParameterJdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
}
