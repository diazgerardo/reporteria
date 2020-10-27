package ar.com.tecnoaccion.reporteria.datasources;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ReportesDataSourceConfig {

    @Bean(name = "reportesProperties")
    @ConfigurationProperties("spring.reportes")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "reportesDataSource")
    public DataSource dataSource(@Qualifier("reportesProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("reportes")
    public JdbcTemplate jdbcTemplate(@Qualifier("reportesDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}