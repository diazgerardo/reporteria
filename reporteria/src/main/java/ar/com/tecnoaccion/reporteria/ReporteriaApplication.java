package ar.com.tecnoaccion.reporteria;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.hibernate.service.spi.InjectService;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import ar.com.tecnoaccion.reporteria.beans.Resultado;
import ar.com.tecnoaccion.reporteria.exception.MiddleResponseErrorHandler;

@ComponentScan(basePackages = {"ar.com.tecnoaccion.reporteria"})
@EntityScan("ar.com.tecnoaccion.reporteria.modelos")
@EnableAutoConfiguration(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
@EnableAsync
@SpringBootApplication

public class ReporteriaApplication {

	@Autowired
	@Qualifier("main") 
	private JdbcTemplate mainJdbcTemplate;

	@Autowired
	@Qualifier("db2") 
	private JdbcTemplate db2JdbcTemplate;

	@Autowired
	@Qualifier("db3") 
	private JdbcTemplate db3JdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(ReporteriaApplication.class, args);
	}

	@SuppressWarnings("rawtypes")
	@Bean Resultado runQueries() {
		String query = "SELECT * FROM USUARIOS;";
		List rs = mainJdbcTemplate.queryForList(query);
		Resultado r = new Resultado(rs); 
		rs = db2JdbcTemplate.queryForList(query);
		r.getRs().add(rs);
		rs = db3JdbcTemplate.queryForList(query);
		r.getRs().add(rs);
		return r;
	}
	
	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();

		SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword("#YerbaMate2018*");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");

		encryptor.setConfig(config);

		return encryptor;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		builder.setConnectTimeout(Duration.ofSeconds(6));
		builder.setReadTimeout(Duration.ofSeconds(6));
		RestTemplate build = builder.build();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

		converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		build.setMessageConverters(Arrays.asList(converter, new FormHttpMessageConverter()));
		build.setErrorHandler(new MiddleResponseErrorHandler());
		return build;
	}
	
}