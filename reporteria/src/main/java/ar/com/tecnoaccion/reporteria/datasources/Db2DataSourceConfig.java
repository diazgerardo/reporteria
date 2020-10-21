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
public class Db2DataSourceConfig {

    @Bean(name = "db2Properties")
    @ConfigurationProperties("spring.db2")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "db2DataSource")
    public DataSource dataSource(@Qualifier("db2Properties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean("db2")
    public JdbcTemplate jdbcTemplate(@Qualifier("db2DataSource") DataSource ccbsDataSource) {
        return new JdbcTemplate(ccbsDataSource);
    }

}